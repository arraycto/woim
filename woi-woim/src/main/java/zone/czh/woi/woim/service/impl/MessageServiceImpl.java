package zone.czh.woi.woim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import zone.czh.woi.woim.base.constant.MapperProperties;
import zone.czh.woi.woim.base.obj.po.Contact;
import zone.czh.woi.woim.base.obj.po.GroupMember;
import zone.czh.woi.woim.base.obj.po.OfflineGroupMsg;
import zone.czh.woi.woim.base.obj.po.OfflinePrivateMsg;
import zone.czh.woi.woim.base.obj.vo.*;
import zone.czh.woi.woim.mapper.ContactMapper;
import zone.czh.woi.woim.mapper.GroupMemberMapper;
import zone.czh.woi.woim.mapper.OfflineGroupMsgMapper;
import zone.czh.woi.woim.mapper.OfflinePrivateMsgMapper;
import zone.czh.woi.woim.service.inter.MessageService;
import zone.czh.woi.woim.service.inter.PushService;
import zone.czh.woi.woim.base.obj.vo.MsgState;

import java.util.*;

/**
*@ClassName: MessageServiceImpl
*@Description: None
*@author woi
*/

public class MessageServiceImpl implements MessageService {
    @Autowired
    PushService pushService;
    @Autowired
    OfflinePrivateMsgMapper offlinePrivateMsgMapper;
    @Autowired
    OfflineGroupMsgMapper offlineGroupMsgMapper;
    @Autowired
    ContactMapper contactMapper;
    @Autowired
    GroupMemberMapper groupMemberMapper;

    @Override
    public MsgState sendMsg(PrivateMsg msg) {
        Contact contact = contactMapper.selectOne(new Contact().setHostUid(msg.getSrcUid()).setContactUid(msg.getDestUid()));
        if (contact==null){
            return new MsgState(MsgState.STATE_OUT_OF_TOUCH);
        }
        pushService.pushDistributed(msg.getDestUid(), msg, ()->{
            offlineMsg(msg);
        });
        return new MsgState(MsgState.STATE_SUCCESS);
    }

    @Override
    public MsgState sendMsg(GroupMsg msg) {
        //todo 改为将群聊信息发送到消息队列进行再进行后续信息转发处理，加快客户端响应速度
        if (groupMemberMapper.selectCount(new GroupMember().setUid(msg.getSrcUid()).setGroupId(msg.getGroupId()))==0){
            return new MsgState(MsgState.STATE_OUT_OF_TOUCH);
        }
        List<GroupMember> members = groupMemberMapper.select(new GroupMember().setGroupId(msg.getGroupId()));
        if (members!=null){
            for (GroupMember member:members){
                if (!msg.getSrcUid().equals(member.getUid())){
                    pushService.pushDistributed(member.getUid(),msg,()->{
                        offlineGroupMsg(msg,member.getUid());
                    });
                }

            }
        }
        return new MsgState(MsgState.STATE_SUCCESS);
    }

    @Override
    public void offlineMsg(PrivateMsg msg) {
        OfflinePrivateMsg offlinePrivateMsg = new OfflinePrivateMsg(msg);
        offlinePrivateMsgMapper.insertSelective(offlinePrivateMsg);
    }

    @Override
    public void offlineGroupMsg(GroupMsg msg, String destUid) {
        OfflineGroupMsg offlineGroupMsg = new OfflineGroupMsg(msg,destUid);
        offlineGroupMsgMapper.insertSelective(offlineGroupMsg);
    }

    @Override
    public OfflineMsg pullOfflineMsg(String uid) {
        Example privateMsgExp = new Example(OfflinePrivateMsg.class);
        privateMsgExp.orderBy(MapperProperties.OfflinePrivateMsg.CREATE_TIME);
        privateMsgExp.createCriteria().andEqualTo(MapperProperties.OfflinePrivateMsg.DEST_UID,uid);
        List<OfflinePrivateMsg> privateMsgs = offlinePrivateMsgMapper.selectByExample(privateMsgExp);

        Example groupMsgExp = new Example(OfflineGroupMsg.class);
        groupMsgExp.orderBy(MapperProperties.OfflineGroupMsg.CREATE_TIME);
        groupMsgExp.createCriteria().andEqualTo(MapperProperties.OfflineGroupMsg.DEST_UID,uid);
        List<OfflineGroupMsg> groupMsgs = offlineGroupMsgMapper.selectByExample(groupMsgExp);
        delOfflinePrivateMsg(privateMsgs);
        delOfflineGroupMsg(groupMsgs);
        return new OfflineMsg().setPrivateMsgs(privateMsgs).setGroupMsgs(groupMsgs);
    }

    @Override
    public void delOfflinePrivateMsg(List<OfflinePrivateMsg> offlinePrivateMsgs) {
        if (offlinePrivateMsgs!=null){
            for (OfflinePrivateMsg msg:offlinePrivateMsgs){
                offlinePrivateMsgMapper.deleteByPrimaryKey(msg.getId());
            }
        }
    }

    @Override
    public void delOfflineGroupMsg(List<OfflineGroupMsg> offlineGroupMsgs) {
        if (offlineGroupMsgs!=null){
            for (OfflineGroupMsg msg:offlineGroupMsgs){
                offlineGroupMsgMapper.deleteByPrimaryKey(msg.getId());
            }
        }
    }



    @Override
    public MsgGroup pullOfflineMsgWithGroup(String uid) {
        MsgGroup group = new MsgGroup();

        List<OfflinePrivateMsg> offlinePrivateMsgs = offlinePrivateMsgMapper.select(new OfflinePrivateMsg().setDestUid(uid));
        List<PrivateMsgGroup> privateMsgGroups = genPrivateMsgGroup(offlinePrivateMsgs);
        List<OfflineGroupMsg> offlineGroupMsgs = offlineGroupMsgMapper.select(new OfflineGroupMsg().setDestUid(uid));
        List<GroupMsgGroup> groupMsgGroups = genGroupMsgGroup(offlineGroupMsgs);
        group.setPrivateMsgGroups(privateMsgGroups);
        group.setGroupMsgGroups(groupMsgGroups);
        delOfflinePrivateMsg(offlinePrivateMsgs);
        delOfflineGroupMsg(offlineGroupMsgs);
        return group;
    }

    @Override
    public List<OfflinePrivateMsg> pullOfflinePrivateMsgWithGroup(String uid, String srcUid) {
        List<OfflinePrivateMsg> msgList = offlinePrivateMsgMapper.select(new OfflinePrivateMsg().setSrcUid(srcUid)
                .setDestUid(uid));
        Collections.sort(msgList);
        delOfflinePrivateMsg(msgList);
        return msgList;
    }


    @Override
    public List<OfflineGroupMsg> pullOfflineGroupMsgWithGroup(String uid, Long groupId) {
        List<OfflineGroupMsg> msgList = offlineGroupMsgMapper.select(new OfflineGroupMsg().setDestUid(uid)
                .setGroupId(groupId));
        Collections.sort(msgList);
        delOfflineGroupMsg(msgList);
        return msgList;
    }

    @Override
    public MsgGroup getOfflineMsgInfo(String uid) {
        List<OfflinePrivateMsg> privateMsgs = offlinePrivateMsgMapper.getLatestOfflineMsg(uid);
        List<OfflineGroupMsg> groupMsgs = offlineGroupMsgMapper.getLatestOfflineMsg(uid);
        List<PrivateMsgGroup> privateMsgGroups = genPrivateMsgGroup(privateMsgs);
        List<GroupMsgGroup> groupMsgGroups = genGroupMsgGroup(groupMsgs);
        for (PrivateMsgGroup privateMsgGroup:privateMsgGroups){
            privateMsgGroup.setUnPull(offlinePrivateMsgMapper.selectCount(new OfflinePrivateMsg().setSrcUid(privateMsgGroup.getSrcUid())));
        }
        for (GroupMsgGroup groupMsgGroup:groupMsgGroups){
            groupMsgGroup.setUnPull(offlineGroupMsgMapper.selectCount(new OfflineGroupMsg().setGroupId(groupMsgGroup.getGroupId())));
        }
        return new MsgGroup().setPrivateMsgGroups(privateMsgGroups).setGroupMsgGroups(groupMsgGroups);
    }

    private List<PrivateMsgGroup> genPrivateMsgGroup(List<OfflinePrivateMsg> msgList){
        List<PrivateMsgGroup> groups = new LinkedList<>();

        if (msgList!=null){
            //分类
            Collections.sort(msgList);
            Map<String, PrivateMsgGroup> uidMap = new HashMap<>();
            for (OfflinePrivateMsg msg:msgList){
                PrivateMsgGroup msgGroup = uidMap.get(msg.getSrcUid());
                if (msgGroup==null){
                    msgGroup = new PrivateMsgGroup().setSrcUid(msg.getSrcUid());
                    uidMap.put(msg.getSrcUid(),msgGroup);
                    groups.add(msgGroup);
                    msgGroup.setUnPull(0);
                }
                msgGroup.addMsg(msg);
            }
        }
        return groups;
    }

    private List<GroupMsgGroup> genGroupMsgGroup(List<OfflineGroupMsg> msgList){
        List<GroupMsgGroup> groups = new LinkedList<>();
        if (msgList!=null){
            //分类
            Collections.sort(msgList);
            Map<Long, GroupMsgGroup> gidMap = new HashMap<>();
            for (OfflineGroupMsg msg:msgList){
                GroupMsgGroup msgGroup = gidMap.get(msg.getGroupId());
                if (msgGroup==null){
                    msgGroup = new GroupMsgGroup().setGroupId(msg.getGroupId());
                    gidMap.put(msg.getGroupId(),msgGroup);
                    groups.add(msgGroup);
                    msgGroup.setUnPull(0);
                }
                msgGroup.addMsg(msg);
            }
        }
        return groups;
    }


}

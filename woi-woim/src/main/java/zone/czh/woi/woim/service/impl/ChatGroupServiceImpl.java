package zone.czh.woi.woim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import zone.czh.woi.base.util.CommonUtil;
import zone.czh.woi.woim.base.constant.MapperProperties;
import zone.czh.woi.woim.base.obj.po.ChatGroup;
import zone.czh.woi.woim.base.obj.po.GroupMember;
import zone.czh.woi.woim.base.obj.vo.ChatGroupMod;
import zone.czh.woi.woim.generator.GroupIdGenerator;
import zone.czh.woi.woim.mapper.ChatGroupMapper;
import zone.czh.woi.woim.mapper.GroupMemberMapper;
import zone.czh.woi.woim.obj.pojo.ChatGroupAuthority;
import zone.czh.woi.woim.service.inter.ChatGroupService;

import java.util.*;

/**
*@ClassName: ChatGroupServiceImpl
*@Description: None
*@author woi
*/

public class ChatGroupServiceImpl implements ChatGroupService {

    static Map<Integer, Set<ChatGroupAuthority>> chatGroupAuthorityMap;
    static {
        chatGroupAuthorityMap = new HashMap<>();
        HashSet<ChatGroupAuthority> adminAuthorities = new HashSet<>();
        adminAuthorities.add(ChatGroupAuthority.UPD_CHAT_GROUP);
        adminAuthorities.add(ChatGroupAuthority.ADD_MEMBER);
        adminAuthorities.add(ChatGroupAuthority.REMOVE_MEMBER);
        HashSet<ChatGroupAuthority> ownerAuthorities = new HashSet<>(adminAuthorities);
        ownerAuthorities.add(ChatGroupAuthority.REMOVE_CHAT_GROUP);
        ownerAuthorities.add(ChatGroupAuthority.PROMOTE);
        ownerAuthorities.add(ChatGroupAuthority.DEMOTE);
        ownerAuthorities.add(ChatGroupAuthority.TRANSFER_CHAT_GROUP);
        chatGroupAuthorityMap.put(GroupMember.ROLE_OWNER,ownerAuthorities);
        chatGroupAuthorityMap.put(GroupMember.ROLE_ADMIN,adminAuthorities);
    }

    @Autowired
    ChatGroupMapper chatGroupMapper;

    @Autowired
    GroupMemberMapper groupMemberMapper;

    @Autowired
    GroupIdGenerator groupIdGenerator;

    @Transactional
    @Override
    public void createChatGroup(String ownerUid, String ownerGroupNickName, ChatGroupMod chatGroupMod) {
        GroupMember owner = new GroupMember().setUid(ownerUid).setGroupNickName(ownerGroupNickName);
        createChatGroup(owner, chatGroupMod);
    }

    @Transactional
    @Override
    public void createChatGroup(GroupMember owner, ChatGroupMod chatGroupMod) {
        CommonUtil.checkNull(owner.getUid());
        ChatGroup chatGroup = new ChatGroup().setId(groupIdGenerator.nextGroupId())
                .setName(chatGroupMod.getName())
                .setAvatar(chatGroupMod.getAvatar())
                .setDescription(chatGroupMod.getDescription());
        chatGroupMapper.insertSelective(chatGroup);
        owner.setGroupId(chatGroup.getId()).setRole(GroupMember.ROLE_OWNER);
        groupMemberMapper.insertSelective(owner);
    }

    @Override
    public void updChatGroup(ChatGroupMod chatGroupMod) {
        CommonUtil.checkNull(chatGroupMod.getId());
        ChatGroup chatGroup = new ChatGroup().setId(chatGroupMod.getId())
                .setName(chatGroupMod.getName())
                .setAvatar(chatGroupMod.getAvatar())
                .setDescription(chatGroupMod.getDescription());
        chatGroupMapper.updateByPrimaryKeySelective(chatGroup);
    }

    @Override
    public void removeChatGroup(long groupChatId) {
        //todo 考虑移除方式，不直接删除，而是修改状态
        chatGroupMapper.deleteByPrimaryKey(groupChatId);
    }


    @Override
    public void addGroupMember(long groupId,String uid,String groupNickName){
        GroupMember groupMember = new GroupMember().setGroupId(groupId).setUid(uid);
        if (groupMemberMapper.selectCount(groupMember)==0){
            groupMember.setRole(GroupMember.ROLE_MEMBER);
            groupMember.setGroupNickName(groupNickName);
            groupMemberMapper.insertSelective(groupMember);
        }
    }

    @Override
    public void removeGroupMember(long groupId,String uid){
        GroupMember groupMember = new GroupMember().setGroupId(groupId).setUid(uid);
        GroupMember member = groupMemberMapper.selectOne(groupMember);
        if (member==null||member.getRole()==GroupMember.ROLE_OWNER||member.getRole()==GroupMember.ROLE_ADMIN){
            return;
        }
        groupMemberMapper.deleteByPrimaryKey(member);
    }
    @Override
    public void promoteGroupMember(long groupId,String uid){
        GroupMember groupMember = groupMemberMapper.selectOne(new GroupMember().setGroupId(groupId).setUid(uid));
        if (groupMember!=null&&groupMember.getRole()!=GroupMember.ROLE_OWNER){
            groupMember.setRole(GroupMember.ROLE_ADMIN);
            groupMemberMapper.updateByPrimaryKeySelective(groupMember);
        }
    }
    @Override
    public void demoteGroupMember(long groupId,String uid){
        GroupMember groupMember = groupMemberMapper.selectOne(new GroupMember().setGroupId(groupId).setUid(uid));
        if (groupMember!=null&&groupMember.getRole()!=GroupMember.ROLE_OWNER){
            groupMember.setRole(GroupMember.ROLE_MEMBER);
            groupMemberMapper.updateByPrimaryKeySelective(groupMember);
        }
    }
    @Transactional
    @Override
    public void transferGroup(long groupId,String toUid){
        GroupMember owner = groupMemberMapper.selectOne(new GroupMember().setGroupId(groupId).setRole(GroupMember.ROLE_OWNER));
        GroupMember member = groupMemberMapper.selectOne(new GroupMember().setGroupId(groupId).setUid(toUid));
        owner.setRole(GroupMember.ROLE_MEMBER);
        member.setRole(GroupMember.ROLE_OWNER);
        groupMemberMapper.updateByPrimaryKeySelective(owner);
        groupMemberMapper.updateByPrimaryKeySelective(member);
    }

    @Override
    public List<ChatGroup> getChatGroupList(String uid){
        HashSet<Long> groupIds = new HashSet<>();
        List<GroupMember> members = groupMemberMapper.select(new GroupMember().setUid(uid));
        for (GroupMember member:members){
            groupIds.add(member.getGroupId());
        }
        if (groupIds.isEmpty()){
            return new ArrayList<>();
        }
        Example example = new Example(ChatGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn(MapperProperties.ChatGroup.ID,groupIds);
        List<ChatGroup> chatGroups = chatGroupMapper.selectByExample(example);
        return (chatGroups==null)?(new ArrayList<>()):chatGroups;
    }

    @Override
    public boolean hasAuthority(String uid, long groupId, ChatGroupAuthority... authorities){
        CommonUtil.checkNull(uid);
        GroupMember groupMember = groupMemberMapper.selectOne(new GroupMember().setUid(uid).setGroupId(groupId));
        if (groupMember==null){
            return false;
        }
        Set<ChatGroupAuthority> authoritySet = chatGroupAuthorityMap.get(groupMember.getRole());
        for (ChatGroupAuthority authority:authorities){
            if (!authoritySet.contains(authority)){
                return false;
            }
        }
        return true;
    }
}

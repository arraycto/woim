package zone.czh.woi.woim.service.inter;

import zone.czh.woi.woim.base.obj.po.OfflineGroupMsg;
import zone.czh.woi.woim.base.obj.po.OfflinePrivateMsg;
import zone.czh.woi.woim.base.obj.vo.GroupMsg;
import zone.czh.woi.woim.base.obj.vo.MsgGroup;
import zone.czh.woi.woim.base.obj.vo.OfflineMsg;
import zone.czh.woi.woim.base.obj.vo.PrivateMsg;
import zone.czh.woi.woim.base.obj.vo.MsgState;

import java.util.List;

/**
*@ClassName: MessageService
*@Description: None
*@author woi
*/
public interface MessageService {

    MsgState sendMsg(PrivateMsg msg);

    MsgState sendMsg(GroupMsg msg);

    void offlineMsg(PrivateMsg msg);

    void offlineGroupMsg(GroupMsg msg, String destUid);

    /**
     * 拉取离线信息，按时间轴排序
     * @param uid
     * @return
     */
    OfflineMsg pullOfflineMsg(String uid);
    /**
     * 拉取所有离线消息包括私聊和群聊,并分组
     * @param uid
     * @return
     */
    MsgGroup pullOfflineMsgWithGroup(String uid);


    /**
     * 拉取指定源用户的离线消息
     * @param uid
     * @param srcUid
     * @return
     */
    List<OfflinePrivateMsg> pullOfflinePrivateMsgWithGroup(String uid, String srcUid);


    /**
     * 拉取指定群的离线群消息
     * @param uid
     * @return
     */
    List<OfflineGroupMsg> pullOfflineGroupMsgWithGroup(String uid, Long groupId);

    MsgGroup getOfflineMsgInfo(String uid);

    void delOfflinePrivateMsg(List<OfflinePrivateMsg> offlinePrivateMsgs);

    void delOfflineGroupMsg(List<OfflineGroupMsg> offlineGroupMsgs);


}

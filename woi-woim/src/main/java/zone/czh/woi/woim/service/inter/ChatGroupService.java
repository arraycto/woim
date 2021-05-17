package zone.czh.woi.woim.service.inter;

import zone.czh.woi.woim.base.obj.po.ChatGroup;
import zone.czh.woi.woim.base.obj.po.GroupMember;
import zone.czh.woi.woim.base.obj.vo.ChatGroupMod;
import zone.czh.woi.woim.obj.pojo.ChatGroupAuthority;

import java.util.List;

/**
*@ClassName: ChatGroupService
*@Description: None
*@author woi
*/
public interface ChatGroupService {
    void createChatGroup(String ownerUid, String ownerGroupNickName, ChatGroupMod chatGroupMod);
    void createChatGroup(GroupMember owner, ChatGroupMod chatGroupMod);

    void updChatGroup(ChatGroupMod chatGroupMod);
    void removeChatGroup(long groupChatId);
    void addGroupMember(long groupId,String uid,String groupNickName);
    void removeGroupMember(long groupId,String uid);
    void promoteGroupMember(long groupId,String uid);
    void demoteGroupMember(long groupId,String uid);
    void transferGroup(long groupId,String toUid);

    List<ChatGroup> getChatGroupList(String uid);

    boolean hasAuthority(String uid, long groupId, ChatGroupAuthority... authorities);

}

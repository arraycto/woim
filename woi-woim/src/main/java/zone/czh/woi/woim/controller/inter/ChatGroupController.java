package zone.czh.woi.woim.controller.inter;

import org.springframework.web.bind.annotation.RequestParam;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.woim.base.obj.vo.ChatGroupCreateMod;
import zone.czh.woi.woim.base.obj.vo.ChatGroupMod;

/**
*@ClassName: ChatGroupController
*@Description: None
*@author woi
*/
public interface ChatGroupController {
    Response createChatGroup(ChatGroupCreateMod chatGroupCreateMod);
    Response updChatGroup(ChatGroupMod chatGroupMod);
    Response removeChatGroup(Long groupId);
    Response addGroupMember(Long groupId,String uid);

    Response removeGroupMember(@RequestParam Long groupId, @RequestParam String uid);

    Response promoteGroupMember(Long groupId, String uid);
    Response demoteGroupMember(Long groupId,String uid);
    Response transferGroup(Long groupId,String toUid);
}

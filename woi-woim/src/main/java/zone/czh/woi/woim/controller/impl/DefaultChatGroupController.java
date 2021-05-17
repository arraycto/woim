package zone.czh.woi.woim.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.woim.base.obj.vo.ChatGroupCreateMod;
import zone.czh.woi.woim.base.obj.vo.ChatGroupMod;
import zone.czh.woi.woim.constant.WOIMUrl;
import zone.czh.woi.woim.controller.inter.ChatGroupController;
import zone.czh.woi.woim.service.inter.ChatGroupService;

/**
*@ClassName: DefaultChatGroupController
*@Description: None
*@author woi
*/
@RequestMapping(WOIMUrl.ChatGroup.MODULE)
@ResponseBody
public class DefaultChatGroupController implements ChatGroupController {

    @Autowired
    ChatGroupService chatGroupService;

    @RequestMapping(WOIMUrl.ChatGroup.PATH_CREATE)
    @Override
    public Response createChatGroup(@RequestBody ChatGroupCreateMod chatGroupCreateMod) {
        chatGroupService.createChatGroup(chatGroupCreateMod.getOwner(), chatGroupCreateMod.getChatGroupMod());
        return new Response<>();
    }

    @RequestMapping(WOIMUrl.ChatGroup.PATH_UPD)
    @Override
    public Response updChatGroup(@RequestBody ChatGroupMod chatGroupMod) {
        chatGroupService.updChatGroup(chatGroupMod);
        return new Response<>();
    }

    @RequestMapping(WOIMUrl.ChatGroup.PATH_REMOVE)
    @Override
    public Response removeChatGroup(@RequestParam Long groupId) {
//        get uid
//        chatGroupService.hasAuthority(uid, ChatGroupAuthority.REMOVE_CHAT_GROUP);
        chatGroupService.removeChatGroup(groupId);
        return new Response<>();

    }

    @RequestMapping(WOIMUrl.ChatGroup.PATH_ADD_MEMBER)
    @Override
    public Response addGroupMember(@RequestParam Long groupId,@RequestParam String uid) {
//        get uid
//        chatGroupService.hasAuthority(uid, ChatGroupAuthority.ADD_MEMBER);
        chatGroupService.addGroupMember(groupId,uid,null);
        return new Response<>();

    }

    @RequestMapping(WOIMUrl.ChatGroup.PATH_REMOVE_MEMBER)
    @Override
    public Response removeGroupMember(@RequestParam Long groupId, @RequestParam String uid) {
//        get uid
//        chatGroupService.hasAuthority(uid, ChatGroupAuthority.REMOVE_MEMBER);
        chatGroupService.removeGroupMember(groupId,uid);
        return new Response<>();

    }

    @RequestMapping(WOIMUrl.ChatGroup.PATH_PROMOTE_MEMBER)
    @Override
    public Response promoteGroupMember(@RequestParam Long groupId,@RequestParam String uid) {
//        get uid
//        chatGroupService.hasAuthority(uid, ChatGroupAuthority.PROMOTE);
        chatGroupService.promoteGroupMember(groupId,uid);
        return new Response<>();

    }

    @RequestMapping(WOIMUrl.ChatGroup.PATH_DEMOTE_MEMBER)
    @Override
    public Response demoteGroupMember(@RequestParam Long groupId,@RequestParam String uid) {
//        get uid
//        chatGroupService.hasAuthority(uid, ChatGroupAuthority.DEMOTE);
        chatGroupService.demoteGroupMember(groupId,uid);
        return new Response<>();

    }

    @RequestMapping(WOIMUrl.ChatGroup.PATH_TRANSFER)
    @Override
    public Response transferGroup(@RequestParam Long groupId,@RequestParam String toUid) {
//        get uid
//        chatGroupService.hasAuthority(uid, ChatGroupAuthority.TRANSFER_CHAT_GROUP);
        chatGroupService.transferGroup(groupId,toUid);
        return new Response<>();
    }
}

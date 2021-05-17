package zone.czh.woi.woim.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.woim.constant.WOIMUrl;
import zone.czh.woi.woim.controller.inter.MessageController;
import zone.czh.woi.woim.service.inter.MessageService;

/**
*@ClassName: MessageController
*@Description: None
*@author woi
*/

@RequestMapping(WOIMUrl.Message.MODULE)
@ResponseBody
public class DefaultMessageController implements MessageController {
    @Autowired
    MessageService messageService;

    @Override
    @PostMapping(WOIMUrl.Message.PATH_PULL_OFFLINE_MESSAGE)
    public Response pullOfflineMsg(@RequestParam String uid){
        return new Response<>(messageService.pullOfflineMsg(uid));
    }

    @Override
    @PostMapping(WOIMUrl.Message.PATH_PULL_OFFLINE_MSG_GROUP)
    public Response pullOfflineMsgWithGroup(@RequestParam String uid){
        return new Response<>(messageService.pullOfflineMsgWithGroup(uid));
    }

    @PostMapping(WOIMUrl.Message.PATH_OFFLINE_PRIVATE_MSG)
    @Override
    public Response pullOfflinePrivateMsgWithGroup(@RequestParam String uid, @RequestParam String srcUid) {
        return new Response<>(messageService.pullOfflinePrivateMsgWithGroup(uid,srcUid));
    }
    @PostMapping(WOIMUrl.Message.PATH_PULL_OFFLINE_GROUP_MSG)
    @Override
    public Response pullOfflineGroupMsgWithGroup(@RequestParam String uid, @RequestParam Long groupId) {
        return new Response<>(messageService.pullOfflineGroupMsgWithGroup(uid,groupId));
    }
    @PostMapping(WOIMUrl.Message.PATH_OFFLINE_MSG_INFO)
    public Response getOfflineMsgInfo(@RequestParam String uid){
        return new Response<>(messageService.getOfflineMsgInfo(uid));
    }
}

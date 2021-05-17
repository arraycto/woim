package zone.czh.woi.woim.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.woim.constant.WOIMUrl;
import zone.czh.woi.woim.controller.inter.UserController;
import zone.czh.woi.woim.service.inter.ChatGroupService;
import zone.czh.woi.woim.service.inter.ContactService;

/**
*@ClassName: DefaultUserController
*@Description: None
*@author woi
*/
@RequestMapping(WOIMUrl.User.MODULE)
@ResponseBody
public class DefaultUserController implements UserController {
    @Autowired
    ContactService contactService;
    @Autowired
    ChatGroupService chatGroupService;


    @PostMapping(WOIMUrl.User.PATH_CONTACTS_LIST)
    @Override
    public Response getContactList(@RequestParam String uid){
        return new Response<>(contactService.getContacts(uid));
    }

    @PostMapping(WOIMUrl.User.PATH_REQUEST_CONTACT)
    @Override
    public Response requestContact(@RequestParam String hostUid
            , @RequestParam String contactUid, @RequestParam String msg){
        contactService.requestContact(hostUid,contactUid,msg);
        return new Response<>();
    }

    @PostMapping(WOIMUrl.User.PATH_ACCEPT_CONTACT_REQ)
    @Override
    public Response acceptContact(@RequestParam Long contactRequestId){
        contactService.acceptContact(contactRequestId);
        return new Response<>();
    }

    @PostMapping(WOIMUrl.User.PATH_CONTACT_REQ_LIST)
    @Override
    public Response getContactRequestList(@RequestParam String uid) {
        return new Response<>(contactService.getContactRequestList(uid));
    }
    @PostMapping(WOIMUrl.User.PATH_CHAT_GROUP_LIST)
    @Override
    public Response getChatGroupList(@RequestParam String uid) {
        return new Response<>(chatGroupService.getChatGroupList(uid));
    }


}

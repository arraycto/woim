package zone.czh.woi.woim.controller.inter;

import org.springframework.web.bind.annotation.RequestParam;
import zone.czh.woi.base.web.usual.Response;

/**
*@ClassName: UserController
*@Description: None
*@author woi
*/
public interface UserController {
    Response getContactList(@RequestParam String uid);
    Response requestContact(@RequestParam String hostUid, @RequestParam String contactUid, @RequestParam String msg);
    Response acceptContact(@RequestParam Long contactRequestId);
    Response getContactRequestList(@RequestParam String uid);
    Response getChatGroupList(@RequestParam String uid);
}

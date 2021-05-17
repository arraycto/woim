package zone.czh.woi.woim.controller.inter;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.woim.base.obj.vo.PrivateMsg;

/**
*@ClassName: MessageController
*@Description: None
*@author woi
*/
public interface MessageController {

    Response pullOfflineMsg(@RequestParam String uid);

    Response pullOfflineMsgWithGroup(@RequestParam String uid);

    Response pullOfflinePrivateMsgWithGroup(@RequestParam String uid, @RequestParam String srcUid);

    Response pullOfflineGroupMsgWithGroup(@RequestParam String uid, @RequestParam Long groupId);

}

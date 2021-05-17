package zone.czh.woi.woim.controller.inter;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.woim.base.obj.po.WOIMSession;

/**
*@ClassName: SessionController
*@Description: None
*@author woi
*/
public interface SessionController {
    Response closeSession(@RequestBody WOIMSession session);
    Response kickOff(@RequestParam Long sessionId, @RequestParam String msg);
}

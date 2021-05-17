package zone.czh.woi.woim.controller.inter;

import org.springframework.web.bind.annotation.RequestBody;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.woim.base.obj.po.WOIMSession;
import zone.czh.woi.woim.base.obj.vo.PushInfo;
import zone.czh.woi.woim.base.obj.vo.SessionState;
/**
*@ClassName: DistributedController
*@Description: None
*@author woi
*/
public interface DistributedController {
    Response<SessionState> push(@RequestBody PushInfo info);
    Response closeSession(@RequestBody WOIMSession session);
}

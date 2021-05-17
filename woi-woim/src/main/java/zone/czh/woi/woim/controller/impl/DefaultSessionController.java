package zone.czh.woi.woim.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.woim.base.obj.po.WOIMSession;
import zone.czh.woi.woim.constant.WOIMUrl;
import zone.czh.woi.woim.controller.inter.SessionController;
import zone.czh.woi.woim.service.inter.SessionService;

/**
*@ClassName: SessionController
*@Description: None
*@author woi
*/
@RequestMapping(WOIMUrl.Session.MODULE)
@ResponseBody
public class DefaultSessionController implements SessionController {
    @Autowired
    SessionService sessionService;

    @PostMapping(WOIMUrl.Session.PATH_CLOSE_SESSION)
    public Response closeSession(@RequestBody WOIMSession session){
        sessionService.closeSession(session);
        return new Response<>();
    }

    @PostMapping(WOIMUrl.Session.PATH_KICK_OFF)
    public Response kickOff(@RequestParam Long sessionId, @RequestParam String msg){
        sessionService.kickOff(sessionId,msg);
        return new Response<>();
    }
}

package zone.czh.woi.woim.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zone.czh.woi.base.web.constant.ErrorCode;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.woim.base.obj.po.WOIMSession;
import zone.czh.woi.woim.base.obj.vo.PushInfo;
import zone.czh.woi.woim.base.obj.vo.SessionState;
import zone.czh.woi.woim.constant.WOIMUrl;
import zone.czh.woi.woim.controller.inter.DistributedController;
import zone.czh.woi.woim.service.inter.PushService;
import zone.czh.woi.woim.service.inter.SessionService;
/**
*@ClassName: DefaultDistributedController
*@Description: None
*@author woi
*/
@RequestMapping(WOIMUrl.Distributed.MODULE)
@ResponseBody
public class DefaultDistributedController implements DistributedController {
    @Autowired
    public PushService pushService;
    @Autowired
    SessionService sessionService;
    @RequestMapping(WOIMUrl.Distributed.PATH_PUSH)
    @Override
    public Response<SessionState> push(PushInfo info) {
        try {
            return new Response<>(pushService.pushDistributed(info.getSession(),info.parseData()));
        }catch (Exception e){
            e.printStackTrace();
            return new Response<SessionState>().setCode(ErrorCode.CODE_GENERAL_ERR).setMessage(ErrorCode.MESSAGE_GENERAL_ERR);
        }
    }

    @PostMapping(WOIMUrl.Distributed.PATH_CLOSE_SESSION)
    @Override
    public Response closeSession(@RequestBody WOIMSession session){
        sessionService.closeSession(session);
        return new Response<>();
    }
}

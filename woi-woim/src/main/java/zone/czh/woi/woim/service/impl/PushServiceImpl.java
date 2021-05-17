package zone.czh.woi.woim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import zone.czh.woi.spring.base.util.WoiNetUtil;
import zone.czh.woi.woim.base.obj.po.WOIMSession;
import zone.czh.woi.woim.base.obj.vo.SessionState;
import zone.czh.woi.woim.distributed.agent.Agent;
import zone.czh.woi.woim.server.WOIMServer;
import zone.czh.woi.woim.service.inter.PushService;
import zone.czh.woi.woim.service.inter.SessionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*@ClassName: PushServiceImpl
*@Description: None
*@author woi
*/

public class PushServiceImpl implements PushService {
    @Autowired
    @Lazy
    WOIMServer woimServer;
    @Autowired
    SessionService sessionService;
    @Autowired
    Agent agent;



    @Override
    public SessionState push(String cid, Object data) {
        return push(cid, data,null);
    }
    @Override
    public SessionState push(String cid,Object data,OfflineHandler offlineHandler) {
        if (!woimServer.push(cid,data)){
            handleOffline(offlineHandler);
            return new SessionState(SessionState.OFFLINE);
        }
        return new SessionState(SessionState.ONLINE);
    }

    @Override
    public void pushDistributed(String uid, Object data) {
        pushDistributed(uid,data,null);
    }

    @Override
    public void pushDistributed(String uid, Object data, OfflineHandler offlineHandler) {
        List<WOIMSession> sessions = sessionService.getSessions(uid);

        if (sessions==null||sessions.size()==0){
            handleOffline(offlineHandler);
            return;
        }else {
            for (WOIMSession session:sessions){
                pushDistributed(session,data,offlineHandler);
            }
            return;
        }

    }

    @Override
    public SessionState pushDistributed(WOIMSession session, Object data) {
        return pushDistributed(session,data,null);
    }

    @Override
    public SessionState pushDistributed(WOIMSession session, Object data, OfflineHandler offlineHandler) {
        if (session!=null){
            if (WoiNetUtil.isLocal(session.getHostIp())){
                return push(session.getCid(),data,offlineHandler);
            }else {
                return callRemotePushService(session,data,offlineHandler);
            }
        }else {
            handleOffline(offlineHandler);
            return new SessionState(SessionState.OFFLINE);
        }
    }

    @Override
    public SessionState callRemotePushService(WOIMSession session, Object data, OfflineHandler offlineHandler) {
        try {
            Map<Agent.Key,Object> params = new HashMap<>();
            params.put(Agent.Key.PUSH_KEY_SESSION,session);
            params.put(Agent.Key.PUSH_KEY_DATA,data);
            SessionState state = agent.call(session.getHostIp(), Agent.Service.PUSH, params, SessionState.class);
            if (state.getState()==SessionState.OFFLINE){
                handleOffline(offlineHandler);
            }
            return state;

        }catch (Exception e){
            e.printStackTrace();
            handleOffline(offlineHandler);
            return new SessionState(SessionState.OFFLINE);
        }
    }

    private void handleOffline(OfflineHandler offlineHandler){
        if (offlineHandler !=null){
            offlineHandler.handleOffline();
        }
    }




}

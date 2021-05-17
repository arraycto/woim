package zone.czh.woi.woim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import zone.czh.woi.base.util.CommonUtil;
import zone.czh.woi.spring.base.util.WoiNetUtil;
import zone.czh.woi.woim.base.constant.SystemIntent;
import zone.czh.woi.woim.base.obj.po.WOIMSession;
import zone.czh.woi.woim.base.obj.vo.WOIMIntent;
import zone.czh.woi.woim.distributed.agent.Agent;
import zone.czh.woi.woim.listener.WOIMEventListener;
import zone.czh.woi.woim.mapper.WOIMSessionMapper;
import zone.czh.woi.woim.server.WOIMServer;
import zone.czh.woi.woim.service.inter.PushService;
import zone.czh.woi.woim.service.inter.SessionService;
import zone.czh.woi.woim.session.storage.LocalSessionStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*@ClassName: SessionServiceImpl
*@Description: None
*@author woi
*/

public class SessionServiceImpl implements SessionService {
    @Autowired
    private WOIMServer woimServer;
    @Autowired
    private WOIMSessionMapper woimSessionMapper;
    @Autowired
    private PushService pushService;
    @Autowired
    private WOIMEventListener woimEventListener;
    @Autowired
    private Agent agent;

//    private Map<String,Map<String,WOIMSession>> tempSessionStorage = new ConcurrentHashMap<>();
    @Autowired
    private LocalSessionStorage localSessionStorage;
    @Override
    public List<WOIMSession> getSessions(String uid){
        return woimSessionMapper.select(new WOIMSession().setUid(uid));
    }

    @Override
    public WOIMSession getSession(String uid, String cid) {
//        Map<String, WOIMSession> userSessionMap = tempSessionStorage.get(uid);
//        WOIMSession session = null;
//
//        if (userSessionMap!=null){
//            //说明本地存在此用户的会话信息，但不确定是不是对应的channel
//            session = userSessionMap.get(cid);
//        }
        WOIMSession session = localSessionStorage.getSession(uid, cid);
        if (session==null){
            //本地不存在，则查数据库
            session = woimSessionMapper.selectOne(new WOIMSession().setUid(uid).setCid(cid));
        }
        return session;
    }


    /**
     * 确保是本地会话
     * @param session
     */
    @Transactional
    @Override
    public void createSession(WOIMSession session) {
        if (session!=null){
//            String uid = session.getUid();
//            String cid = session.getCid();
//            Map<String, WOIMSession> userSessionMap = tempSessionStorage.get(uid);
//            if (userSessionMap==null){
//                userSessionMap = new ConcurrentHashMap<>();
//            }
//            userSessionMap.put(cid,session);
//            tempSessionStorage.put(session.getUid(),userSessionMap);
            localSessionStorage.storeSession(session);
            woimSessionMapper.insertSelective(session);
        }
    }

    @Transactional
    @Override
    public void closeSession(WOIMSession session) {
        if (session!=null){
            String hostIp = session.getHostIp();
            if (WoiNetUtil.isLocal(hostIp)){
                removeWOIMSession(session);
                closeChannel(session.getCid());
            }else {
                try {
                    Map<Agent.Key,Object> params = new HashMap<>();
                    params.put(Agent.Key.CLOSE_SESSION_KEY_WOIMSESSION,session);
                    agent.call(session.getHostIp(),Agent.Service.CLOSE_SESSION,params,void.class);
                }catch (Exception e){
                    e.printStackTrace();
                    return;
                }

            }
            woimEventListener.onSessionClosed(session);
        }
    }



    @Override
    public void closeSession(String uid,String cid) {
        WOIMSession session = getSession(uid, cid);
        closeSession(session);
    }

    /**
     * 关闭本地channel
     * 确保是本地的channel
     * @param cid
     */
    @Override
    public void closeChannel(String cid) {
        woimServer.closeChannel(cid);
    }


    @Override
    public void removeWOIMSession(WOIMSession session) {
        String uid = session.getUid();
        String cid = session.getCid();
        CommonUtil.checkNull(uid,cid);
        //先删除数据库的数据
        woimSessionMapper.delete(new WOIMSession().setUid(uid).setCid(cid));
        //todo 从redis中删除session信息
//        Map<String, WOIMSession> userSessionMap = tempSessionStorage.get(uid);
//        userSessionMap.remove(cid);
        localSessionStorage.removeSession(session);
    }

    @Override
    public void kickOff(String uid, Integer channelType) {
        kickOff(uid,channelType,null);
    }

    @Override
    public void kickOff(String uid,Integer channelType, String msg) {
        List<WOIMSession> sessions = getSessions(uid);
        if (sessions!=null){
            for (WOIMSession session:sessions){
                if (channelType.equals(session.getChannelType())){
                    kickOff(session,msg);
                }
            }
        }
    }

    @Override
    public void kickOff(Long sessionId, String msg) {
        WOIMSession session = woimSessionMapper.selectByPrimaryKey(sessionId);
        kickOff(session,msg);
    }

    @Override
    public void kickOff(WOIMSession session, String msg) {
        if (session!=null){
            WOIMIntent intent = new WOIMIntent().setCmd(SystemIntent.CMD_KICK_OFF);
            if (msg==null){
                msg = "下线提醒";
            }
            intent.putExtra(SystemIntent.KEY_KICK_OFF_MSG,msg);
            pushService.pushDistributed(session,intent);
            closeSession(session);
        }
    }
}

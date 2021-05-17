package zone.czh.woi.woim.session.storage;

import zone.czh.woi.base.util.CommonUtil;
import zone.czh.woi.woim.base.obj.po.WOIMSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
*@ClassName: MapLocalSessionStorage
*@Description: None
*@author woi
*/
public class MapLocalSessionStorage implements LocalSessionStorage{
    private Map<String, Map<String,WOIMSession>> sessionStorage = new ConcurrentHashMap<>();
    @Override
    public boolean exist(WOIMSession session) {
        if (session==null){
            return false;
        }
        return getSession(session.getUid(),session.getCid())==null;
    }

    @Override
    public void storeSession(WOIMSession session) {
        CommonUtil.checkNull(session);
        String uid = session.getUid();
        String cid = session.getCid();
        CommonUtil.checkNull(uid,cid);
        Map<String, WOIMSession> userSessionMap = sessionStorage.get(uid);
        if (userSessionMap==null){
            userSessionMap = new ConcurrentHashMap<>();
        }
        userSessionMap.put(cid,session);
        sessionStorage.put(uid,userSessionMap);
    }

    @Override
    public void removeSession(WOIMSession session) {
        CommonUtil.checkNull(session);
        String uid = session.getUid();
        String cid = session.getCid();
        CommonUtil.checkNull(uid,cid);
        Map<String, WOIMSession> userSessionMap = sessionStorage.get(uid);
        CommonUtil.checkNull(userSessionMap);
        userSessionMap.remove(cid);
    }

    @Override
    public WOIMSession getSession(String uid, String cid) {
        WOIMSession session = null;
        Map<String, WOIMSession> userSessionMap = sessionStorage.get(uid);
        if (userSessionMap!=null){
            //说明本地存在此用户的会话信息，但不确定是不是对应的channel
            session = userSessionMap.get(cid);
        }
        return session;
    }
}

package zone.czh.woi.woim.service.inter;

import zone.czh.woi.woim.base.obj.po.WOIMSession;
import zone.czh.woi.woim.base.obj.vo.SessionState;

/**
*@ClassName: PushService
*@Description: None
*@author woi
*/
public interface PushService {
    SessionState push(String cid, Object data);
    SessionState push(String cid, Object data, OfflineHandler offlineHandler);
    void pushDistributed(String uid, Object data);
    void pushDistributed(String uid, Object data, OfflineHandler offlineHandler);
    SessionState pushDistributed(WOIMSession session, Object data);
    SessionState pushDistributed(WOIMSession session, Object data, OfflineHandler offlineHandler);
    SessionState callRemotePushService(WOIMSession session, Object data, OfflineHandler offlineHandler);
    interface OfflineHandler {
        void handleOffline();
    }
}

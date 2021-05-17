package zone.czh.woi.woim.service.inter;

import zone.czh.woi.woim.base.obj.po.WOIMSession;

import java.util.List;

/**
*@ClassName: SessionService
*@Description: None
*@author woi
*/
public interface SessionService {

    List<WOIMSession> getSessions(String uid);

    WOIMSession getSession(String uid, String cid);

    void createSession(WOIMSession session);

    void closeSession(WOIMSession session);


    void closeSession(String uid, String cid);

    void closeChannel(String cid);

    void removeWOIMSession(WOIMSession session);

    void kickOff(String uid, Integer channelType);

    void kickOff(String uid, Integer channelType, String msg);

    void kickOff(Long sessionId, String msg);

    void kickOff(WOIMSession session, String msg);

}

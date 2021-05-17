package zone.czh.woi.woim.session.storage;

import zone.czh.woi.woim.base.obj.po.WOIMSession;
/**
*@ClassName: LocalSessionStorage
*@Description: None
*@author woi
*/
public interface LocalSessionStorage {
    boolean exist(WOIMSession session);
    void storeSession(WOIMSession session);
    void removeSession(WOIMSession session);
    WOIMSession getSession(String uid, String cid);
}

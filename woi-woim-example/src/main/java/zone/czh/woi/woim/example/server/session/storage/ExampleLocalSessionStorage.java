package zone.czh.woi.woim.example.server.session.storage;

import org.springframework.stereotype.Component;
import zone.czh.woi.woim.base.obj.po.WOIMSession;
import zone.czh.woi.woim.session.storage.LocalSessionStorage;
//默认使用map存储session信息，支持使用redis存储(详见Config中的LocalSessionStorage配置)，也可以自行实现LocalSessionStorage接口
@Component
public class ExampleLocalSessionStorage implements LocalSessionStorage {
    @Override
    public boolean exist(WOIMSession session) {
        return false;
    }

    @Override
    public void storeSession(WOIMSession session) {

    }

    @Override
    public void removeSession(WOIMSession session) {

    }

    @Override
    public WOIMSession getSession(String uid, String cid) {
        return null;
    }
}

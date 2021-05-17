package zone.czh.woi.woim.distributed.agent;

import java.util.Map;
/**
*@ClassName: Agent
*@Description: None
*@author woi
*/
public interface Agent {
    <T> T call(String hostIp, Service service, Map<Key, Object> params, Class<T> resType) throws Exception;

    enum Service{
        PUSH,
        CLOSE_SESSION
    }
    enum Key{
        PUSH_KEY_SESSION,
        PUSH_KEY_DATA,
        CLOSE_SESSION_KEY_WOIMSESSION
    }
}

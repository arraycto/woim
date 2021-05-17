package zone.czh.woi.woim.session.storage;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import zone.czh.woi.base.util.CommonUtil;
import zone.czh.woi.woim.base.obj.po.WOIMSession;
import zone.czh.woi.spring.base.util.RedisUtil;
/**
*@ClassName: RedisLocalSessionStorage
*@Description: None
*@author woi
*/
@AllArgsConstructor
public class RedisLocalSessionStorage implements LocalSessionStorage{
    public static final String KEY_PREFIX = "woimsession.";
    private RedisUtil redisUtil;
    public RedisLocalSessionStorage(RedisConnectionFactory factory){
        redisUtil=new RedisUtil(factory);
    }
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
        redisUtil.hset(getKey(uid),cid,session);
    }

    @Override
    public void removeSession(WOIMSession session) {
        CommonUtil.checkNull(session);
        String uid = session.getUid();
        String cid = session.getCid();
        CommonUtil.checkNull(uid,cid);
        redisUtil.hdel(getKey(uid),cid);
    }

    @Override
    public WOIMSession getSession(String uid, String cid) {
        return (WOIMSession) redisUtil.hget(uid,cid);
    }

    private String getKey(String key){
        return KEY_PREFIX+key;
    }
}

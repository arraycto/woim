package zone.czh.woi.woim.server.util;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
*@ClassName: AttributeKeyUtil
*@Description: None
*@author woi
*/
public class AttributeKeyUtil {
    public static <T> T get(Channel channel, String key, Class<T> clzz){
        AttributeKey<T> sid = AttributeKey.valueOf(key);
        return channel.attr(sid).get();
    }

    public static <T> void set(Channel channel, String key, T value){
        AttributeKey<T> sid = AttributeKey.valueOf(key);
        channel.attr(sid).set(value);
    }
}

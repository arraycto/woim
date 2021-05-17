package zone.czh.woi.woim.listener;

import io.netty.channel.Channel;
import zone.czh.woi.woim.base.obj.po.WOIMSession;

/**
*@ClassName: WOIMEventListener
*@Description: None
*@author woi
*/
public interface WOIMEventListener {
    public void onSessionClosed(WOIMSession session);

    /**
     *
     * @param channel
     * @param token
     * @return kick off intent
     */
    public WOIMServerEventListener.KickOffIntent onBeforeChannelBeingManaged(Channel channel, Object token);
    public void onSessionCreated(Channel channel, Object token);
}

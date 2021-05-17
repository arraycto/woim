package zone.czh.woi.woim.listener;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import zone.czh.woi.woim.base.obj.po.WOIMSession;
import zone.czh.woi.woim.service.inter.SessionService;

/**
*@ClassName: DefaultWOIMEventListener
*@Description: None
*@author woi
*/
@AllArgsConstructor
public class DefaultWOIMEventListener implements WOIMEventListener{
    SessionService sessionService;


    @Override
    public void onSessionClosed(WOIMSession session) {

    }

    @Override
    public WOIMServerEventListener.KickOffIntent onBeforeChannelBeingManaged(Channel channel, Object token) {
        return new WOIMServerEventListener.KickOffIntent(true,"您的账号在别的设备上登录");
    }

    @Override
    public void onSessionCreated(Channel channel, Object token) {

    }
}

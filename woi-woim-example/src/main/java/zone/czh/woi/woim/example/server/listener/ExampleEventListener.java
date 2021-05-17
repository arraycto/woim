package zone.czh.woi.woim.example.server.listener;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;
import zone.czh.woi.woim.listener.WOIMEventListener;
import zone.czh.woi.woim.listener.WOIMServerEventListener;
import zone.czh.woi.woim.base.obj.po.WOIMSession;

/**
*@ClassName: ExampleEventListener
*@Description: 全局事件监听器
*@author woi
*/
@Component//别忘记注入spring容器
public class ExampleEventListener implements WOIMEventListener {


    @Override
    public void onSessionClosed(WOIMSession session) {
        //你可以在这里实现类似通知好友下线的功能逻辑
    }

    @Override
    public WOIMServerEventListener.KickOffIntent onBeforeChannelBeingManaged(Channel channel, Object token) {
        //处理连接重置
        return new WOIMServerEventListener.KickOffIntent(true,"您的账号在另一台设备登录");
    }

    @Override
    public void onSessionCreated(Channel channel, Object token) {
        //你可以在这里实现类似通知好友上线的功能逻辑
    }

}

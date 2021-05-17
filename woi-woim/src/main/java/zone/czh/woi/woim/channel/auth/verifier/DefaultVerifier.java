package zone.czh.woi.woim.channel.auth.verifier;

import io.netty.channel.Channel;
import zone.czh.woi.woim.base.constant.ChannelCst;
import zone.czh.woi.woim.base.obj.vo.Packet;

/**
*@ClassName: DefaultVerifier
*@Description: None
*@author woi
*/
public class DefaultVerifier extends WOIMVerifier {
    @Override
    public String verify(Channel channel, Packet packet) {
        uid = channel.id().asLongText();
        channelType= ChannelCst.Type.DESKTOP;
        return channel.id().asLongText();
    }
}

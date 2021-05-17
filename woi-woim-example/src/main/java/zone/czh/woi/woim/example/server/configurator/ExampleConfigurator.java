package zone.czh.woi.woim.example.server.configurator;

import org.springframework.stereotype.Component;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.base.constant.ChannelCst;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.channel.auth.verifier.WOIMVerifier;
import zone.czh.woi.woim.configurator.WOIMConfigurator;

import java.util.List;
import java.util.UUID;
/**
*@ClassName: ExampleConfigurator
*@Description: 配置WOIM并注入spring容器
*@author woi
*/

@Component//todo 别忘记注入spring容器
public class ExampleConfigurator implements WOIMConfigurator {

    @Override
    public WOIMVerifier getChannelVerifier() {
        //todo 你需要new一个verifier除非你能确保它是线程安全的
        //此处以匿名类作为示例
        return new WOIMVerifier() {
            @Override
            public String verify(io.netty.channel.Channel channel, Packet packet) {
                //todo do not forget to set uid and channel type, they will be bound to the channel and used to generated woimsession
                //todo 别忘记设置uid和通道类型，他会与通道进行绑定并用以生成WOIMSession
                uid = "uid";
                channelType = ChannelCst.Type.DESKTOP;
                //你也可以在此设置记录客户端的os版本信息
                osName="osname";
                osVersion="osversion";
                //
                return UUID.randomUUID().toString();
            }

            @Override
            public String getUidAfterVerify() {
                return uid;
            }

            @Override
            public int getChannelTypeAfterVerify() {
                return channelType;
            }
        };
    }

    @Override
    public List<PacketHandler> getCustomHandler() {
        //如果你有自定义推送消息类型需要处理可以在此处配置你的handler
        return null;
    }
}

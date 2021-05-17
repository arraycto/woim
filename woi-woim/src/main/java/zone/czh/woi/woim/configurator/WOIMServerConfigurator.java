package zone.czh.woi.woim.configurator;

import lombok.AllArgsConstructor;
import zone.czh.woi.spring.base.util.BeanUtil;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.channel.msg.DistributedMsgService;
import zone.czh.woi.woim.server.WOIMServer;
import zone.czh.woi.woim.server.channel.handler.AuthHandler;
import zone.czh.woi.woim.server.channel.handler.GroupMsgHandler;
import zone.czh.woi.woim.server.channel.handler.PrivateMsgHandler;

import java.util.List;

/**
*@ClassName: WOIMServerConfigurator
*@Description: None
*@author woi
*/
@AllArgsConstructor
public class WOIMServerConfigurator implements WOIMServer.Configurator {
    private WOIMConfigurator woimConfigurator;

    @Override
    public AuthHandler.Verifier getChannelVerifier() {
        return woimConfigurator.getChannelVerifier();
    }


    @Override
    public PrivateMsgHandler.MsgService getPrivateMsgService() {
        return BeanUtil.getBean(DistributedMsgService.class);
    }

    @Override
    public GroupMsgHandler.MsgService getGroupMsgService() {
        return BeanUtil.getBean(DistributedMsgService.class);
    }

    @Override
    public List<PacketHandler> getCustomHandler() {
        return woimConfigurator.getCustomHandler();
    }
}

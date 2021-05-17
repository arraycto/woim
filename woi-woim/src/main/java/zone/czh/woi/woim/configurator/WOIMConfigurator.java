package zone.czh.woi.woim.configurator;

import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.channel.auth.verifier.WOIMVerifier;

import java.util.List;

/**
*@ClassName: WOIMConfigurator
*@Description: None
*@author woi
*/
public interface WOIMConfigurator {
    public WOIMVerifier getChannelVerifier();

    public List<PacketHandler> getCustomHandler();
}

package zone.czh.woi.woim.configurator;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.channel.auth.verifier.DefaultVerifier;
import zone.czh.woi.woim.channel.auth.verifier.WOIMVerifier;

import java.util.List;

/**
*@ClassName: SimpleWOIMConfigurator
*@Description: None
*@author woi
*/
public class SimpleWOIMConfigurator implements WOIMConfigurator{

    @Override
    public WOIMVerifier getChannelVerifier() {
        return new DefaultVerifier();
    }

    @Override
    public List<PacketHandler> getCustomHandler() {
        return null;
    }
}

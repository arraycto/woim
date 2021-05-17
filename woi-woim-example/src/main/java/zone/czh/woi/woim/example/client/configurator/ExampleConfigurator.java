package zone.czh.woi.woim.example.client.configurator;

import lombok.AllArgsConstructor;
import lombok.Data;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.client.WOIMClient;

import java.util.List;

/**
*@ClassName: ExampleConfigurator
*@Description: 配置客户端
*@author woi
*/
@Data
public class ExampleConfigurator implements WOIMClient.Configurator {

    @Override
    public List<PacketHandler> getCustomHandler() {
        return null;
    }


}

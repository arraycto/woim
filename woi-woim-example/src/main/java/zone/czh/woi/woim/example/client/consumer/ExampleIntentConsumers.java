package zone.czh.woi.woim.example.client.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zone.czh.woi.woim.base.annotation.intent.IntentConsumer;
import zone.czh.woi.woim.base.constant.SystemIntent;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.base.obj.vo.WOIMIntent;
import zone.czh.woi.woim.client.WOIMClient;

/**
*@ClassName: ExampleIntentConsumers
*@Description:
 * 处理服务端的woimintent
 * woimintent支持携带可序列化的对象
*@author woi
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExampleIntentConsumers {
    WOIMClient woimClient;

    /**
     * 方法使用@IntentConsumer注解，则此方法则会处理相应的intent
     * @param packet 注意这里是的固定参数，否则会出错
     */
    @IntentConsumer(SystemIntent.CMD_KICK_OFF)
    public void kickOff(Packet<WOIMIntent> packet){
        WOIMIntent intent = packet.getData();
        System.out.println(intent);
        System.out.println("下线提醒：");
        System.out.println(intent.getExtra("msg"));
        woimClient.disconnect();
    }
}

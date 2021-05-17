package zone.czh.woi.woim.example.client;
import zone.czh.woi.woim.base.obj.vo.GroupMsg;
import zone.czh.woi.woim.base.obj.vo.PrivateMsg;
import zone.czh.woi.woim.client.WOIMClient;
import zone.czh.woi.woim.example.client.configurator.ExampleConfigurator;
import zone.czh.woi.woim.example.client.consumer.ExampleIntentConsumers;
import zone.czh.woi.woim.example.client.listener.ExampleEventListener;

public class Client {
    public static void main(String[] args) {
        fool();
    }

    private static void fool() {
        WOIMClient client = new WOIMClient.Builder().setInetHost("127.0.0.1")
                .setPort(6666)
                .setEventListener(new ExampleEventListener())
                .setConfigurator(new ExampleConfigurator())
                .build();
        //配置IntentConsumer 处理服务端的推送的woimintent 非必须
        ExampleIntentConsumers exampleIntentConsumers = new ExampleIntentConsumers();
        exampleIntentConsumers.setWoimClient(client);
        client.addIntentHandler(exampleIntentConsumers);

        //连接服务端，此时连接未鉴权，无法使用
        client.connect();
        //使用自定义的token进行连接鉴权，前提是已连接
        client.auth("token",null,null);
        //或者使用authsafely替代上面两句
        client.authSafely("token",null,null);
        //推送数据到服务端，服务端需要配置有相应类的处理逻辑
        client.push("data");
        client.push("data",null,null,true);
        //发送私聊消息，woim服务端默认内置了PrivateMsg的处理逻辑
        client.push(new PrivateMsg());
        client.push(new GroupMsg());
    }
}

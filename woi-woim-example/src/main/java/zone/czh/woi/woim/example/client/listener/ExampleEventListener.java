package zone.czh.woi.woim.example.client.listener;

import io.netty.util.concurrent.Future;
import zone.czh.woi.woim.base.obj.vo.GroupMsg;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.base.obj.vo.PrivateMsg;
import zone.czh.woi.woim.client.WOIMClient;

/**
*@ClassName: ExampleEventListener
*@Description: 全局事件监听器
*@author woi
*/
public class ExampleEventListener implements WOIMClient.EventListener {
    @Override
    public void onNewSucceeded(WOIMClient client, Future future) {
        System.out.println("succ");
    }

    @Override
    public void onChannelClosed(WOIMClient client,Future future) {
        System.out.println("服务器异常");
    }

    @Override
    public void onRejected(WOIMClient client) {
        System.out.println("服务器异常");
    }

    @Override
    public void onReceivePrivateMsg(Packet<PrivateMsg> packet) {
        PrivateMsg msg = packet.getData();
        System.out.println(packet);
        System.out.println("收到私聊消息:");
        System.out.println(msg.getSrcUid()+":"+msg.getContent());

    }

    @Override
    public void onReceiveGroupMsg(Packet<GroupMsg> packet) {
        GroupMsg msg = packet.getData();
        System.out.println(packet);
        System.out.println("收到群聊消息:");
        System.out.println(msg.getSrcUid()+":"+msg.getContent());
    }
}

package zone.czh.woi.woim.client.handler;

import io.netty.channel.ChannelHandlerContext;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.base.obj.vo.PrivateMsg;
import zone.czh.woi.woim.client.WOIMClient;

import java.util.List;

/**
*@ClassName: PrivateMsgHandler
*@Description: None
*@author woi
*/

public class PrivateMsgHandler extends PacketHandler<PrivateMsg> {

    WOIMClient.EventListener listener;


    public PrivateMsgHandler(WOIMClient.EventListener listener) {
        super(PrivateMsg.class);
        this.listener=listener;
    }

    @Override
    protected void handle(ChannelHandlerContext ctx, Packet<PrivateMsg> packet, List<Object> out) throws Exception {
        if (listener!=null){
            listener.onReceivePrivateMsg(packet);
        }
    }


    public interface MsgService {
        void handleMsg(Packet<PrivateMsg> packet, PrivateMsg msg);
    }

}

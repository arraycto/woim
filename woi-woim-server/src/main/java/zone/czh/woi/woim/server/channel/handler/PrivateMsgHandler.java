package zone.czh.woi.woim.server.channel.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.base.obj.vo.PrivateMsg;

import java.util.List;

/**
*@ClassName: PrivateMsgHandler
*@Description: None
*@author woi
*/
@ChannelHandler.Sharable
public class PrivateMsgHandler extends PacketHandler<PrivateMsg> {

    MsgService msgService;

    public PrivateMsgHandler(MsgService msgService){
        super(PrivateMsg.class);
        this.msgService = msgService;
    }


    @Override
    protected void handle(ChannelHandlerContext ctx, Packet<PrivateMsg> packet, List<Object> out) throws Exception {
        msgService.handleMsg(packet,packet.getData());
    }


    public interface MsgService {
        void handleMsg(Packet<PrivateMsg> packet, PrivateMsg msg);
    }
}

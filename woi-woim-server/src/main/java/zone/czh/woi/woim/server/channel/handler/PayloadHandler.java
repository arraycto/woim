package zone.czh.woi.woim.server.channel.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.AttributeKey;
import zone.czh.woi.protocol.protocol.Payload;
import zone.czh.woi.protocol.protocol.WoiProtobuf;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.server.constant.AttributeKeyConstant;

import java.util.List;
import java.util.Objects;

/**
*@ClassName: PayloadHandler
*@Description: None
*@author woi
*/
@ChannelHandler.Sharable
public class PayloadHandler extends MessageToMessageDecoder<WoiProtobuf.Payload> {
    @Override
    protected void decode(ChannelHandlerContext ctx, WoiProtobuf.Payload payload, List<Object> out) throws Exception {
        if (Objects.equals(payload.getCmd(), Payload.Cmd.HEARTBEAT)) {
            System.out.println("heartbeat from:"+ctx.channel().attr(AttributeKey.valueOf(AttributeKeyConstant.USER_ID)).get());
        }else {
            out.add(new Packet(payload));
        }
    }
}

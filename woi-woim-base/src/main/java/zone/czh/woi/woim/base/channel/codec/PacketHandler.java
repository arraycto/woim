package zone.czh.woi.woim.base.channel.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;
import zone.czh.woi.woim.base.obj.vo.Packet;

import java.util.List;

@AllArgsConstructor
public abstract class PacketHandler<T> extends MessageToMessageDecoder<Packet> {
    Class<T> tClass;

    @Override
    protected final void decode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
        if (tClass.isInstance(packet.getData())){
            handle(ctx,packet,out);
        }else {
            out.add(packet);
        }
    }

    protected abstract void handle(ChannelHandlerContext ctx, Packet<T> packet, List<Object> out) throws Exception;

}

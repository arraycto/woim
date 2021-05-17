package zone.czh.woi.protocol.encoder;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import zone.czh.woi.protocol.protocol.Payload;
import zone.czh.woi.protocol.util.PayloadUtil;

import java.util.List;

@ChannelHandler.Sharable
public class PayloadEncoder extends MessageToMessageEncoder<Payload> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Payload payload, List<Object> list) throws Exception {
        list.add(PayloadUtil.buildPayload(payload));
    }
}

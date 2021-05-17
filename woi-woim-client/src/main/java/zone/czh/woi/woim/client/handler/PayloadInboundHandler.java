package zone.czh.woi.woim.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;
import zone.czh.woi.base.util.Pair;
import zone.czh.woi.protocol.protocol.Payload;
import zone.czh.woi.protocol.protocol.WoiProtobuf;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.client.WOIMClient;

import java.util.List;

/**
*@ClassName: PayloadInboundHandler
*@Description: None
*@author woi
*/
@AllArgsConstructor
public class PayloadInboundHandler extends MessageToMessageDecoder<WoiProtobuf.Payload> {
    private WOIMClient client;
    @Override
    protected void decode(ChannelHandlerContext ctx, WoiProtobuf.Payload payload, List<Object> out) throws Exception {
        Packet packet = new Packet(payload);
        if (payload.getType()== Payload.Type.RESPONSE){
            Pair<WOIMClient.ResponseHandler, Boolean> pair = client.getResponseHandlers().remove(payload.getRespondSn());
            if (pair!=null){
                WOIMClient.ResponseHandler responseHandler = pair.getKey();
                if (responseHandler!=null){
                    responseHandler.handleResponse(packet);
                }
                if (pair.getValue()){
                    return;
                }
            }
        }
        out.add(packet);
    }
}

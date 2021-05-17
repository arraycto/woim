package zone.czh.woi.woim.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.AllArgsConstructor;
import zone.czh.woi.base.util.Pair;
import zone.czh.woi.protocol.protocol.Payload;
import zone.czh.woi.woim.client.WOIMClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
*@ClassName: ReqHandler
*@Description: None
*@author woi
*/
@AllArgsConstructor
public class ReqHandler extends MessageToMessageEncoder<WOIMClient.Req> {
    private WOIMClient client;
    @Override
    protected void encode(ChannelHandlerContext ctx, WOIMClient.Req req, List<Object> out) throws Exception {
        Payload payload = req.getPayload();
        if (payload.isResponseRequired()){
            if (req.getResponseHandler()!=null){
                client.getResponseHandlers().put(payload.getSn(),new Pair(req.getResponseHandler(),req.isBlock()));
            }

            if (req.getTimeoutHandler()!=null){
                if (req.getExpiration()!=null){
                    ctx.channel().eventLoop().schedule(() -> {
                        Pair<WOIMClient.ResponseHandler, Boolean> responseHandler = client.getResponseHandlers().remove(payload.getSn());
                        if (responseHandler!=null){
                            WOIMClient.TimeoutHandler timeoutHandler = req.getTimeoutHandler();
                            if (timeoutHandler!=null){
                                timeoutHandler.handleTimeout();
                            }
                        }

                    }, req.getExpiration(), TimeUnit.MILLISECONDS);
                }
            }
        }
        out.add(payload);
    }

}

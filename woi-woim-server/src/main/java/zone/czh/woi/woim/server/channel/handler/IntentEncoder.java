package zone.czh.woi.woim.server.channel.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import zone.czh.woi.protocol.util.PayloadUtil;
import zone.czh.woi.woim.base.obj.vo.WOIMIntent;

import java.util.List;

/**
*@ClassName: IntentEncoder
*@Description: None
*@author woi
*/
@ChannelHandler.Sharable
public class IntentEncoder extends MessageToMessageEncoder<WOIMIntent> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, WOIMIntent intent, List<Object> list) throws Exception {
        //将intent封装成payload
        list.add(PayloadUtil.buildPayload(intent,false));
    }

}

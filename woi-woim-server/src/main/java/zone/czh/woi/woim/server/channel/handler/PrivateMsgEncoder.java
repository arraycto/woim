package zone.czh.woi.woim.server.channel.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import zone.czh.woi.protocol.util.PayloadUtil;
import zone.czh.woi.woim.base.obj.vo.PrivateMsg;

import java.util.List;

/**
*@ClassName: PrivateMsgEncoder
*@Description: None
*@author woi
*/
@ChannelHandler.Sharable
public class PrivateMsgEncoder extends MessageToMessageEncoder<PrivateMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, PrivateMsg msg, List<Object> list) throws Exception {
        //将msg封装成payload
        list.add(PayloadUtil.buildPayload(msg,false));
    }

}

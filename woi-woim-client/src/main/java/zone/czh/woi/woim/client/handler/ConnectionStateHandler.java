package zone.czh.woi.woim.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import zone.czh.woi.protocol.util.PayloadUtil;


/**
*@ClassName: ConnectionStateHandler
*@Description: None
*@author woi
*/
public class ConnectionStateHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            switch (((IdleStateEvent) evt).state()){
                case READER_IDLE:
                    break;
                case WRITER_IDLE:
                    ctx.channel().writeAndFlush(PayloadUtil.buildHeartbeat());
                    break;
                case ALL_IDLE:
                    break;
            }
        }
    }
}

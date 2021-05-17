package zone.czh.woi.woim.server.channel.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import zone.czh.woi.woim.base.constant.WOIMConfig;
import zone.czh.woi.woim.server.WOIMServer;
import zone.czh.woi.woim.server.constant.AttributeKeyConstant;

import java.util.concurrent.TimeUnit;

/**
*@ClassName: ConnectionStateHandler
*@Description: None
*@author woi
*/
@Data
@AllArgsConstructor
@ChannelHandler.Sharable
public class ConnectionStateHandler extends ChannelInboundHandlerAdapter {

    private WOIMServer.EventListener eventListener;

    @Override
    public void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
        //定时关闭未鉴权的连接
        ctx.channel().eventLoop().schedule(() -> {
            AttributeKey<String> cid = AttributeKey.valueOf(AttributeKeyConstant.CHANNEL_ID);
            String id = ctx.channel().attr(cid).get();
            if (id==null){
                ctx.close();
            }else {
                // TODO: 2021/4/3 log
            }

        }, WOIMConfig.AUTH_EXPIRED_SECONDS, TimeUnit.SECONDS);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        eventListener.onChannelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        eventListener.onChannelExceptionCaught(ctx,cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            switch (((IdleStateEvent) evt).state()){
                case READER_IDLE:
                    ctx.channel().close();
                    break;
                case WRITER_IDLE:
                    break;
                case ALL_IDLE:
                    break;
            }
        }
    }
}

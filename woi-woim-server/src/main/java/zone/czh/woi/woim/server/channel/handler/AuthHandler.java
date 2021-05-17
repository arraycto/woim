package zone.czh.woi.woim.server.channel.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import zone.czh.woi.protocol.protocol.Payload;
import zone.czh.woi.protocol.protocol.WoiProtobuf;
import zone.czh.woi.protocol.util.PayloadUtil;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.server.WOIMServer;
import zone.czh.woi.woim.server.constant.AttributeKeyConstant;
import zone.czh.woi.woim.server.util.AttributeKeyUtil;

/**
*@ClassName: AuthHandler
*@Description: None
*@author woi
*/
@Data
@Accessors(chain = true)
public class AuthHandler extends SimpleChannelInboundHandler<WoiProtobuf.Payload> {

    private Verifier verifier;

    private WOIMServer.EventListener eventListener;

    WOIMServer woimServer;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WoiProtobuf.Payload payload) {
        Channel channel = ctx.channel();
        if (Payload.Cmd.AUTH.equals(payload.getCmd())){
            if (verifier!=null){
                Packet packet = new Packet(payload);
                Object token = PayloadUtil.readValue(payload);
                String cid = verifier.verify(channel,packet);
                if (cid!=null){
                    AttributeKeyUtil.set(channel, AttributeKeyConstant.CHANNEL_ID,cid);
                    verifier.handleAuthSuccess(channel,packet);
                    if (eventListener!=null){
                        eventListener.onBeforeChannelBeingManaged(ctx.channel(),token);
                    }
                    woimServer.manage(channel,cid);
                    ctx.pipeline().remove(this);
                    if (eventListener!=null){
                        eventListener.onAfterChannelBeingManaged(ctx.channel(),token);
                    }
                }else {
                    verifier.handleAuthFailed(channel,packet);
                    ctx.close();
                }
            }else {
                ctx.close();
                throw new RuntimeException("verifier is null");
            }
        }else {
            ctx.channel().writeAndFlush(PayloadUtil.buildPayload("unauthorized", Payload.Cmd.SYS,false));
        }
    }

    /**
     * 需要提供无参构造器
     */
    public interface Verifier {
        /**
         * 返回值为channel标识，将存入attr中，键值为AttributeKeyConstant.CHANNEL_ID
         * @param channel
         * @param packet
         * @return channel id
         */
        String verify(Channel channel, Packet packet);


        /**
         * 授权成功 可在此对channel进行属性设置等
         * @param channel
         * @param packet
         */
        void handleAuthSuccess(Channel channel, Packet packet);

        /**
         * 授权成功 可在此对channel进行属性设置等
         * @param channel
         * @param packet
         */
        void handleAuthFailed(Channel channel, Packet packet);
    }

}

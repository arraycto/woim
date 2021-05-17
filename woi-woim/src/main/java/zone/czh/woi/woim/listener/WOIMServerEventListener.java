package zone.czh.woi.woim.listener;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.NetUtil;
import lombok.*;
import lombok.experimental.Accessors;
import zone.czh.woi.woim.base.obj.po.WOIMSession;
import zone.czh.woi.woim.server.WOIMServer;
import zone.czh.woi.woim.server.constant.AttributeKeyConstant;
import zone.czh.woi.woim.server.util.AttributeKeyUtil;
import zone.czh.woi.woim.service.inter.SessionService;

import java.net.InetSocketAddress;
import java.sql.Timestamp;

/**
*@ClassName: WOIMServerEventListener
*@Description: None
*@author woi
*/
@Getter
@Setter
@AllArgsConstructor
public class WOIMServerEventListener implements WOIMServer.EventListener {
    SessionService sessionService;

    WOIMEventListener listener;

    @Override
    public void onChannelUnregistered(ChannelHandlerContext ctx) {
        close(ctx);
    }

    @Override
    public void onChannelExceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        close(ctx);
    }

    @Override
    public void onBeforeChannelBeingManaged(Channel channel, Object token) {
        KickOffIntent kickOffIntent = null;
        if (listener!=null){
            kickOffIntent = listener.onBeforeChannelBeingManaged(channel, token);
        }
        if (kickOffIntent!=null){
            if (kickOffIntent.isKickOff()){
                String uid = AttributeKeyUtil.get(channel, AttributeKeyConstant.USER_ID, String.class);
                Integer channelType = AttributeKeyUtil.get(channel, AttributeKeyConstant.CHANNEL_TYPE, Integer.class);
                sessionService.kickOff(uid,channelType,kickOffIntent.getMsg());
            }
        }else {
            String uid = AttributeKeyUtil.get(channel, AttributeKeyConstant.USER_ID, String.class);
            Integer channelType = AttributeKeyUtil.get(channel, AttributeKeyConstant.CHANNEL_TYPE, Integer.class);
            sessionService.kickOff(uid,channelType);
        }

    }

    @Override
    public void onAfterChannelBeingManaged(Channel channel, Object token) {
        String cid = AttributeKeyUtil.get(channel, AttributeKeyConstant.CHANNEL_ID, String.class);
        String uid = AttributeKeyUtil.get(channel, AttributeKeyConstant.USER_ID, String.class);
        Integer channelType = AttributeKeyUtil.get(channel, AttributeKeyConstant.CHANNEL_TYPE, Integer.class);
        String osName = AttributeKeyUtil.get(channel, AttributeKeyConstant.OS_NAME, String.class);
        String osVersion = AttributeKeyUtil.get(channel, AttributeKeyConstant.OS_VERSION, String.class);

        InetSocketAddress localAddress = (InetSocketAddress)channel.localAddress();
        InetSocketAddress remoteAddress = (InetSocketAddress)channel.remoteAddress();

        WOIMSession woimSession = new WOIMSession().setUid(uid)
                .setCid(cid)
                .setChannelType(channelType)
                .setHostIp(NetUtil.getHostname(localAddress))
                .setHostPort(localAddress.getPort())
                .setRemoteIp(NetUtil.getHostname(remoteAddress))
                .setRemotePort(remoteAddress.getPort())
                .setOsName(osName)
                .setOsVersion(osVersion)
                .setConnectTime(new Timestamp(System.currentTimeMillis()));
        sessionService.createSession(woimSession);
        if (listener!=null){
            listener.onSessionCreated(channel,token);
        }

    }

    public void close(ChannelHandlerContext ctx){
        Channel channel = ctx.channel();
        String cid = AttributeKeyUtil.get(channel, AttributeKeyConstant.CHANNEL_ID, String.class);
        String uid = AttributeKeyUtil.get(channel, AttributeKeyConstant.USER_ID, String.class);
        if (uid!=null&&cid!=null){
            sessionService.closeSession(uid,cid);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class KickOffIntent{
        boolean kickOff;
        String msg;
    }
}

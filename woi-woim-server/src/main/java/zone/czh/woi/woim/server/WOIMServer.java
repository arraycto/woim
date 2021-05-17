package zone.czh.woi.woim.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.server.channel.handler.AuthHandler;
import zone.czh.woi.woim.server.channel.handler.GroupMsgHandler;
import zone.czh.woi.woim.server.channel.handler.PrivateMsgHandler;
import zone.czh.woi.woim.server.channel.initializer.ServerChannelInitializer;
import zone.czh.woi.woim.server.channel.initializer.manager.HandlerManager;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
*@ClassName: WOIMServer
*@Description: None
*@author woi
*/
@Getter
@Accessors(chain = true)
public class WOIMServer {
    private int port;
    private Map<String, Channel> channelMap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelGroup channelGroup;
    private Configurator configurator;
    private EventListener eventListener;

    public WOIMServer(Builder builder) {
        port = builder.port;
        configurator = builder.configurator;
        eventListener = builder.eventListener;
        channelMap = new ConcurrentHashMap<>();
        channelGroup = new DefaultChannelGroup(new DefaultEventLoop());
    }

    public void shutdown(){
        if (bossGroup != null && !bossGroup.isShuttingDown() && !bossGroup.isShutdown()) {
            try {
                bossGroup.shutdownGracefully();
            } catch (Exception var5) {
            }
        }

        if (workerGroup != null && !workerGroup.isShuttingDown() && !workerGroup.isShutdown()) {
            try {
                workerGroup.shutdownGracefully();
            } catch (Exception var4) {
            }
        }
    }

    public void bind(){
        try {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            HandlerManager handlerManager = new HandlerManager(this,configurator);
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childHandler(new ServerChannelInitializer(handlerManager));
            ChannelFuture channelFuture = serverBootstrap.bind(port).syncUninterruptibly();
            channelFuture.channel().closeFuture().addListener((future)->{
                if (future.cause()!=null){
                    future.cause().printStackTrace();
                }
                shutdown();
            });
        }catch (Exception e){
            e.printStackTrace();
            shutdown();
        }
    }

    public void manage(Channel channel,String cid){
        channelMap.put(cid,channel);
        channelGroup.add(channel);
    }

    public Channel getChannel(String cid){
        return channelMap.get(cid);
    }

    public void closeChannel(String cid){
        Channel channel = getChannel(cid);
        if (channel!=null){
            channelMap.remove(cid);
            channelGroup.remove(channel);
            channel.close();
        }
    }

    public boolean push(String cid,Object data){
        Channel channel = getChannel(cid);
        try {
            if (channel!=null&&channel.isActive()){
                channel.writeAndFlush(data);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean broadcast(Data data){
        try {
            channelGroup.writeAndFlush(data);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean broadcast(Data data, ChannelMatcher channelMatcher){
        try {
            channelGroup.writeAndFlush(data,channelMatcher);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public interface EventListener{
        public void onChannelUnregistered(ChannelHandlerContext ctx);
        public void onChannelExceptionCaught(ChannelHandlerContext ctx, Throwable cause);
         /**
         * 连接被正式管理前会调用此方法
         * @param channel
         * @param token
         */
        void onBeforeChannelBeingManaged(Channel channel, Object token);

        /**
         * server正式管理channel，此时可以建立相关会话信息
         * @param channel
         * @param token
         */
        void onAfterChannelBeingManaged(Channel channel, Object token);
    }

    public interface Configurator{
        AuthHandler.Verifier getChannelVerifier();
        PrivateMsgHandler.MsgService getPrivateMsgService();
        GroupMsgHandler.MsgService getGroupMsgService();
        List<PacketHandler> getCustomHandler();
    }

    @Data
    @Accessors(chain = true)
    public static class Builder{
        private int port;
        Configurator configurator;
        EventListener eventListener;
        public WOIMServer build() {
            return new WOIMServer(this);
        }
    }
    public static void main(String[] args) throws UnknownHostException {

    }
}

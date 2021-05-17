package zone.czh.woi.woim.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import lombok.*;
import lombok.experimental.Accessors;
import zone.czh.woi.base.util.Pair;
import zone.czh.woi.protocol.encoder.PayloadEncoder;
import zone.czh.woi.protocol.protocol.Payload;
import zone.czh.woi.protocol.protocol.WoiProtobuf;
import zone.czh.woi.protocol.util.PayloadUtil;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.base.constant.WOIMConfig;
import zone.czh.woi.woim.base.obj.vo.GroupMsg;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.base.obj.vo.PrivateMsg;
import zone.czh.woi.woim.client.handler.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

/**
*@ClassName: WOIMClient
*@Description: None
*@author woi
*/
public class WOIMClient {
    public static long DEFAULT_EXPIRATION = 5000;
    @Getter
    private String inetHost;
    @Getter
    private int port;
    @Getter
    private Channel channel;
    @Getter
    private Map<Long, Pair<ResponseHandler,Boolean>> responseHandlers;

    private NioEventLoopGroup group;

    private Bootstrap bootstrap;

    private ChannelHandler channelInitializer;

    private EventListener eventListener;

    private Configurator configurator;

    private IntentHandler intentHandler;
    @Getter
    private boolean initialized;

    @Getter
    boolean shutDown;

    public WOIMClient(Builder builder) {
        shutDown = false;
        inetHost = builder.inetHost;
        port = builder.port;
        eventListener = builder.eventListener;
        configurator = builder.configurator;
        responseHandlers = new HashMap<>();
        initialized =false;
        init();
    }


    public void initHandler(){
        intentHandler = new IntentHandler();
        channelInitializer =  new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new IdleStateHandler(0, WOIMConfig.WRITER_IDLE_TIME_SECONDS,0));
                //---
                pipeline.addLast(new ProtobufVarint32FrameDecoder());
                pipeline.addLast(new ProtobufDecoder(WoiProtobuf.Payload.getDefaultInstance()));
                //---
                pipeline.addLast(new PayloadInboundHandler(WOIMClient.this));
                pipeline.addLast(new ConnectionStateHandler());
                pipeline.addLast(new PrivateMsgHandler(eventListener));
                pipeline.addLast(new GroupMsgHandler(eventListener));
                pipeline.addLast(intentHandler);
                //---
                pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast(new ProtobufEncoder());
                pipeline.addLast(new PayloadEncoder());
                //---
                pipeline.addLast(new ReqHandler(WOIMClient.this));
                List<PacketHandler> customHandlers = configurator.getCustomHandler();
                if (customHandlers!=null){
                    for (MessageToMessageDecoder<Packet> customHandler:customHandlers){
                        pipeline.addLast(customHandler);
                    }
                }


            }
        };
    }

    public void initChannel(){
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(channelInitializer);
    }

    private void init(){
        initHandler();
        initChannel();
        initialized =true;
    }

    public boolean connect(){
        return connect(false);
    }

    public boolean connect(boolean syn){
        if (bootstrap==null||group==null||inetHost==null){
            throw new RuntimeException("");
        }
        try {
            ChannelFuture channelFuture = bootstrap.connect(inetHost, port);
            ChannelFuture succeededFuture = channelFuture.channel().newSucceededFuture().addListener((future -> {
                if (eventListener != null) {
                    eventListener.onNewSucceeded(this,future);
                }
            }));
            channelFuture.channel().closeFuture().addListener((future)->{
                if (eventListener!=null){
                    eventListener.onChannelClosed(this,future);
                }
                if (future.cause()!=null){
                    future.cause().printStackTrace();
                }

            });
            channel = channelFuture.channel();
            if (syn){
                channelFuture.syncUninterruptibly();
            }
            return true;
        }catch (RejectedExecutionException e){
            if (eventListener!=null){
                eventListener.onRejected(this);
            }
            e.printStackTrace();
            return false;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 关闭连接，可恢复连接
     */
    public void disconnect(){
        if (channel!=null){
            channel.disconnect();
        }
    }

    public boolean isConnected(){
        return channel!=null&&channel.isActive();
    }

    public void addIntentHandler(Object handler){
        intentHandler.addHandler(handler);
    }

    public void removeIntentHandler(Object handler){
        intentHandler.removeHandler(handler);
    }

    public void auth(Object token, ResponseHandler responseHandler, TimeoutHandler timeoutHandler){
        channel.writeAndFlush(new Req().setPayload(PayloadUtil.buildPayload(token, Payload.Cmd.AUTH,true))
                .setResponseHandler(responseHandler)
                .setTimeoutHandler(timeoutHandler)
                .setExpiration(DEFAULT_EXPIRATION)
                .setBlock(true));
    }

    public void authSafely(Object token, ResponseHandler responseHandler, TimeoutHandler timeoutHandler){
        if (!isConnected()){
            connect(true);
        }
        auth( token,  responseHandler,  timeoutHandler);
    }

    /**
     * 关闭后不可恢复连接！
     */
    public void shutdown(){
        if (group != null && !group.isShuttingDown() && !group.isShutdown()) {
            try {
                Future<?> future = group.shutdownGracefully();
                future.addListener((future1 -> {
                    if (future1.isDone()&&future1.isSuccess()){
                        shutDown = true;
                    }
                }));

            } catch (Exception e) {
            }
        }
    }

    public void push(Object data){
        if (channel.isActive()){
            channel.writeAndFlush(PayloadUtil.buildPayload(data,false));
        }
    }

    public void push(Object data, ResponseHandler responseHandler,boolean block){
        push(data,responseHandler,null,block);
    }

    public void push(Object data, ResponseHandler responseHandler, TimeoutHandler timeoutHandler,boolean block){
        push(data,responseHandler,timeoutHandler,null,block);
    }

    public void push(Object data, ResponseHandler responseHandler, TimeoutHandler timeoutHandler, Long expiration, boolean block ){
        if (channel.isActive()){
            channel.writeAndFlush(new Req().setPayload(PayloadUtil.buildPayload(data,true))
                    .setResponseHandler(responseHandler)
                    .setTimeoutHandler(timeoutHandler)
                    .setExpiration((expiration==null)?DEFAULT_EXPIRATION:expiration)
                    .setBlock(block));
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public class Req {
        Payload payload;
        ResponseHandler responseHandler;
        TimeoutHandler timeoutHandler;
        Long expiration;
        boolean block;
    }

    public interface EventListener{
        void onNewSucceeded(WOIMClient client, Future future);
        void onChannelClosed(WOIMClient client, Future future);
        void onRejected(WOIMClient client);
        void onReceivePrivateMsg(Packet<PrivateMsg> packet);
        void onReceiveGroupMsg(Packet<GroupMsg> packet);
    }

    public interface Configurator{
        List<PacketHandler> getCustomHandler();
    }

    public interface ResponseHandler {
        void handleResponse(Packet packet);
    }

    public interface TimeoutHandler {
        void handleTimeout();
    }


    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Builder{
        private String inetHost;
        private Integer port;
        private EventListener eventListener;
        private Configurator configurator;

        public WOIMClient build(){
            if (inetHost==null){
                throw new RuntimeException("inetHost cannot be null");
            }
            if (port==null){
                throw new RuntimeException("port cannot be null");
            }
            if (configurator==null){
                throw new RuntimeException("configurator cannot be null");
            }
            return new WOIMClient(this);
        }
    }

}

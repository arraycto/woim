package zone.czh.woi.woim.server.channel.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.server.channel.initializer.manager.HandlerManager;

import java.util.List;

/**
*@ClassName: ServerChannelInitializer
*@Description: None
*@author woi
*/
@Data
@AllArgsConstructor
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private HandlerManager handlerManager;
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //heartbeat
        pipeline.addLast(handlerManager.idleStateHandler());
        pipeline.addLast(handlerManager.connectionStateHandler());
        //other handler
        pipeline.addLast(handlerManager.protobufVarint32FrameDecoder());
        pipeline.addLast(handlerManager.protobufDecoder());
        pipeline.addLast(handlerManager.authHandler());//鉴权
        pipeline.addLast(handlerManager.payloadHandler());
        pipeline.addLast(handlerManager.protobufVarint32LengthFieldPrepender());
        pipeline.addLast(handlerManager.protobufEncoder());
        pipeline.addLast(handlerManager.payloadEncoder());
        pipeline.addLast(handlerManager.privateMsgDecoder());
        pipeline.addLast(handlerManager.groupMsgDecoder());
        pipeline.addLast(handlerManager.privateMsgEncoder());
        pipeline.addLast(handlerManager.groupMsgEncoder());
        pipeline.addLast(handlerManager.intentEncoder());
        List<PacketHandler> customHandlers = handlerManager.customHandlers();
        if (customHandlers!=null){
            for (MessageToMessageDecoder<Packet> customHandler:customHandlers){
                pipeline.addLast(customHandler);
            }
        }
    }
}

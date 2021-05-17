package zone.czh.woi.woim.server.channel.initializer.manager;

import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import zone.czh.woi.protocol.encoder.PayloadEncoder;
import zone.czh.woi.protocol.protocol.WoiProtobuf;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.base.constant.WOIMConfig;
import zone.czh.woi.woim.server.WOIMServer;
import zone.czh.woi.woim.server.channel.handler.*;

import java.util.List;

/**
*@ClassName: HandlerManager
*@Description: None
*@author woi
*/
public class HandlerManager {
    private WOIMServer woimServer;
    private WOIMServer.Configurator configurator;

    ConnectionStateHandler connectionStateHandler;
    ProtobufDecoder protobufDecoder;
    PayloadHandler payloadHandler;
    ProtobufVarint32LengthFieldPrepender protobufVarint32LengthFieldPrepender;
    ProtobufEncoder protobufEncoder;
    PayloadEncoder payloadEncoder;
    PrivateMsgHandler privateMsgHandler;
    GroupMsgHandler groupMsgHandler;
    PrivateMsgEncoder privateMsgEncoder;
    GroupMsgEncoder groupMsgEncoder;
    IntentEncoder intentEncoder;
    public HandlerManager(WOIMServer woimServer,WOIMServer.Configurator configurator){
        this.woimServer = woimServer;
        this.configurator = configurator;
        connectionStateHandler = new ConnectionStateHandler(woimServer.getEventListener());
        protobufDecoder= new ProtobufDecoder(WoiProtobuf.Payload.getDefaultInstance());
        payloadHandler = new PayloadHandler();
        protobufVarint32LengthFieldPrepender = new ProtobufVarint32LengthFieldPrepender();
        protobufEncoder = new ProtobufEncoder();
        payloadEncoder=new PayloadEncoder();
        privateMsgHandler = new PrivateMsgHandler(configurator.getPrivateMsgService());
        groupMsgHandler = new GroupMsgHandler(configurator.getGroupMsgService());
        privateMsgEncoder = new PrivateMsgEncoder();
        groupMsgEncoder = new GroupMsgEncoder();
        intentEncoder = new IntentEncoder();
    }

    public IdleStateHandler idleStateHandler(){
        return new IdleStateHandler(WOIMConfig.READER_IDLE_TIME_SECONDS,0,0);
    }

    public ConnectionStateHandler connectionStateHandler(){
        return connectionStateHandler;
    }

    public ProtobufVarint32FrameDecoder protobufVarint32FrameDecoder(){
        return new ProtobufVarint32FrameDecoder();
    }

    public ProtobufDecoder protobufDecoder(){
        return protobufDecoder;
    }

    public AuthHandler authHandler(){
         AuthHandler authHandler = new AuthHandler().setVerifier(configurator.getChannelVerifier())
                .setEventListener(woimServer.getEventListener())
                .setWoimServer(woimServer);
        return authHandler;
    }

    public PayloadHandler payloadHandler(){
        return payloadHandler;
    }

    public ProtobufVarint32LengthFieldPrepender protobufVarint32LengthFieldPrepender(){
        return protobufVarint32LengthFieldPrepender;
    }

    public ProtobufEncoder protobufEncoder(){
        return protobufEncoder;
    }

    public PayloadEncoder payloadEncoder(){return payloadEncoder;}

    public PrivateMsgHandler privateMsgDecoder(){
        return privateMsgHandler;
    }

    public GroupMsgHandler groupMsgDecoder(){
        return groupMsgHandler;
    }

    public PrivateMsgEncoder privateMsgEncoder(){
        return privateMsgEncoder;
    }

    public GroupMsgEncoder groupMsgEncoder(){
        return groupMsgEncoder;
    }

    public IntentEncoder intentEncoder(){
        return intentEncoder;
    }

    public List<PacketHandler> customHandlers(){
        return configurator.getCustomHandler();
    }
}

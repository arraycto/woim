package zone.czh.woi.protocol.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import zone.czh.woi.protocol.protocol.WoiProtocol;
/**
*@ClassName: WoiProtocolEncoder
*@Description: None
*@author woi
*/
public class WoiProtocolEncoder extends MessageToByteEncoder<WoiProtocol> {
    protected void encode(ChannelHandlerContext ctx, WoiProtocol msg, ByteBuf out) throws Exception {
//        System.out.println(this.getClass());
//        //header
//        System.out.println("写入协议头");
//        out.writeByte(msg.getHeader().getPrefix());
//        out.writeByte(msg.getHeader().getVersion());
//        out.writeInt(msg.getHeader().getBodyLength());
//        out.writeBytes(msg.getHeader().getOther());
//        //body
//        System.out.println("写入正文");
//        out.writeBytes(msg.getBody());
        out.writeBytes(msg.protocolByteArray());
    }
}

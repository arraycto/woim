package zone.czh.woi.protocol.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import zone.czh.woi.protocol.protocol.WoiProtocol;
import zone.czh.woi.protocol.protocol.header.WoiHeader;

import java.util.List;
/**
*@ClassName: WoiProtocolDecoder
*@Description: None
*@author woi
*/
public class WoiProtocolDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        System.out.println(this.getClass());
        if (in.readableBytes()>= WoiHeader.HEADER_LEN){
            WoiProtocol woiProtocol = new WoiProtocol(new WoiHeader());
            in.markReaderIndex();
            //判断是否为协议开始标志
            byte prefix = in.readByte();
            if (!woiProtocol.verifyPrefix(prefix)){
                System.out.println("非标准协议");
                return;
            }else {
                //读取剩余协议头
                byte version = in.readByte();
                woiProtocol.getHeader().setVersion(version);
                byte cmd = in.readByte();
                woiProtocol.getHeader().setCmd(cmd);
                System.out.println("protocol cmd:"+cmd);
                int bodyLen = in.readInt();
                woiProtocol.getHeader().setBodyLength(bodyLen);
                in.readBytes(woiProtocol.getHeader().getOther());

                if (in.readableBytes()>=woiProtocol.getHeader().getBodyLength()){
                    byte[] body = new byte[woiProtocol.getHeader().getBodyLength()];
                    in.readBytes(body);
                    woiProtocol.setBody(body);
                    out.add(woiProtocol);
                }else {
                    in.resetReaderIndex();
                    return;
                }
            }
        }
    }

}

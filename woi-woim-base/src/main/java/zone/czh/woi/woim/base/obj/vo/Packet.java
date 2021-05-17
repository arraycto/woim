package zone.czh.woi.woim.base.obj.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import zone.czh.woi.protocol.protocol.WoiProtobuf;
import zone.czh.woi.protocol.util.PayloadUtil;

/**
*@ClassName: Packet
*@Description: None
*@author woi
*/
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Packet<T> {
    long sn;
    String cmd;
    int type;
    boolean responseRequired;
    long respondSn;
    T data;
    public Packet(WoiProtobuf.Payload payload){
        sn = payload.getSn();
        cmd = payload.getCmd();
        type = payload.getType();
        respondSn = payload.getRespondSn();
        responseRequired = payload.getResponseRequired();
        data = (T) PayloadUtil.readValue(payload);
    }
}

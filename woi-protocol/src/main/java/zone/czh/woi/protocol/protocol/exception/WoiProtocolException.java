package zone.czh.woi.protocol.protocol.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import zone.czh.woi.protocol.protocol.exception.err.WoiProtocolErr;

@Data
@ToString
public class WoiProtocolException extends Exception{

    private WoiProtocolErr protocolErr;

    public WoiProtocolException(WoiProtocolErr protocolErr){
        super("protocol err");
        this.protocolErr = protocolErr;
    }
    public WoiProtocolException(WoiProtocolErr protocolErr, String msg){
        super(msg);
        this.protocolErr = protocolErr;
    }
}

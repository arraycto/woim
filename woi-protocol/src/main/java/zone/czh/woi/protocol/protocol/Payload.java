package zone.czh.woi.protocol.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Payload {
    long sn;
    String cmd;
    int type;
    boolean responseRequired;
    long respondSn;
    byte[] dcn;
    byte[] data;

    public static class Cmd{
        public static final String HEARTBEAT = "heartbeat";
        public static final String AUTH = "auth";
        public static final String SYS = "system";
        public static final String REQUEST = "req";
        public static final String RESPONSE = "res";
    }

    public static class Type{
        public static final int REQUEST = 0;
        public static final int RESPONSE = 1;
    }
}

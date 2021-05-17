package zone.czh.woi.protocol.protocol.header;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import zone.czh.woi.protocol.util.ByteUtil;

import java.io.Serializable;

public class WoiHeader implements Serializable {
    @Getter
    byte prefix = 0x01;
    @Getter
    @Setter
    byte version = 0x01;
    @Getter
    @Setter
    byte cmd = 0;
    @Setter
    @Getter
    int bodyLength = 0;
    @Getter
    @Setter
    byte[] other;
    //len
    public static final int BYTE_SIZE = Byte.SIZE;
    public static final int HEADER_LEN = 32;
    public  final int PREFIX_LEN = Byte.SIZE/BYTE_SIZE;
    public  final int VERSION_LEN = Byte.SIZE/BYTE_SIZE;
    public final int CMD_LEN = Byte.SIZE/BYTE_SIZE;
    public  final int BODY_LENGTH_LEN = Integer.SIZE/BYTE_SIZE;
    public  int OTHER_LEN = HEADER_LEN - PREFIX_LEN - VERSION_LEN - CMD_LEN - BODY_LENGTH_LEN;

    /**
     * cmd
     * 四位
     *
     */


    public WoiHeader(){
        this.other = new byte[OTHER_LEN];
    }

    public WoiHeader(byte version){
        this.other = new byte[OTHER_LEN];
        this.version = version;
    }

    public WoiHeader(int bodyLength){
        this.other = new byte[OTHER_LEN];
        this.bodyLength = bodyLength;
    }

    @JsonIgnore
    public byte[] headerByteArray(){
        int curPos = 0;
        byte[] bytes = new byte[HEADER_LEN];
        bytes[curPos] = prefix;
        curPos+=PREFIX_LEN;
        bytes[curPos] = version;
        curPos+=VERSION_LEN;
        bytes[curPos] = cmd;
        curPos+=CMD_LEN;
        System.arraycopy(ByteUtil.getBytes(bodyLength),0,bytes,curPos,BODY_LENGTH_LEN);
        curPos+=BODY_LENGTH_LEN;
        System.arraycopy(other,0,bytes,curPos,OTHER_LEN);
        return bytes;
    }

    public boolean verifyPrefix(byte prefix){
        return prefix==this.prefix;
    }

    public boolean verifyVersion(byte version){
        return version==this.version;
    }
}

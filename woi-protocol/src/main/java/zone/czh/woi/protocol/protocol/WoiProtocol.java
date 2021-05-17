package zone.czh.woi.protocol.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import zone.czh.woi.protocol.protocol.exception.WoiProtocolException;
import zone.czh.woi.protocol.protocol.exception.err.WoiProtocolErr;
import zone.czh.woi.protocol.protocol.header.WoiHeader;

import java.io.*;

/**
*@ClassName: WoiProtocol
*@Description: None
*@author woi
*/
public class WoiProtocol<H extends WoiHeader> implements Serializable{

    @Setter
    @Getter
    H header;
    //body
    @Getter
    byte[] body;

    public WoiProtocol(H header) throws WoiProtocolException {
        this.header = header;
        setBody(null);
    }

    public WoiProtocol(H header, Object body) throws WoiProtocolException {
        this.header = header;
        setBody(body);
    }

    @JsonIgnore
    public byte[] headerByteArray() throws WoiProtocolException {
        if (header==null){
            throw new WoiProtocolException(WoiProtocolErr.NullHeader,"null header");
        }
        return this.header.headerByteArray();
    }

    @JsonIgnore
    public byte[] protocolByteArray() throws WoiProtocolException {
        if (header==null){
            throw new WoiProtocolException(WoiProtocolErr.NullHeader,"null header");
        }
        if (body==null){
            throw new WoiProtocolException(WoiProtocolErr.NullBody,"null body");
        }
        byte[] bytes = new byte[WoiHeader.HEADER_LEN + this.header.getBodyLength()];
        int curPos = 0;
        System.arraycopy(headerByteArray(),0,bytes,curPos,WoiHeader.HEADER_LEN);
        curPos+=WoiHeader.HEADER_LEN;
        System.arraycopy(body,0,bytes,curPos,this.header.getBodyLength());
        return bytes;
    }

    @JsonProperty("body")
    public void setBody(byte[] body){

        if (body==null){
            this.body = new byte[0];
        }else {
            this.body = body;
        }

        this.header.setBodyLength(this.body.length);
    }

    public void setBody(Object body) throws WoiProtocolException {

        if (header==null){
            throw new WoiProtocolException(WoiProtocolErr.NullHeader,"null header");
        }

        if (body==null){
            this.body = new byte[0];
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new WoiProtocolException(WoiProtocolErr.BodySerializeErr,e.getMessage());
        }
        this.body = jsonStr.getBytes();
        //set body len
        this.header.setBodyLength(this.body.length);
    }



    @JsonIgnore
    public int getPacketSize(){
        return WoiHeader.HEADER_LEN+this.header.getBodyLength();
    }

    public String body2JsonStr(){
        return new String(body);
    }

    public boolean verifyPrefix(byte prefix){
        return this.header.verifyPrefix(prefix);
    }

    public boolean verifyVersion(byte version){
        return this.header.verifyVersion(version);
    }


    public static void main(String[] args) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String str = objectMapper.writeValueAsString("test");
//        System.out.println(str);
        WoiProtocol woiProtocol = new WoiProtocol(new WoiHeader(), "test");
        System.out.println(woiProtocol.body2JsonStr());

    }

}



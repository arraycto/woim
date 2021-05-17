package zone.czh.woi.protocol.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import zone.czh.woi.protocol.protocol.Payload;
import zone.czh.woi.protocol.protocol.WoiProtobuf;

/**
 * message Payload{
 *   int64 sn=1;
 *   string cmd=2;
 *   int32 type=3;
 *   bool responseRequired=4;
 *   int64 respondSn=5;
 *   bytes dcn=6;
 *   bytes data=7;
 * }
 */
public class PayloadUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Object readValue(WoiProtobuf.Payload payload){
        try {
            Object obj = objectMapper.readValue(payload.getData().toByteArray(), Class.forName(new String(payload.getDcn().toByteArray())));
            return obj;
        } catch (Exception e){
            return null;
        }
    }

    public static Object readValue(Payload payload){
        try {
            Object obj = objectMapper.readValue(payload.getData(), Class.forName(new String(payload.getDcn())));
            return obj;
        } catch (Exception e){
            return null;
        }
    }
    //request
    public static Payload buildPayload(Object obj,boolean responseRequired){
        return buildPayload(obj, Payload.Cmd.REQUEST,responseRequired);
    }
    //request
    public static Payload buildPayload(Object obj,String cmd,boolean responseRequired){
        return buildPayload(obj,cmd, Payload.Type.REQUEST,responseRequired,-1);
    }
    //response
    public static Payload buildPayload(Object obj,String cmd,long respondSn){
        return buildPayload(obj,cmd, Payload.Type.RESPONSE,false,respondSn);
    }

//    public static WoiProtobuf.Payload buildPayload(Payload payload){
//        return buildPayload()
//    }

    //all
//    @Deprecated
//    public static WoiProtobuf.Payload buildPayload(Object obj,String cmd,Integer type, boolean responseRequired,long respondSn){
//        if (cmd==null){
//            return null;
//        }else if (cmd.equals(PayloadCst.Cmd.HEARTBEAT)){
//            return WoiProtobuf.Payload.newBuilder()
//                    .setSn(System.currentTimeMillis())
//                    .setCmd(cmd)
//                    .setType(PayloadCst.Type.REQUEST)
//                    .setResponseRequired(false)
//                    .build();
//        }
//        try {
//            WoiProtobuf.Payload.Builder builder = WoiProtobuf.Payload.newBuilder();
//            builder.setSn(System.currentTimeMillis()).setResponseRequired(responseRequired).setRespondSn(respondSn);
//            if (cmd!=null){
//                builder.setCmd(cmd);
//            }
//            if (type!=null){
//                builder.setType(type);
//            }
//            if (obj!=null){
//                System.out.println(obj.getClass().getName());
//                builder.setDcn(ByteString.copyFrom(obj.getClass().getName().getBytes()))
//                        .setData(ByteString.copyFrom(objectMapper.writeValueAsBytes(obj)));
//            }
//            return builder.build();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static Payload buildPayload(Object obj,String cmd,int type, boolean responseRequired,long respondSn){
        if (cmd==null){
            return null;
        }
        try {
            return new Payload().setSn(genSn())
                    .setCmd(cmd)
                    .setType(type)
                    .setResponseRequired(responseRequired)
                    .setRespondSn(respondSn)
                    .setDcn(obj.getClass().getName().getBytes())
                    .setData(objectMapper.writeValueAsBytes(obj));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static WoiProtobuf.Payload buildHeartbeat(){
        return WoiProtobuf.Payload.newBuilder()
                .setSn(genSn())
                .setCmd(Payload.Cmd.HEARTBEAT)
                .setType(Payload.Type.REQUEST)
                .setResponseRequired(false)
                .build();
    }

    public static WoiProtobuf.Payload buildPayload(Payload payload){
        return buildPayload(
                payload.getSn(),
                payload.getCmd(),
                payload.getType(),
                payload.isResponseRequired(),
                payload.getRespondSn(),
                payload.getDcn(),
                payload.getData());
    }

    public static WoiProtobuf.Payload buildPayload(long sn, String cmd,int type,boolean responseRequired,long respondSn,byte[] dcn,byte[] data){
        WoiProtobuf.Payload.Builder builder = WoiProtobuf.Payload.newBuilder();
        if (cmd==null){
            return null;
        }
        builder.setSn(sn);
        builder.setCmd(cmd);
        builder.setType(type);
        builder.setResponseRequired(responseRequired);
        builder.setRespondSn(respondSn);
        if (dcn!=null){
            builder.setDcn(ByteString.copyFrom(dcn));
        }
        if (data!=null){
            builder.setData(ByteString.copyFrom(data));
        }

        return builder.build();

    }

    public static long genSn(){
        return System.currentTimeMillis();
    }
}

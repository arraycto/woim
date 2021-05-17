package zone.czh.woi.woim.channel.msg;

import lombok.Data;
import lombok.experimental.Accessors;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.protocol.protocol.Payload;
import zone.czh.woi.protocol.util.PayloadUtil;
import zone.czh.woi.woim.base.obj.vo.GroupMsg;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.base.obj.vo.PrivateMsg;
import zone.czh.woi.woim.server.channel.handler.GroupMsgHandler;
import zone.czh.woi.woim.server.channel.handler.PrivateMsgHandler;
import zone.czh.woi.woim.service.inter.MessageService;
import zone.czh.woi.woim.service.inter.PushService;
import zone.czh.woi.woim.base.obj.vo.MsgState;

/**
*@ClassName: DistributedMsgService
*@Description: None
*@author woi
*/

@Data
@Accessors(chain = true)
public class DistributedMsgService implements PrivateMsgHandler.MsgService, GroupMsgHandler.MsgService {

    MessageService messageService;

    PushService pushService;

    CustomMsgService customMsgService;

    @Override
    public void handleMsg(Packet<PrivateMsg> packet,PrivateMsg msg) {
        boolean block = false;
        if (customMsgService!=null){
            block = customMsgService.handleMsg(packet,msg);
        }
        if (!block){
            MsgState msgState = messageService.sendMsg(msg);
            //响应客户端
            pushService.pushDistributed(msg.getSrcUid(), PayloadUtil.buildPayload(msgState,
                    Payload.Cmd.RESPONSE, Payload.Type.RESPONSE,false,packet.getSn()));
        }

    }

    @Override
    public void handleMsg(Packet<GroupMsg> packet,GroupMsg msg) {
        boolean block = false;
        if (customMsgService!=null){
            block = customMsgService.handleMsg(packet,msg);
        }
        if (!block){
            MsgState msgState = messageService.sendMsg(msg);
            //响应客户端
            pushService.pushDistributed(msg.getSrcUid(), PayloadUtil.buildPayload(msgState,
                    Payload.Cmd.RESPONSE, Payload.Type.RESPONSE,false,packet.getSn()));
        }
    }
}

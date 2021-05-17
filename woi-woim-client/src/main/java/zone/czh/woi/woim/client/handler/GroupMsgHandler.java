package zone.czh.woi.woim.client.handler;

import io.netty.channel.ChannelHandlerContext;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.base.obj.vo.GroupMsg;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.client.WOIMClient;

import java.util.List;

/**
*@ClassName: GroupMsgHandler
*@Description: None
*@author woi
*/

public class GroupMsgHandler extends PacketHandler<GroupMsg> {
    WOIMClient.EventListener listener;

    public GroupMsgHandler(WOIMClient.EventListener listener){
        super(GroupMsg.class);
        this.listener=listener;
    }

    @Override
    protected void handle(ChannelHandlerContext ctx, Packet<GroupMsg> packet, List<Object> out) throws Exception {
        if (listener!=null){
            listener.onReceiveGroupMsg(packet);
        }
    }

    public interface MsgService {
        void handleMsg(Packet<GroupMsg> packet, GroupMsg msg);
    }
}

package zone.czh.woi.woim.server.channel.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.base.obj.vo.GroupMsg;
import zone.czh.woi.woim.base.obj.vo.Packet;

import java.util.List;

/**
*@ClassName: GroupMsgHandler
*@Description: None
*@author woi
*/
@ChannelHandler.Sharable
public class GroupMsgHandler extends PacketHandler<GroupMsg> {
    MsgService msgService;

    public GroupMsgHandler(MsgService msgService){
        super(GroupMsg.class);
        this.msgService=msgService;
    }

    @Override
    protected void handle(ChannelHandlerContext ctx, Packet<GroupMsg> packet, List<Object> out) throws Exception {
        msgService.handleMsg(packet, packet.getData());
    }

    public interface MsgService {
        void handleMsg(Packet<GroupMsg> packet, GroupMsg msg);
    }

}

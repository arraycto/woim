package zone.czh.woi.woim.server.channel.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import zone.czh.woi.protocol.util.PayloadUtil;
import zone.czh.woi.woim.base.obj.vo.GroupMsg;

import java.util.List;

/**
*@ClassName: GroupMsgEncoder
*@Description: None
*@author woi
*/

@ChannelHandler.Sharable
public class GroupMsgEncoder extends MessageToMessageEncoder<GroupMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, GroupMsg groupMsg, List<Object> list) throws Exception {
        //将msg封装成payload
        list.add(PayloadUtil.buildPayload(groupMsg,false));
    }

}

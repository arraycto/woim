package zone.czh.woi.woim.channel.msg;

import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.protocol.protocol.Payload;
import zone.czh.woi.protocol.util.PayloadUtil;
import zone.czh.woi.woim.base.obj.vo.GroupMsg;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.base.obj.vo.PrivateMsg;
/**
*@ClassName: CustomMsgService
*@Description: None
*@author woi
*/
public interface CustomMsgService {
    /**
     *
     * @param packet
     * @param msg
     * @return block woim msg service
     */
    boolean handleMsg(Packet<PrivateMsg> packet, PrivateMsg msg);

    /**
     *
     * @param packet
     * @param msg
     * @return block woim msg service
     */
    boolean handleMsg(Packet<GroupMsg> packet, GroupMsg msg) ;
}

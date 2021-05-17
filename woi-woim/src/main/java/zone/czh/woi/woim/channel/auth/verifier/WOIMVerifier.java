package zone.czh.woi.woim.channel.auth.verifier;

import io.netty.channel.Channel;
import zone.czh.woi.base.web.constant.ErrorCode;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.protocol.protocol.Payload;
import zone.czh.woi.protocol.util.PayloadUtil;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.server.channel.handler.AuthHandler;
import zone.czh.woi.woim.server.constant.AttributeKeyConstant;
import zone.czh.woi.woim.server.util.AttributeKeyUtil;

/**
*@ClassName: WOIMVerifier
*@Description: None
*@author woi
*/
public abstract class WOIMVerifier implements AuthHandler.Verifier {
    protected String uid;
    protected int channelType;
    protected String osName;
    protected String osVersion;



    @Override
    public final void handleAuthSuccess(Channel channel, Packet packet) {
        AttributeKeyUtil.set(channel, AttributeKeyConstant.USER_ID,getUidAfterVerify());
        AttributeKeyUtil.set(channel, AttributeKeyConstant.CHANNEL_TYPE,getChannelTypeAfterVerify());
        AttributeKeyUtil.set(channel, AttributeKeyConstant.OS_NAME,getOsNameAfterVerify());
        AttributeKeyUtil.set(channel, AttributeKeyConstant.OS_VERSION,getOsVersionAfterVerify());
        channel.writeAndFlush(PayloadUtil.buildPayload(new Response("auth success"),
                Payload.Cmd.RESPONSE,packet.getSn()));
    }

    @Override
    public final void handleAuthFailed(Channel channel, Packet packet) {
        channel.writeAndFlush(PayloadUtil.buildPayload(new Response(ErrorCode.CODE_LOG_IN_ERR,ErrorCode.MESSAGE_LOG_IN_ERR,"auth failed"),
                Payload.Cmd.RESPONSE,packet.getSn()));
    }

    public String getUidAfterVerify(){
        return uid;
    }

    public int getChannelTypeAfterVerify(){
        return channelType;
    }

    public String getOsNameAfterVerify(){
        return osName;
    }

    public String getOsVersionAfterVerify(){
        return osVersion;
    }
}

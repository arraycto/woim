package zone.czh.woi.woim.distributed.adapter;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import zone.czh.woi.base.web.constant.ErrorCode;
import zone.czh.woi.base.web.usual.Response;
import zone.czh.woi.woim.base.obj.po.WOIMSession;
import zone.czh.woi.woim.base.obj.vo.PushInfo;
import zone.czh.woi.woim.base.obj.vo.SessionState;
import zone.czh.woi.woim.constant.WOIMUrl;
import zone.czh.woi.woim.distributed.agent.Agent;
import zone.czh.woi.woim.vo.RequestInfo;

import java.util.Map;
/**
*@ClassName: DefaultAdapter
*@Description: None
*@author woi
*/
public class DefaultAdapter extends Adapter {

    private ParameterizedTypeReference pushReference =  new ParameterizedTypeReference<Response<SessionState>>(){};
    private ParameterizedTypeReference closeSessionReference =  new ParameterizedTypeReference<Response>(){};

    public DefaultAdapter(){
        path.put(Agent.Service.PUSH, WOIMUrl.Distributed.MODULE+WOIMUrl.Distributed.PATH_PUSH);
        path.put(Agent.Service.CLOSE_SESSION,WOIMUrl.Distributed.MODULE+WOIMUrl.Distributed.PATH_CLOSE_SESSION);
    }

    @Override
    public RequestInfo getRequestInfo(Agent.Service service, Map<Agent.Key, Object> params) throws Exception {
        RequestInfo requestInfo = new RequestInfo();
        switch (service){
            case PUSH:{
                WOIMSession session =(WOIMSession) params.get(Agent.Key.PUSH_KEY_SESSION);
                Object data = params.get(Agent.Key.PUSH_KEY_DATA);
                PushInfo pushInfo = new PushInfo(session,data);
                requestInfo.setPath(path.get(service));
                requestInfo.setReference(pushReference);
                requestInfo.setHttpEntity(new HttpEntity(pushInfo));
                break;
            }
            case CLOSE_SESSION:{
                WOIMSession session =(WOIMSession) params.get(Agent.Key.CLOSE_SESSION_KEY_WOIMSESSION);
                requestInfo.setPath(path.get(service));
                requestInfo.setReference(closeSessionReference);
                requestInfo.setHttpEntity(new HttpEntity(session));
                break;
            }

        }
        return requestInfo;
    }

    @Override
    public <T> T parse(Agent.Service service, Object response, Class<T> targetCls) throws Exception {
        if (targetCls.equals(void.class)){
            return null;
        }
        if (response instanceof Response){
            Response res = (Response) response;
            if (res.getCode()== ErrorCode.CODE_SUCCESS){
                return (T) res.getData();
            }
        }
        return null;
    }

}

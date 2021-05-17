package zone.czh.woi.woim.distributed.agent;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import zone.czh.woi.woim.distributed.adapter.DefaultAdapter;
import zone.czh.woi.woim.distributed.adapter.Adapter;
import zone.czh.woi.woim.vo.RequestInfo;

import java.util.HashMap;
import java.util.Map;
/**
*@ClassName: DefaultAgent
*@Description: None
*@author woi
*/
public class DefaultAgent implements Agent{
    String[] serverAddr;
    RestTemplate restTemplate = new RestTemplate();
    private Adapter defAdapter;
    private Map<String,String> serverAddrMap;
    private Map<String, Adapter> adapterMap;
    public DefaultAgent(String[] serverAddr){
        this.serverAddr=serverAddr;
        defAdapter=new DefaultAdapter();
        reload();
    }

    public <T> T call(String hostIp, Service service, Map<Key,Object> params, Class<T> resType) throws Exception {
        Adapter adapter = adapterMap.get(hostIp);
        if (adapter==null){
            adapter=defAdapter;
        }
        if (adapter!=null){
            RequestInfo requestInfo = adapter.getRequestInfo(service, params);
            ResponseEntity entity = restTemplate.exchange(
                    getUrl(hostIp, requestInfo.getPath()),
                    requestInfo.getMethod(),
                    requestInfo.getHttpEntity(),
                    requestInfo.getReference(),
                    requestInfo.getUriVariables());
            return adapter.parse(service,entity.getBody(),resType);
        }else {
            return null;
        }
    }

    private void reload(String[] serverAddr){
        this.serverAddr=serverAddr;
        reload();
    }

    private void reload(){
        if (serverAddrMap==null){
            serverAddrMap = new HashMap<>();
        }
        if (adapterMap==null){
            adapterMap=new HashMap<>();
        }

        if (serverAddr!=null){
            HashMap<String, Adapter> newAdapterMap = new HashMap<>();
            for (int i=0;i<serverAddr.length;i++){
                //upd server addr
                String[] split = serverAddr[i].split(":");
                String hostIp = split[0];
                serverAddrMap.put(hostIp,serverAddr[i]);
                //upd adapter
                Adapter adapter = adapterMap.get(hostIp);
                if (adapter!=null){
                    newAdapterMap.put(hostIp,adapter);
                }
            }
            adapterMap=newAdapterMap;
        }
    }

    public void put(String hostIp, Adapter adapter){
        adapterMap.put(hostIp,adapter);
    }

    private String getUrl(String hostIp, String path) {
        return "http://"+serverAddrMap.get(hostIp)+path;
    }
}

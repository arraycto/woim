package zone.czh.woi.woim.example.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import zone.czh.woi.woim.controller.impl.DefaultUserController;
import zone.czh.woi.woim.controller.inter.UserController;
import zone.czh.woi.woim.distributed.adapter.Adapter;
import zone.czh.woi.woim.distributed.agent.Agent;
import zone.czh.woi.woim.distributed.agent.DefaultAgent;
import zone.czh.woi.woim.generator.GroupIdGenerator;
import zone.czh.woi.woim.session.storage.LocalSessionStorage;
import zone.czh.woi.woim.session.storage.RedisLocalSessionStorage;
import zone.czh.woi.woim.vo.RequestInfo;

import java.util.Map;

@Configuration
public class Config {
    //内置了一些controller（DefaultXXXController），有需要可以实例化后使用，或者实现自己的controller
    @Bean
    UserController userController(){
        return new DefaultUserController();
    }

    @Bean
    LocalSessionStorage localSessionStorage(RedisConnectionFactory factory){
        //默认使用map存储session信息，支持使用redis存储，也可以自行实现LocalSessionStorage接口
        return new RedisLocalSessionStorage(factory);
    }

    //!！重要  集群配置时需留意
    @Bean
    public Agent woimAdapter(){
        /**
         * 情况一：使用默认的DefaultDistributedController
         * 无需进行此Agent的配置
         *
         * 情况二：自定义了DistributedController，但未改变默认的url、参数、返回值类型
         * 无需进行此Agent的配置
         */

        /**
         * 情况三：自定义了DistributedController，且改变了默认的url、参数、返回值类型（出于集群间访问安全）
         * （且在不同的服务器你的url和参数都有变动---建议不要做这么无聊的事=。=）
         * 你可以使用DefaultAgent，并为相应的服务器配置adapter，进行请求时url、参数、返回值类型等的适配
         * 未配置的服务器则会使用DefaultAgent进行适配
         */
        DefaultAgent agent = new DefaultAgent(new String[]{});
        Adapter adapter = new Adapter() {
            @Override
            public RequestInfo getRequestInfo(Agent.Service service, Map<Agent.Key, Object> params) throws Exception {
                return null;
            }

            @Override
            public <T> T parse(Agent.Service service, Object response, Class<T> targetCls) throws Exception {
                return null;
            }
        };
        agent.put("127.0.0.1",adapter);
        /**
         * 情况四：你想自己实现远程调用逻辑
         * 则自行实现Agent
         */
        return new DefaultAgent(new String[]{});
    }
}

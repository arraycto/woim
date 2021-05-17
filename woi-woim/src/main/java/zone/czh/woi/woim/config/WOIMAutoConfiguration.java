package zone.czh.woi.woim.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import tk.mybatis.spring.annotation.MapperScan;
import zone.czh.woi.spring.base.util.BeanUtil;
import zone.czh.woi.woim.channel.msg.CustomMsgService;
import zone.czh.woi.woim.channel.msg.DistributedMsgService;
import zone.czh.woi.woim.configurator.SimpleWOIMConfigurator;
import zone.czh.woi.woim.configurator.WOIMConfigurator;
import zone.czh.woi.woim.configurator.WOIMServerConfigurator;
import zone.czh.woi.woim.constant.WOIMConstant;
import zone.czh.woi.woim.controller.impl.*;
import zone.czh.woi.woim.controller.inter.*;
import zone.czh.woi.woim.db.DBInitializer;
import zone.czh.woi.woim.distributed.agent.Agent;
import zone.czh.woi.woim.distributed.agent.DefaultAgent;
import zone.czh.woi.woim.generator.GroupIdGenerator;
import zone.czh.woi.woim.generator.SnowflakeIdGenerator;
import zone.czh.woi.woim.listener.DefaultWOIMEventListener;
import zone.czh.woi.woim.listener.WOIMEventListener;
import zone.czh.woi.woim.listener.WOIMServerEventListener;
import zone.czh.woi.woim.mapper.WOIMInitMapper;
import zone.czh.woi.woim.server.WOIMServer;
import zone.czh.woi.woim.service.impl.*;
import zone.czh.woi.woim.service.inter.*;
import zone.czh.woi.woim.session.storage.LocalSessionStorage;
import zone.czh.woi.woim.session.storage.MapLocalSessionStorage;

import javax.annotation.Resource;

/**
*@ClassName: WOIMAutoConfiguration
*@Description: None
*@author woi
*/
@Data
@Configuration
@ConfigurationProperties(prefix = "woi.woim")
@MapperScan(WOIMConstant.MAPPER_PACKAGE)
public class WOIMAutoConfiguration implements ApplicationListener<ApplicationStartedEvent> {

    private int connectionPort=6666;
    private String[] serverAddr;
    private long datacenterId =0;
    private long machineId=0;
    private String dbInitializationMode = DBInitializer.MODE_NONE;

    @Resource
    private ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean
    BeanUtil beanUtil(){
        return new BeanUtil();
    }

//    controller config
    @Bean
    @ConditionalOnMissingBean
    public DistributedController distributedController(){
        return new DefaultDistributedController();
    }
//    service config
    @Bean
    public PushService pushService(){
        return new PushServiceImpl();
    }
    @Bean
    public MessageService messageService(){
        return new MessageServiceImpl();
    }
    @Bean
    public SessionService sessionService(){
        return new SessionServiceImpl();
    }
    @Bean
    public ContactService userService(){
        return new ContactServiceImpl();
    }
    @Bean
    public ChatGroupService chatGroupService(){
        return new ChatGroupServiceImpl();
    }
    @Bean
    DistributedMsgService distributedMsgService(PushService pushService, MessageService messageService,@Nullable CustomMsgService customMsgService){
        return new DistributedMsgService().setPushService(pushService).setMessageService(messageService).setCustomMsgService(customMsgService);
    }
//    woim config
    @Bean
    @ConditionalOnMissingBean
    public Agent woimAdapter(){
        return new DefaultAgent(serverAddr);
    }

    @Bean
    @ConditionalOnMissingBean
    public WOIMEventListener woimEventListener(SessionService sessionService){
        return new DefaultWOIMEventListener(sessionService);
    }

    @Bean
    @ConditionalOnMissingBean
    public WOIMConfigurator woimConfigurator(){
        return new SimpleWOIMConfigurator();
    }

    @Bean
    @ConditionalOnMissingBean
    public GroupIdGenerator groupIdGenerator(){
        return new SnowflakeIdGenerator(datacenterId,machineId);
    }

    @Bean
    @ConditionalOnMissingBean
    public LocalSessionStorage localSessionStorage(){
        return new MapLocalSessionStorage();
    }

    @Bean
    public WOIMServer.EventListener serverEventListener(SessionService sessionService, WOIMEventListener woimEventListener){
        return new WOIMServerEventListener(sessionService,woimEventListener);
    }


    @Bean
    public WOIMServer.Configurator serverConfigurator(WOIMConfigurator configurator){
        return new WOIMServerConfigurator(configurator);
    }

    @Bean
    public WOIMServer woimServer(WOIMServer.EventListener eventListener, WOIMServer.Configurator configurator){
        WOIMServer WOIMServer = new WOIMServer.Builder()
                .setPort(connectionPort)
                .setConfigurator(configurator)
                .setEventListener(eventListener)
                .build();
        return WOIMServer;
    }


    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        applicationContext.getBean(WOIMServer.class).bind();
        new DBInitializer(applicationContext.getBean(WOIMInitMapper.class)).init(dbInitializationMode);
    }
}

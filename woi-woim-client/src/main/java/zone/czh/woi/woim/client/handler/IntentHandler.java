package zone.czh.woi.woim.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import zone.czh.woi.woim.base.annotation.intent.IntentConsumer;
import zone.czh.woi.woim.base.channel.codec.PacketHandler;
import zone.czh.woi.woim.base.obj.vo.Packet;
import zone.czh.woi.woim.base.obj.vo.WOIMIntent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*@ClassName: IntentHandler
*@Description: None
*@author woi
*/
@ChannelHandler.Sharable
public class IntentHandler extends PacketHandler<WOIMIntent> {
    @Getter
    Map<Object,List<Consumer>> handlerConsumerMap = new HashMap<>();
    @Getter
    Map<String,List<Consumer>> consumerMap = new HashMap<>();

    public IntentHandler(){
        super(WOIMIntent.class);
    }


    @Override
    protected void handle(ChannelHandlerContext ctx, Packet<WOIMIntent> packet, List<Object> out) throws Exception {
        WOIMIntent intent = packet.getData();
        List<Consumer> consumers = consumerMap.get(intent.getCmd());
        if (consumers !=null){
            for (Consumer consumer : consumers){
                consumer.invoke(packet);
            }
        }
    }

    public synchronized void  addHandler(Object handler){
        if (handler!=null){
            Method[] methods = handler.getClass().getDeclaredMethods();
            for (Method method:methods){
                if (method.isAnnotationPresent(IntentConsumer.class)){
                    IntentConsumer annotation = method.getAnnotation(IntentConsumer.class);
                    String cmd = annotation.value();
                    if (cmd!=null){
                        List<Consumer> consumers = consumerMap.get(cmd);
                        if (consumers ==null){
                            consumers = new ArrayList<>();
                        }
                        Consumer consumer = new Consumer(cmd, method, handler);
                        consumers.add(consumer);
                        consumerMap.put(cmd, consumers);
                        //更新handler对应的consumer数组，方便remove handler
                        List<Consumer> handlerConsumers = handlerConsumerMap.get(handler);
                        if (handlerConsumerMap.get(handler)==null){
                            handlerConsumers = new ArrayList<>();
                        }
                        handlerConsumers.add(consumer);
                        handlerConsumerMap.put(handler,handlerConsumers);
                    }
                }
            }
        }
    }

    public synchronized void removeHandler(Object handler){
        if (handler!=null){
            List<Consumer> consumers = handlerConsumerMap.get(handler);
            if (consumers!=null){
                //移除映射关系
                for (Consumer consumer:consumers){
                    if (consumer!=null){
                        List<Consumer> targetList = consumerMap.get(consumer.getCmd());
                        targetList.remove(consumer);
                    }
                }
                //移除保存的consumer
                handlerConsumerMap.remove(handler);
            }

        }
    }

    @Data
    @AllArgsConstructor
    private static class Consumer {
        private String cmd;
        private Method method;
        private Object obj;
        public Object invoke(Packet<WOIMIntent> packet) throws InvocationTargetException, IllegalAccessException {
            return method.invoke(obj,packet);
        }
    }


}

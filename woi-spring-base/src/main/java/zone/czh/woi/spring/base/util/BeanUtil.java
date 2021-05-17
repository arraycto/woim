package zone.czh.woi.spring.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
*@ClassName: BeanUtil
*@Description: None
*@author woi
*/
public class BeanUtil implements ApplicationContextAware {
    protected static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static<T>  T getBean(Class<T> c){
        if (context==null){
            return null;
        }
        return context.getBean(c);
    }
}

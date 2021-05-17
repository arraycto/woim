package zone.czh.woi.woim.base.annotation.intent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
*@ClassName: IntentConsumer
*@Description: None
*@author woi
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntentConsumer {
    String value() default "";
}

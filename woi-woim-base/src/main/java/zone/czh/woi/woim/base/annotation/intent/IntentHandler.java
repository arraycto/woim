package zone.czh.woi.woim.base.annotation.intent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
*@ClassName: IntentHandler
*@Description: None
*@author woi
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntentHandler {
    String value() default "default";
}

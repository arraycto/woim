package zone.czh.woi.woim.annotation;

import org.springframework.context.annotation.Import;
import zone.czh.woi.woim.config.WOIMAutoConfiguration;

import java.lang.annotation.*;
/**
*@ClassName: EnableWOIM
*@Description: None
*@author woi
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(WOIMAutoConfiguration.class)
@Documented
public @interface EnableWOIM {
}

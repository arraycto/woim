package zone.czh.woi.woim.example.server.generator;

import org.springframework.stereotype.Component;
import zone.czh.woi.woim.generator.GroupIdGenerator;

/**
*@ClassName: ExampleGroupIdGenerator
*@Description:
 * 如果你需要自定义群组id生成器则可以实现GroupIdGenerator接口，或者使用默认的SnowflakeIdGenerator
*@author woi
*/
@Component
public class ExampleGroupIdGenerator implements GroupIdGenerator {
    @Override
    public long nextGroupId() {
        return 0;
    }
}

package zone.czh.woi.woim.generator;

import zone.czh.woi.spring.base.util.SnowFlake;
/**
*@ClassName: SnowflakeIdGenerator
*@Description: None
*@author woi
*/
public class SnowflakeIdGenerator implements GroupIdGenerator{
    SnowFlake snowFlake;
    public SnowflakeIdGenerator(long datacenterId, long machineId){
        snowFlake = new SnowFlake(datacenterId,machineId);
    }
    @Override
    public long nextGroupId() {
        return snowFlake.nextId();
    }
}

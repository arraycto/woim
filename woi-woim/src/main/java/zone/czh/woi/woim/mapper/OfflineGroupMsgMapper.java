package zone.czh.woi.woim.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import zone.czh.woi.woim.base.obj.po.OfflineGroupMsg;

import java.util.List;

public interface OfflineGroupMsgMapper extends Mapper<OfflineGroupMsg>, MySqlMapper<OfflineGroupMsg> {
    @Select("SELECT a.* FROM offline_group_msg a INNER JOIN (\n" +
            "SELECT group_id,MAX(create_time) create_time\n" +
            "FROM offline_group_msg \n" +
            "WHERE dest_uid=#{uid} \n" +
            "GROUP BY group_id) b \n" +
            "ON a.group_id=b.group_id AND a.create_time = b.create_time")
    List<OfflineGroupMsg> getLatestOfflineMsg(String uid);
}

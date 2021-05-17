package zone.czh.woi.woim.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import zone.czh.woi.woim.base.obj.po.OfflinePrivateMsg;

import java.util.List;

public interface OfflinePrivateMsgMapper extends Mapper<OfflinePrivateMsg>, MySqlMapper<OfflinePrivateMsg> {
    @Select("SELECT a.* FROM offline_private_msg a INNER JOIN (\n" +
            "SELECT src_uid,MAX(create_time) create_time\n" +
            "FROM offline_private_msg \n" +
            "WHERE dest_uid=#{uid} \n" +
            "GROUP BY src_uid) b \n" +
            "ON a.src_uid=b.src_uid AND a.create_time = b.create_time")
    List<OfflinePrivateMsg> getLatestOfflineMsg(String uid);

}

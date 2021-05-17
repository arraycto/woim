package zone.czh.woi.woim.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import zone.czh.woi.woim.base.obj.po.Contact;

public interface ContactMapper extends Mapper<Contact>, MySqlMapper<Contact> {
}

package zone.czh.woi.woim.base.obj.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import zone.czh.woi.woim.base.obj.po.GroupMember;

import java.util.List;
/**
*@ClassName: ChatGroupInfo
*@Description: None
*@author woi
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatGroupInfo {
    Long id;
    String name;
    String avatar;
    String desc;
    List<GroupMember> groupMembers;

}

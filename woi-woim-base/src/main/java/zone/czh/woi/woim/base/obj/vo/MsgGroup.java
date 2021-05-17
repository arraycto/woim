package zone.czh.woi.woim.base.obj.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
*@ClassName: MsgGroup
*@Description: None
*@author woi
*/
@Data
@Accessors(chain = true)
public class MsgGroup {
    List<PrivateMsgGroup> privateMsgGroups;
    List<GroupMsgGroup> groupMsgGroups;
}

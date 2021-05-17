package zone.czh.woi.woim.base.obj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import zone.czh.woi.woim.base.constant.MsgCst;

/**
*@ClassName: GroupMsg
*@Description: None
*@author woi
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GroupMsg {
    private String srcUid;
    private Long groupId;
    private int type= MsgCst.Type.TEXT;
    private String content;
    private long timestamp=System.currentTimeMillis();
}

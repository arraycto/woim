package zone.czh.woi.woim.base.obj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import zone.czh.woi.woim.base.constant.MsgCst;

/**
*@ClassName: PrivateMsg
*@Description: None
*@author woi
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PrivateMsg {
    private String srcUid;
    private String destUid;
    private int type= MsgCst.Type.TEXT;
    private String content;
    private long timestamp=System.currentTimeMillis();
}

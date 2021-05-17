package zone.czh.woi.woim.base.obj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MsgState {
    public static final int STATE_SUCCESS=0;
    public static final int STATE_OUT_OF_TOUCH=1;
    Integer state;
}

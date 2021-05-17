package zone.czh.woi.woim.base.obj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SessionState {
    public static final int OFFLINE = 0;
    public static final int ONLINE = 1;
    Integer state;
}

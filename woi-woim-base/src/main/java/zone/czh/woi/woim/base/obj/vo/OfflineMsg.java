package zone.czh.woi.woim.base.obj.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import zone.czh.woi.woim.base.obj.po.OfflineGroupMsg;
import zone.czh.woi.woim.base.obj.po.OfflinePrivateMsg;

import java.util.List;

@Data
@Accessors(chain = true)
public class OfflineMsg {
    List<OfflinePrivateMsg> privateMsgs;
    List<OfflineGroupMsg> groupMsgs;
}

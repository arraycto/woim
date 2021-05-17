package zone.czh.woi.woim.base.obj.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import zone.czh.woi.woim.base.obj.po.WOIMSession;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PushInfo {
    public static ObjectMapper objectMapper=new ObjectMapper();
    WOIMSession session;
    String dcn;
    String data;
    public PushInfo(WOIMSession session,Object data) throws JsonProcessingException {
        this.session=session;
        this.dcn=data.getClass().getName();
        this.data=objectMapper.writeValueAsString(data);
    }
    public Object parseData() throws ClassNotFoundException, JsonProcessingException {
        return objectMapper.readValue(data,Class.forName(dcn));
    }
}

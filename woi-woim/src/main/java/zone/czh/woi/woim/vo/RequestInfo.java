package zone.czh.woi.woim.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;
/**
*@ClassName: RequestInfo
*@Description: None
*@author woi
*/
@Data
@Accessors(chain = true)
public class RequestInfo {
    String path;
    HttpMethod method=HttpMethod.POST;
    HttpEntity httpEntity;
    ParameterizedTypeReference reference;
    Map<String,?> uriVariables=new HashMap<>();

}

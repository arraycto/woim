package zone.czh.woi.base.web.usual;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import zone.czh.woi.base.web.constant.ErrorCode;

/**
*@ClassName: ResponseApi
*@Description: None
*@author woi
*/
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

    //业务操作成功可直接new对象
    public Response() {
        this.code = ErrorCode.CODE_SUCCESS;
        this.message = ErrorCode.MESSAGE_SUCCESS;
        this.data=null;
    }

    //正常返回可直接添加data
    public Response(T data) {
        this.code = ErrorCode.CODE_SUCCESS;
        this.message = ErrorCode.MESSAGE_SUCCESS;
        this.data = data;
    }
}

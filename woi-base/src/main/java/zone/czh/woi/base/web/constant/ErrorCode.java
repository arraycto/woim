package zone.czh.woi.base.web.constant;
/**
*@ClassName: ErrorCode
*@Description: None
*@author woi
*/
public class ErrorCode {
//    CODE_
    /**
     * 1000 成功码
     * 2--- 权限认证错误
     * 3--- 请求错误
     * 4--- 其他错误
     * 5000 常规总错误码（任何错误皆可使用）
     */
    //1000 成功码
    public static final int CODE_SUCCESS = 1000;
    //2--- 权限认证错误
    public static final int CODE_LOG_IN_ERR = 2001;
    public static final int CODE_SIGN_UP_ERR = 2002;
    public static final int CODE_TOKEN_EXPIRED = 2003;
    public static final int CODE_TOKEN_SIGNATURE_ERR = 2004;
    public static final int CODE_GENERAL_ERR=5000;

//    MESSAGE_
    public static final String MESSAGE_SUCCESS = "success";
    public static final String MESSAGE_LOG_IN_ERR = "log in err";
    public static final String MESSAGE_SIGN_UP_ERR = "sign up err";
    public static final String MESSAGE_TOKEN_EXPIRED = "token expired";
    public static final String MESSAGE_TOKEN_SIGNATURE_ERR = "token signature err";
    public static final String MESSAGE_GENERAL_ERR="error";
}

package zone.czh.woi.base.util;
/**
*@ClassName: CommonUtil
*@Description: None
*@author woi
*/
public class CommonUtil {
    /**
     * 空值校验
     * @param params 需要校验的参数
     * @throws NullPointerException 返回校验参数表第几个参数为空的异常信息
     */
    public static void checkNull(Object... params) throws NullPointerException {
        int len = params.length;
        for (int i = 0; i < len; i++) {
            if (params[i] == null) {
                throw new NullPointerException("params[" + i + "] is null");
            }
        }
    }
}

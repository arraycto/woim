package zone.czh.woi.spring.base.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
*@ClassName: WoiNetUtil
*@Description: None
*@author woi
*/
public class WoiNetUtil {
    /**
     * 获取请求ip
     */
    public static String getRequestIp(HttpServletRequest request){
        //获取客户端IP地址
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {
            ip = request.getHeader ("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {
            ip = request.getRemoteAddr ();
            if (ip.equals ("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost ();
                } catch (Exception e) {
                    e.printStackTrace ();
                }
                ip = inet.getHostAddress ();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.indexOf(",")!=-1) {
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }
        return ip;
    }

    public static boolean isLocal(String ip){
        if ("127.0.0.1".equals(ip)||"localhost".equals(ip)){
            return true;
        }
        List<InetAddress> inetAddresses = getAllInetAddress();
        for (InetAddress address:inetAddresses){
            if (address.getAddress().length==4){
                if (address.getHostAddress().equals(ip)){
                    return true;
                }
            }
        }
        return false;
    }

    private static void test(){
        try {
            Enumeration<NetworkInterface> interfaces=null;
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                System.out.println();
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresss = ni.getInetAddresses();
                while(addresss.hasMoreElements()){
                    InetAddress nextElement = addresss.nextElement();
                    System.out.println(nextElement.getAddress().length);
                    String hostAddress = nextElement.getHostAddress();
                    System.out.println("本机IP地址为：" +hostAddress);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLocalHostName() {
        String hostName;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostName = addr.getHostName();
        } catch (Exception ex) {
            hostName = "";
        }
        return hostName;
    }

    public static List<InetAddress> getAllInetAddress() {
        List<InetAddress> ret = null;
        try {
            String hostName = getLocalHostName();
            if (hostName.length() > 0) {
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                ret =  Arrays.asList(addrs);
            }

        } catch (Exception ex) {
            ret = null;
        }
        return ret;
    }


}

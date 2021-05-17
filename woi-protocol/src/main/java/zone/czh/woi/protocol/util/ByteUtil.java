package zone.czh.woi.protocol.util;

import java.nio.charset.Charset;

public class ByteUtil
{

    public static byte[] getBytes(int data){
        int len = Integer.SIZE / Byte.SIZE;
        byte[] bytes = new byte[len];
        len-=1;
        for (int i=0;i<len;i++){
            bytes[len-i] = (byte) ((data>>(i*Byte.SIZE))&0xff);
        }
        return bytes;
    }

    public static byte[] getBytes(short data){
        int len = Short.SIZE / Byte.SIZE;
        byte[] bytes = new byte[len];
        len-=1;
        for (int i=0;i<len;i++){
            bytes[len-i] = (byte) ((data>>(i*Byte.SIZE))&0xff);
        }
        return bytes;
    }

    public static byte[] getBytes(long data){
        int len = Long.SIZE / Byte.SIZE;
        byte[] bytes = new byte[len];
        len-=1;
        for (int i=0;i<len;i++){
            bytes[len-i] = (byte) ((data>>(i*Byte.SIZE))&0xff);
        }
        return bytes;
    }



    public static void main(String[] args)
    {
        byte[] bytes = getBytes((int)879);
        for (byte b:bytes){
            System.out.println(b);
        }
    }

}
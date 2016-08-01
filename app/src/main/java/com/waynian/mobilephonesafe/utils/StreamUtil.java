package com.waynian.mobilephonesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by waynian on 2016/7/31.
 */
public class StreamUtil {
    /**
     *
     * @param is 流对象
     * @return  流转换成对象，返回null表示异常
     */
    public static String streamToString(InputStream is) {
        //1,在读取的过程中,将读取的内容存储值缓存中,然后一次性的转换成字符串返回
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //2,读流操作,读到没有为止(循环)
        byte[] buffer = new byte[1024];
        //3,记录读取内容的临时变量
        int temp = -1;
        try {
            while((temp = is.read(buffer))!=-1){
                bos.write(buffer, 0, temp);
            }
            //返回读取数据
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                is.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

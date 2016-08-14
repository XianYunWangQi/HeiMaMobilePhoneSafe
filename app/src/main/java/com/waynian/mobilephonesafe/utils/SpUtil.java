package com.waynian.mobilephonesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by waynian on 2016/8/10.
 */
public class SpUtil {

    private static SharedPreferences sp;

    /**
     * @param context 上下文
     * @param key     存储节点的名称
     * @param value   存储节点的值
     */
    public static void putBoolean(Context context, String key, boolean value) {
        //存储节点文件名称，读写方式
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * @param context  上下文
     * @param key      存储节点的名称
     * @param defValue 没有此节点的默认值
     * @return 默认值或此节点读取到的值
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        //存储节点文件名称，读写方式
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);

    }

    /**
     * @param context 上下文
     * @param key     存储节点的名称
     * @param value   存储节点的值
     */
    public static void putString(Context context, String key, String value) {
        //存储节点文件名称，读写方式
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * @param context  上下文
     * @param key      存储节点的名称
     * @param defValue 没有此节点的默认值
     * @return 默认值或此节点读取到的值
     */
    public static String getString(Context context, String key, String defValue) {
        //存储节点文件名称，读写方式
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);

    }
}

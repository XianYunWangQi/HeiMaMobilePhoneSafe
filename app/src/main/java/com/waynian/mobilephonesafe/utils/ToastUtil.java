package com.waynian.mobilephonesafe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by waynian on 2016/8/1.
 */
public class ToastUtil {
    public static void show(Context ctx,String msg){
        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
    }
}

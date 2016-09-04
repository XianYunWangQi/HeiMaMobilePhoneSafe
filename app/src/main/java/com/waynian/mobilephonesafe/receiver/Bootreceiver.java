package com.waynian.mobilephonesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.waynian.mobilephonesafe.utils.ConstantValue;
import com.waynian.mobilephonesafe.utils.SpUtil;

/**
 * Created by waynian on 2016/9/4.
 */
public class Bootreceiver extends BroadcastReceiver{
    private static final String TAG = "Bootreceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //一旦监听到开机广播，就将广播发送到广播接收者
        Log.d(TAG, "onReceive: 接收开关机的广播，可在此处做SIM卡是否切换的操作的判断。。。");

        //1.获取本地存储的SIM卡序列号
        String spSerialNumber = SpUtil.getString(context, ConstantValue.SIM_NUMBER,"");
        //2.获取当前插入手机的SIM卡序列号
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = manager.getSimSerialNumber();
        //3.两个SIM卡序列号比对
        if (spSerialNumber.equals(simSerialNumber)){
            //4.如果序列号不一致，则给指定联系人发送短信（发送短信权限）
            SmsManager sm = SmsManager.getDefault();
            String phone = SpUtil.getString(context,ConstantValue.CONTACT_PHONE,"");
            sm.sendTextMessage(phone,null,"sim change!!!",null,null);
        }

    }
}

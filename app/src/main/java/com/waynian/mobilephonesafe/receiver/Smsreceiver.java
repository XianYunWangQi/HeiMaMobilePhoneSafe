package com.waynian.mobilephonesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.waynian.mobilephonesafe.R;
import com.waynian.mobilephonesafe.service.LocationService;
import com.waynian.mobilephonesafe.utils.ConstantValue;
import com.waynian.mobilephonesafe.utils.SpUtil;

/**
 * Created by waynian on 2016/9/4.
 */
public class Smsreceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        //1.判断是否开启了防盗保护
        boolean open_security = SpUtil.getBoolean(context, ConstantValue.OPEN_SECURITY,false);
        if (open_security) {
            //2.获取短信内容
            Object[] objects = (Object[]) intent.getExtras().get("pdus");
            //3.循环遍历短信
            for (Object object : objects){
                //4.获取短信对象
                SmsMessage sms = SmsMessage.createFromPdu((byte[])object);
                //5.获取短信对象的基本信息
                String originatingaddress  = sms.getOriginatingAddress();
                String messageBody = sms.getMessageBody();

                //6.判断是否包换播放音乐的关键字
                if (messageBody.contains("#*alaem*#")){
                    //7.播放音乐(准备音乐)
                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }

                if(messageBody.contains("#*location*#")){
                    //8.开启位置服务
                    context.startService( new Intent(context,LocationService.class));

                }
            }
        }
    }
}

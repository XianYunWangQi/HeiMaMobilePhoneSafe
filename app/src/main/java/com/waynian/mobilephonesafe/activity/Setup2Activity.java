package com.waynian.mobilephonesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.waynian.mobilephonesafe.R;
import com.waynian.mobilephonesafe.utils.ConstantValue;
import com.waynian.mobilephonesafe.utils.SpUtil;
import com.waynian.mobilephonesafe.utils.ToastUtil;
import com.waynian.mobilephonesafe.view.SettingItemView;

public class Setup2Activity extends AppCompatActivity {
    private static final String TGA = "Setup2Activity";
    private SettingItemView siv_sim_bound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        initUI();
    }

    private void initUI() {
        siv_sim_bound = (SettingItemView) findViewById(R.id.siv_sim_bound);
        //1.回显（读取已有的绑定状态，用作显示，sp是否存储了SIM卡的序列号）
        String sim_number = SpUtil.getString(this, ConstantValue.SIM_NUMBER, "");
        //2.判断序列卡号是否为空
        if (TextUtils.isEmpty(sim_number)) {
            siv_sim_bound.setCheck(false);
        } else {
            siv_sim_bound.setCheck(true);
        }

        siv_sim_bound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //3.获取原有的状态
                boolean isCheck = siv_sim_bound.isCheck();
                Log.d(TGA, String.valueOf(isCheck));
                //4.将原有的状态取反，设置给当前条目
                siv_sim_bound.setCheck(!isCheck);
                //5.存储序列卡号
                if (!isCheck){
                    //6.存储序列卡号
                    //6.1获取SIM卡序列号
                    TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String simSerialNumber = manager.getSimSerialNumber();
                    //6.3存储
                    SpUtil.putString(getApplicationContext(),ConstantValue.SIM_NUMBER,simSerialNumber);

                }else {
                    //7.将存储序列卡号的节点从sp删除
                    SpUtil.remove(getApplicationContext(),ConstantValue.SIM_NUMBER);

                }


            }
        });


    }

    public void nextPage(View v) {
        String serialNumber = SpUtil.getString(this,ConstantValue.SIM_NUMBER,"");
        if (!TextUtils.isEmpty(serialNumber)) {
            Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
            startActivity(intent);
            finish();
        }else {
            ToastUtil.show(this,"请绑定SIM卡");
        }

        //开启动画
        overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
    }

    public void prePage(View v) {
        Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
        startActivity(intent);
        finish();

        //开启动画
        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }
}

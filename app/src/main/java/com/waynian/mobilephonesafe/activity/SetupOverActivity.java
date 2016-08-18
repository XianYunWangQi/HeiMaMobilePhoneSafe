package com.waynian.mobilephonesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.waynian.mobilephonesafe.R;
import com.waynian.mobilephonesafe.utils.ConstantValue;
import com.waynian.mobilephonesafe.utils.SpUtil;

public class SetupOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER, false);
        if (setup_over) {
            //密码输入成功，并且四个导航界面设置完成 ---->停留在设置完成功能列表界面
            setContentView(R.layout.activity_setup_over);
        } else {
            //密码输入成功，四个导航界面没有设置完成 ---->跳转到导航界面第一个
            Intent intent = new Intent(this,Setup1Activity.class);
            startActivity(intent);
            finish();
        }
    }
}

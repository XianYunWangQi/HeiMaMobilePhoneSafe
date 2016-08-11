package com.waynian.mobilephonesafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.waynian.mobilephonesafe.R;
import com.waynian.mobilephonesafe.utils.ConstantValue;
import com.waynian.mobilephonesafe.utils.SpUtil;
import com.waynian.mobilephonesafe.view.SettingItemView;

public class SettingActivity extends AppCompatActivity {
    private String TGA = "SettingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUpdate();
    }

    private void initUpdate() {

        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
        //获取已有的状态，用作显示
        boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE,false);
        //是否选中，根据上一次存储的结果去决定
        siv_update.setCheck(open_update);
        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取之前的选中状态
                boolean isCheck = siv_update.isCheck();
                //将原有状态取返
                siv_update.setCheck(!isCheck);
                //将取返后的状态存入
                SpUtil.putBoolean(getApplicationContext(),ConstantValue.OPEN_UPDATE,!isCheck);
            }
        });
    }

}

package com.waynian.mobilephonesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.waynian.mobilephonesafe.R;
import com.waynian.mobilephonesafe.utils.ConstantValue;
import com.waynian.mobilephonesafe.utils.SpUtil;
import com.waynian.mobilephonesafe.utils.ToastUtil;

public class Setup4Activity extends AppCompatActivity {

    private CheckBox cb_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        initUI();
    }

    private void initUI() {
        cb_box = (CheckBox) findViewById(R.id.cb_box);
        //1.是否选中状态的回显
        boolean open_security = SpUtil.getBoolean(this, ConstantValue.OPEN_SECURITY, false);
        //2.根据状态，修改checkbox的后续文字显示
        if (open_security) {
            cb_box.setText("安全设置已开启");
        } else {
            cb_box.setText("安全设置已关闭");
        }
        cb_box.setChecked(open_security);
        //3.点击过程中，监听checkbox的状态的切换
//        cb_box.setChecked(!cb_box.isChecked());
        cb_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //isChecked  点击后的状态
                //4.状态的存储
                SpUtil.putBoolean(getApplicationContext(),ConstantValue.OPEN_SECURITY,isChecked);
                if (isChecked) {
                    cb_box.setText("安全设置已开启");
                } else {
                    cb_box.setText("安全设置已关闭");
                }
            }
        });

    }

    public void nextPage(View v) {
        boolean open_security = SpUtil.getBoolean(this, ConstantValue.OPEN_SECURITY, false);
        if (open_security) {
            Intent intent = new Intent(getApplicationContext(), MobileSafeActivity.class);
            startActivity(intent);
            finish();
            SpUtil.putBoolean(getApplicationContext(), ConstantValue.SETUP_OVER, true);
        }else {
            ToastUtil.show(this,"请开始安全设置");
        }

        //开启动画
        overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
    }

    public void prePage(View v) {
        Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
        startActivity(intent);
        finish();

        //开启动画
        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }
}

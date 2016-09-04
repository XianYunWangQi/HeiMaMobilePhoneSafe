package com.waynian.mobilephonesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.waynian.mobilephonesafe.R;
import com.waynian.mobilephonesafe.utils.ConstantValue;
import com.waynian.mobilephonesafe.utils.SpUtil;
import com.waynian.mobilephonesafe.utils.ToastUtil;

public class Setup3Activity extends AppCompatActivity {
    private EditText et_phone_number;
    private Button bt_select_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initUI();
    }

    private void initUI() {
        //显示号码的输入框
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        //获取联系人的回显
        String contact_phone = SpUtil.getString(getApplicationContext(),ConstantValue.CONTACT_PHONE,"");
        et_phone_number.setText(contact_phone);
        //点击选择联系人的按钮
        bt_select_number = (Button) findViewById(R.id.bt_select_number);
        bt_select_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    //返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            //返回当前界面的时候，接收返回的结果
            String phone = data.getStringExtra("phone");
            //过滤特殊字符(中划线转换成空字符串)
            phone = phone.replace("-", "").replace(" ", "").trim();
            et_phone_number.setText(phone);
            //3.存储联系人到Sp中
            SpUtil.putString(getApplicationContext(), ConstantValue.CONTACT_PHONE, phone);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void nextPage(View v) {
        String phone = et_phone_number.getText().toString();
        if (!TextUtils.isEmpty(phone)) {
            Intent intent = new Intent(getApplicationContext(), Setup4Activity.class);
            startActivity(intent);
            finish();
            //如果现在输入的电话号码，则需要去保存
            SpUtil.putString(getApplicationContext(), ConstantValue.CONTACT_PHONE, phone);
        }else {
            ToastUtil.show(getApplicationContext(),"请输入电话号码");
        }
        //开启动画
        overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
    }

    public void prePage(View v) {
        String phone = et_phone_number.getText().toString();
            Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
            startActivity(intent);
            finish();
            //如果现在输入的电话号码，则需要去保存
            SpUtil.putString(getApplicationContext(), ConstantValue.CONTACT_PHONE, phone);
        //开启动画
        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }

}


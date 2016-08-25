package com.waynian.mobilephonesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.waynian.mobilephonesafe.R;

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
        //点击选择联系人的按钮
        bt_select_number = (Button) findViewById(R.id.bt_select_number);
        bt_select_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ContactListActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }

    //返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void nextPage(View v) {
        Intent intent = new Intent(getApplicationContext(), Setup4Activity.class);
        startActivity(intent);
        finish();
    }

    public void prePage(View v) {
        Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
        startActivity(intent);
        finish();
    }
}


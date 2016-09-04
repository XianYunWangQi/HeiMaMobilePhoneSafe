package com.waynian.mobilephonesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.waynian.mobilephonesafe.R;
import com.waynian.mobilephonesafe.utils.ConstantValue;
import com.waynian.mobilephonesafe.utils.SpUtil;

public class MobileSafeActivity extends AppCompatActivity {
    private TextView tv_safe_number,tv_reset_setup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mobile_safe);

        initUI();
    }

    private void initUI() {
        tv_safe_number = (TextView) findViewById(R.id.tv_safe_number);
        tv_reset_setup = (TextView) findViewById(R.id.tv_reset_setup);
        String safe_number = SpUtil.getString(getApplicationContext(), ConstantValue.CONTACT_PHONE,"");
       tv_safe_number.setText(safe_number);

        tv_reset_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),Setup1Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
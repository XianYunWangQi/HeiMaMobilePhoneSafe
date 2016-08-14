package com.waynian.mobilephonesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.waynian.mobilephonesafe.R;
import com.waynian.mobilephonesafe.utils.ConstantValue;
import com.waynian.mobilephonesafe.utils.Md5Util;
import com.waynian.mobilephonesafe.utils.SpUtil;
import com.waynian.mobilephonesafe.utils.ToastUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private GridView gv_home;
    private String[] mTitleStr;
    private int[] mImageId;
    private EditText ed_set_psd;
    private EditText ed_confir_psd;
    private EditText ed_get_confir_psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        //初始化数据的方法
        initDate();
    }

    private void initDate() {
        //9组图片，9组文字
        mTitleStr = new String[]{
                "手机防盗", "通信卫士", "软件管理",
                "进程管理", "流量统计", "手机杀毒",
                "缓存清理", "高级工具", "设置中心"};
        mImageId = new int[]{
                R.drawable.home_safe, R.drawable.home_callmsgsafe,
                R.drawable.home_safe, R.drawable.home_taskmanager, R.drawable.home_netmanager,
                R.drawable.home_trojan, R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings};

        //九宫格的数据适配器
        gv_home.setAdapter(new MyAdapter());
        //注册九宫格的点击事件方法
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position 点中列表条目的索引
                switch (position) {
                    case 0:
                        showDialog();
                        break;
                    case 8:
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void showDialog() {
        //判断本地是否有存储密码(sp 字符串)
        String psd = SpUtil.getString(this, ConstantValue.MOBILE_SAFE_PSD, "");
        if (TextUtils.isEmpty(psd)) {
            //1.初始设置密码的对话框
            showSetDialog();
        } else {
            //2.确认密码对话框
            showConfirmPsdDialog();
        }


    }

    /**
     * 确认密码对话框
     */
    private void showConfirmPsdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_confirm_psd, null);
        //对话框显示自己定义的一个界面
        dialog.setView(view);
        dialog.show();
        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        ed_get_confir_psd = (EditText) view.findViewById(R.id.ed_get_confir_psd);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String psd = ed_get_confir_psd.getText().toString();
                String md5_psd = Md5Util.encoder(psd);
                String get_psd = SpUtil.getString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, "");
                if (md5_psd.equals(get_psd)) {
                    //进入手机防盗模块
                    Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                    startActivity(intent);
                    dialog.dismiss();

                } else {
                    //提示用户密码不能为空
                    ToastUtil.show(getApplicationContext(), "密码错误");

                }

            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

    /**
     * 设置密码的对话框
     */
    private void showSetDialog() {
        //需要自己定义对话框的展示样式，要调用dialog.setView(view);
        //view是自己定义的布局
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_set_psd, null);
        //对话框显示自己定义的一个界面
        dialog.setView(view);
        dialog.show();
        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        ed_set_psd = (EditText) view.findViewById(R.id.ed_set_psd);
        ed_confir_psd = (EditText) view.findViewById(R.id.ed_confir_psd);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String psd = ed_set_psd.getText().toString();
                String confirmPsd = ed_confir_psd.getText().toString();
                String md5_psd = Md5Util.encoder(psd);
                if (!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmPsd)) {
                    if (psd.equals(confirmPsd)) {
                        //进入手机防盗模块
                        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                        startActivity(intent);
                        SpUtil.putString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, md5_psd);
                        dialog.dismiss();

                    } else {
                        //提示用户两次输入的密码不同
                        ToastUtil.show(getApplicationContext(), "两次输入的密码不同");
                    }
                } else {
                    //提示用户密码不能为空
                    ToastUtil.show(getApplicationContext(), "密码不能为空");

                }

            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


    }

    private void initUI() {
        gv_home = (GridView) findViewById(R.id.gv_home);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mImageId.length;
        }

        @Override
        public Object getItem(int position) {
            return mImageId[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.gridview_item, null);
//            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

            tv_title.setText(mTitleStr[position]);
            iv_icon.setBackgroundResource(mImageId[position]);
            return view;
        }
    }
}
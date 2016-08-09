package com.waynian.mobilephonesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.waynian.mobilephonesafe.R;

public class MainActivity extends AppCompatActivity {

    private GridView gv_home;
    private String[] mTitleStr;
    private int[] mImageId;

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
                switch (position){
                    case 0:
                        break;
                    case 8:
                        Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                        startActivity(intent);
                        break;
                }
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
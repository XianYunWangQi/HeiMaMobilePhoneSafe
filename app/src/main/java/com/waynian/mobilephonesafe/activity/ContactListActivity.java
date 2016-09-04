package com.waynian.mobilephonesafe.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.waynian.mobilephonesafe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    private static final String TAG = "ContactListActivity";
    private ListView lv_contact;
    private List<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
    private MyAdapter mAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //8.填充数据适配器
            mAdapter = new MyAdapter();
            lv_contact.setAdapter(mAdapter);
        }
    };
    private MyAdapter mAdapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initUI();
        //获取系统联系人
        initData();
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return contactList.size();
        }

        @Override
        public HashMap<String, String> getItem(int position) {
            return contactList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),R.layout.listview_contact_item,null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);

            tv_name.setText(contactList.get(position).get("name"));
            tv_phone.setText(contactList.get(position).get("phone"));
            return view;
        }
    }

    private void initUI() {
        lv_contact = (ListView) findViewById(R.id.lv_contact);
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.获取点中条目的索引指向集合的对象
                if (mAdapter!=null){
                HashMap<String,String> hashMap = mAdapter1.getItem(position);
                    //2.获取当前条目指向的电话号码
                    String phone = hashMap.get("phone");
                    //3.此电话号码需要给第三个导航页面使用
                    Intent intent = new Intent();
                    intent.putExtra("phone",phone);
                    setResult(0,intent);
                    //4.在结束此界面，返回前一个界面，需要把数据传递过去
                    finish();
                }

            }
        });

    }

    private void initData() {
        //读取联系人可能是一个耗时操作
        new Thread() {
            @Override
            public void run() {
                //1.获取内容解析器的对象
                ContentResolver contentResolver = getContentResolver();
                //2.查询系统联系人的过程（读取联系人权限）
                Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/raw_contacts"),
                        new String[]{"contact_id"}, null, null, null);
                //3.循环游标，直至没有为止
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
//                    Log.d(TAG,"id = " +id);
                    //4.根据用户的唯一性id值，查询data表和mimetype表生成的师视图，获取data以及mimetype的字段
                    Cursor indexCursor = contentResolver.query(Uri.parse("content://com.android.contacts/data"),
                            new String[]{"data1", "mimetype"}, "raw_contact_id = ?",
                            new String[]{id}, null);

                    contactList.clear();
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    while (indexCursor.moveToNext()) {
                        //5.循环获取每一个联系人的姓名和联系电话，数据类型
                        String data = indexCursor.getString(0);
                        String mimetype = indexCursor.getString(1);

                        //6.区分类型，给hashMap填充数据
                        if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                            if (!TextUtils.isEmpty(data)) {
                                hashMap.put("phone", data);
                            }
                        } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                            if (!TextUtils.isEmpty(data)) {
                                hashMap.put("name", data);
                            }
                        }
                        contactList.add(hashMap);
                    }
                    indexCursor.close();
                }
                cursor.close();
                //7.消息机制,发送一个空的消息，告知主线程可以使用子线程已经填充好的数据
                mHandler.sendEmptyMessage(0);
            }
        }.start();


    }


}

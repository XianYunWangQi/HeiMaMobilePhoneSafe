package com.waynian.mobilephonesafe.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.waynian.mobilephonesafe.BuildConfig;
import com.waynian.mobilephonesafe.R;
import com.waynian.mobilephonesafe.utils.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {

    private TextView tv_version_name;
    private int mLocal_Versioncode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏,必须要继承Activity，不能继承AppCompatActivity
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        //初始化UI
        initUI();

        //初始化数据
        initDate();
    }


    /**
     * 获取数据方法
     */
    private void initDate() {
        //1.获取版本名称
        getVersionName();
        //检测是否有更新
        //本地版本号和服务器版本号是否相同，不同，则提示有新版本更新
        //2.获取本地版本号
        getVersionCode();
        //3.获取服务器版本号(客户端请求，服务端响应)(json,xml)
        /*
        json包含的内容：
        更新版本的版本名称
        新版本的描述信息
        服务器版本号
        新版本apk下载地址
         */

        checkVersion();
    }
    /*
    检查版本
     */

    private void checkVersion() {
        new Thread(){
            @Override
            public void run() {
                //发送请求 获取数据,参数为请求json的链接地址
                //http://192.168.253.1:8080/update.json  不是最优，不能改变ip地址
                //http://10.0.2.2:8080/update.json  仅限于模拟器访问
                try {
                    //封装URL地址
                   URL url =  new URL("http://192.168.253.1:8080/update.json");
                    //开启一个链接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置常见的额请求参数
                    //1.请求超时
                    connection.setConnectTimeout(2000);
                    //2.读取超时
                    connection.setReadTimeout(2000);
                    //3.请求方式,默认就是GET请求方式
//                    connection.setRequestMethod("GET");
                    //4.获取响应码
                    if (connection.getResponseCode()==200){
                        //5.以流的形式，将数据获取下来
                        InputStream is = connection.getInputStream();
                        //6.将流转换成字符串，工具封装类
                        StreamUtil.streamToString();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
    }


    /**
     *
     * @return
     * 返回版本号
     */
    private int getVersionCode() {
        mLocal_Versioncode = BuildConfig.VERSION_CODE;

        return mLocal_Versioncode;

    }

    /**
     * 获取版本名称
     * 在BuildConfig中
     */
    private void getVersionName() {
        String versionName = BuildConfig.VERSION_NAME;
        tv_version_name.setText("版本名称："+versionName);

    }

    /**
     * 初始化UI说明
     */
    private void initUI() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);

    }


}

package com.waynian.mobilephonesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.waynian.mobilephonesafe.BuildConfig;
import com.waynian.mobilephonesafe.R;
import com.waynian.mobilephonesafe.utils.ConstantValue;
import com.waynian.mobilephonesafe.utils.SpUtil;
import com.waynian.mobilephonesafe.utils.StreamUtil;
import com.waynian.mobilephonesafe.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {
    protected static final String TAG = "SplashActivity";
    /*
    更新新版本的状态码
     */
    protected static final int UPDATE_VERSION = 100;
    /*
    进入应用程序的状态码
     */
    private static final int ENTER_HOME = 101;
    /*
    url错误状态码
     */
    private static final int URL_ERROR = 102;
    private static final int IO_ERROR = 103;
    private static final int JSON_ERROR = 104;

    private TextView tv_version_name;
    private int mLocal_Versioncode;
    private String mVersionDes;
    private String mDownloadUrl;
    private RelativeLayout rl_root;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VERSION:
                    //弹出对话框，提示用户更新
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    //直接进入主界面
                    enterHome();
                    break;
                case URL_ERROR:
                    ToastUtil.show(SplashActivity.this, "URL异常");
                    enterHome();
                    break;
                case IO_ERROR:
                    ToastUtil.show(SplashActivity.this, "IO异常");
                    enterHome();
                    break;
                case JSON_ERROR:
                    ToastUtil.show(SplashActivity.this, "JSON异常");
                    enterHome();
                    break;
                default:
                    break;

            }
        }
    };


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

        //初始化动画
        initAnimation();
    }

    /*
    添加淡入的效果
     */
    private void initAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(3000);
        rl_root.startAnimation(alphaAnimation);
    }


    /**
     * 弹出对话框，提示用户更新
     */
    private void showUpdateDialog() {
        //对话框是依赖于Activity存在的
        Log.e(TAG, "要跳出更新界面了");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("版本更新");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载APK，downloadUrl
                downloadApk();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消，并跳转主界面
                enterHome();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
                //即使用户点击取消，也需要进入应用主界面
                dialog.dismiss();
            }
        });
        builder.show();

    }

    /*
    下载APK
     */
    private void downloadApk() {
        //APK的下载地址，APK所在路径

        //1.判断SD卡是否可用,是否挂载上
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //2.SD卡对应的路径
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+
                    File.separator+"mobliePhoneSafe.apk";
            //3.发送请求，获取APK，放到指定位置
            HttpUtils httpUtils= new HttpUtils();
            httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    //下载成功
                    File file = responseInfo.result;
                    Log.e(TAG,"下载成功");
                    installApk(file);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    //下载失败
                    Log.e(TAG,"下载失败");

                }

                //刚刚下载的方法
                @Override
                public void onStart() {
                    super.onStart();
                    Log.e(TAG,"刚刚下载");
                }

                //下载过程中的方法(下载apk的总大小，当前下载位置，是否在下载)
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    Log.e(TAG,"下载中...");
                    Log.e(TAG,"total"+total);
                    Log.e(TAG,"current"+current);


                }
            });


        }
    }

    /*
    安装APK
     */
    private void installApk(File file) {
        //调用系统安装APP
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        //文件作为数据源
//        intent.setData(Uri.fromFile(file));
        //设置安装类型
//        intent.setType("application/vnd.android.package-archive");
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
//        startActivity(intent);
        startActivityForResult(intent,0);

    }
    //开启一个Activity返回调用的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }

    private void enterHome() {
        //进入应用程序的主界面
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //开启一个新的界面后，将导航界面关闭
        finish();
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
        if (SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE,false)) {
            checkVersion();
        }else {
            //在发送消息4秒钟后，去处理当前ENTER_HOME状态0码指向的消息
            mHandler.sendEmptyMessageDelayed(ENTER_HOME,4000);
        }
    }
    /*
    检查版本
     */

    private void checkVersion() {
        new Thread() {
            @Override
            public void run() {
                //发送请求 获取数据,参数为请求json的链接地址
                //http://192.168.253.1:8080/update.json  不是最优，不能改变ip地址
                //http://10.0.2.2:8080/update.json  仅限于模拟器访问
                Message msg = Message.obtain();
                long starttime = System.currentTimeMillis();
                try {
                    //1,封装url地址
                    URL url = new URL("http://192.168.253.1:8080/update.json");
                    //2,开启一个链接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //3,设置常见请求参数(请求头)

                    //请求超时
                    connection.setConnectTimeout(2000);
                    //读取超时
                    connection.setReadTimeout(2000);

                    //请求方式,默认就是GET请求方式
//                    connection.setRequestMethod("GET");
                    //4.获取响应码
                    Log.e(TAG, String.valueOf(connection.getResponseCode()));
                    if (connection.getResponseCode() == 200) {
                        //5,以流的形式,将数据获取下来
                        InputStream is = connection.getInputStream();
                        //6,将流转换成字符串(工具类封装)
                        String json = StreamUtil.streamToString(is);
                        Log.i(TAG, json);
                        //7.json解析
                        JSONObject jsonObject = new JSONObject(json);
                        mDownloadUrl = jsonObject.getString("downLoadUrl");
                        String versionCode = jsonObject.getString("versionCode");
                        mVersionDes = jsonObject.getString("versionDes");
                        String versionName = jsonObject.getString("versionName");

                        Log.e(TAG, mDownloadUrl);
                        Log.e(TAG, versionCode);
                        Log.e(TAG, mVersionDes);
                        Log.e(TAG, versionName);

                        //比对版本号
                        if (mLocal_Versioncode < Integer.parseInt(versionCode)) {
                            //提示用户更新,弹出对话框，消息机制
                            msg.what = UPDATE_VERSION;
                        } else {
                            //进去主界面
                            msg.what = ENTER_HOME;
                        }

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what = URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what = IO_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = JSON_ERROR;
                } finally {
                    //指定睡眠时间,请求网络的时超过四秒，不做睡眠

                    long endtime = System.currentTimeMillis();
                    if ((endtime - starttime) < 4000) {
                        try {
                            Thread.sleep(4000 - (endtime - starttime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }


    /**
     * @return 返回版本号
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
        tv_version_name.setText("版本名称：" + versionName);

    }

    /**
     * 初始化UI说明
     */
    private void initUI() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);

    }


}

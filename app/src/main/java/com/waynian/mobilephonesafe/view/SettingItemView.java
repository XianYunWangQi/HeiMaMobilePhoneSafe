package com.waynian.mobilephonesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.waynian.mobilephonesafe.R;

/**
 * Created by waynian on 2016/8/9.
 */
public class SettingItemView extends RelativeLayout {
    private static final String NAMESAPCE = "http://schemas.android.com/apk/res/com.waynian.mobilephonesafe";
    private String TGA = "SettingItemView";

    private CheckBox cb_box;

    private TextView tv_des;
    private String destitle;
    private String mDestitle1;
    private String mDesoff;
    private String mDeson;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //xml转换成view
        View.inflate(context, R.layout.setting_item_view, this);

        //标题
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_des = (TextView) findViewById(R.id.tv_des);
        cb_box = (CheckBox) findViewById(R.id.cb_box);
        //获取自定义或原生操作，AttributeSet attrs 获取
        initAttrs(attrs);

        tv_title.setText(mDestitle1);

    }

    /**
     *
     * @param attrs 构造方法中维护的属性集合
     */
    private void initAttrs(AttributeSet attrs) {
//        //获取属性的总个数
//        Log.e(TGA,"getAttributeCount : " + attrs.getAttributeCount());
//        //获取属性的名称
//        for (int i =0;i<attrs.getAttributeCount();i++){
//            Log.e(TGA,"Name = "+attrs.getAttributeName(i));
//            Log.e(TGA,"Value = "+attrs.getAttributeValue(i));
//            Log.d(TGA,"*******************************");
//
//        }
        mDestitle1 = attrs.getAttributeValue(NAMESAPCE,"destitle");
        mDesoff = attrs.getAttributeValue(NAMESAPCE,"desoff");
        mDeson = attrs.getAttributeValue(NAMESAPCE,"deson");
        Log.e(TGA, mDestitle1);
        Log.e(TGA, mDesoff);
        Log.e(TGA, mDeson);
    }

    /**
     *判断是否开启的方法
     * @return当前checkbox是否为选中状态
     */
    public boolean isCheck(){
        //由checkbox决定当前条目是否开启
        return cb_box.isChecked();
    }

    /**
     * @param isCheck 是否去做开启的变量，点击过程中去改变
     */
    public void setCheck(boolean isCheck){
        cb_box.setChecked(isCheck);
        if (isCheck){
            tv_des.setText(mDeson);
        }else {
            tv_des.setText(mDesoff);
        }

    }
}


package com.waynian.mobilephonesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.waynian.mobilephonesafe.R;

/**
 * Created by waynian on 2016/8/9.
 */
public class SettingItemView extends RelativeLayout {

    private CheckBox cb_box;
    private TextView tv_des;

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
            tv_des.setText("自动更新已开始");
        }else {
            tv_des.setText("自动更新已关闭");
        }

    }
}


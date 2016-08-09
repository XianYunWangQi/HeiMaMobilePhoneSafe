package com.waynian.mobilephonesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by waynian on 2016/8/8.
 * 能够获取焦点的自定义的TextView
 */
public class FocusView extends TextView{
    //使用通过java代码创建控件
    public FocusView(Context context) {
        super(context);
    }

    //由系统调用（带属性+上下文环境构造方法）
    public FocusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //由系统调用（带属性，带上下文环境，在布局文件定义样式的构造方法）
    public FocusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //重写获取焦点的方法

    @Override
    public boolean isFocused() {
        return true;

    }
}

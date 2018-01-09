package com.dawnling.www.widget;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * github:https://github.com/KnifeStone/Hyena
 * Created by KnifeStone on 2017-7-12.
 * 小区物资补充
 */
public class MyEditText extends android.support.v7.widget.AppCompatEditText {

    private TextWatcher listener;

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        if (listener != null) {
            removeTextChangedListener(listener);
        }
        listener = watcher;
        super.addTextChangedListener(listener);
    }
}
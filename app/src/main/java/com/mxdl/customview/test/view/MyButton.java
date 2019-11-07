package com.mxdl.customview.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.mxdl.customview.util.MotionEventUtil;

/**
 * Description: <MyButton><br>
 * Author:      mxdl<br>
 * Date:        2019/10/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyButton extends androidx.appcompat.widget.AppCompatButton {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.v("MYTAG","MyButton dispatchTouchEvent start..."+ MotionEventUtil.getEventType(event));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("MYTAG","MyButton onTouchEvent start..."+ MotionEventUtil.getEventType(event));
        return super.onTouchEvent(event);
    }
}

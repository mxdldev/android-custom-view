package com.mxdl.customview.test.view1;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.mxdl.customview.R;

/**
 * Description: <MyStickLayout2><br>
 * Author:      mxdl<br>
 * Date:        2019/10/30<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 *     1.在onInterceptTouchEvent方法进行事件过滤【上下滑动，且是展开状态或者折叠状态】
 *     2.在onTouchEvent方法消费事件【拖动，回弹】
 */
public class MyStickLayout2 extends LinearLayout {

    private int mLastInterceptY;
    private int mLastInterceptX;
    private int mLastY;
    private int mLastX;
    private boolean isExpand = true;
    private int mScaledTouchSlop;
    private MyExpandableListView2 mExpandableListView;
    private LinearLayout mHeaderLayout;
    private int mOriginHeaderHeight;
    private int mCurrHeaderHeight;

    public MyStickLayout2(Context context) {
        super(context);
    }

    public MyStickLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyStickLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyStickLayout2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus){
            mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            mExpandableListView = findViewById(R.id.view_expand_listview);
            mHeaderLayout = findViewById(R.id.sticky_header);
            mOriginHeaderHeight = mHeaderLayout.getMeasuredHeight();
            mCurrHeaderHeight = mOriginHeaderHeight;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                mLastX = x;
                mLastY = y;
                mLastInterceptX = x;
                mLastInterceptY = y;
                Log.v("MYTAG","onInterceptTouchEvent ACTION_DOWN...");
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastInterceptX;
                int dy = y - mLastInterceptY;
                Log.v("MYTAG","onInterceptTouchEvent ACTION_MOVE dy:"+dy+";mScaledTouchSlop:"+mScaledTouchSlop);
                if(Math.abs(dx) > Math.abs(dy)){
                    intercept = false;
                }else if(isExpand && dy < -mScaledTouchSlop){
                    //上滑
                    intercept = true;
                    Log.v("MYTAG","上滑...");
                }else if(mExpandableListView != null && mExpandableListView.getFirstVisiblePosition() == 0 && dy > mScaledTouchSlop){
                    //下滑
                    Log.v("MYTAG","下滑...");
                    intercept = true;
                }else{
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                mLastInterceptX = 0;
                mLastInterceptY = 0;
                Log.v("MYTAG","onInterceptTouchEvent ACTION_UP...");
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - mLastY;
                int height = mCurrHeaderHeight + dy;
                setHeaderHeight(height);
                //Log.v("MYTAG","height:"+height);
                break;
            case MotionEvent.ACTION_UP:
                int deltaY = 0;
                if(mCurrHeaderHeight > mOriginHeaderHeight/2){
                    deltaY = mOriginHeaderHeight;
                    isExpand = true;
                }else{
                    deltaY = 0;
                    isExpand = false;
                }
                smoothSetHeight(deltaY);
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothSetHeight(int deltaY) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mCurrHeaderHeight, deltaY).setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setHeaderHeight((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    private void setHeaderHeight(int height) {
        if(height < 0){
            mCurrHeaderHeight = 0;
        }else if(height > mOriginHeaderHeight){
            mCurrHeaderHeight = mOriginHeaderHeight;
        }else{
            mCurrHeaderHeight = height;
        }
        if(mCurrHeaderHeight == 0){
            isExpand = false;
        }else{
            isExpand = true;
        }
        //Log.v("MYTAG","mCurrHeaderHeight:"+mCurrHeaderHeight);

        mHeaderLayout.getLayoutParams().height = mCurrHeaderHeight;
        mHeaderLayout.requestLayout();
    }
}

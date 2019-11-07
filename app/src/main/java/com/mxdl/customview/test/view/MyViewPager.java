package com.mxdl.customview.test.view;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.mxdl.customview.util.MotionEventUtil;

/**
 * Description: <MyViewPager><br>
 * Author:      mxdl<br>
 * Date:        2019/10/23<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyViewPager extends ViewGroup {
    public static final String TAG = MyViewPager.class.getSimpleName();
    private int mLastX;
    private int mLastY;
    private int mChildIndex;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mLastXIntercept;
    private int mLastYIntercept;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.v("MYTAG", "ViewPager onMeasure start...");

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.v("MYTAG","onMeasure width:"+widthSpecSize+";height:"+heightSpecSize);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        int childCount = getChildCount();
        if(childCount == 0){
           setMeasuredDimension(0,0);
        }else if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            View child = getChildAt(0);
            measureWidth = child.getMeasuredWidth() * childCount;
            measureHeight = child.getMeasuredHeight();
            //setMeasuredDimension(widthSpecSize,heightSpecSize);
            setMeasuredDimension(measureWidth,measureHeight);
        }else if(widthSpecMode == MeasureSpec.AT_MOST){
            View child = getChildAt(0);
            measureWidth = child.getMeasuredWidth() * childCount;
            measureHeight = child.getMeasuredHeight();
            setMeasuredDimension(measureWidth,heightSpecSize);
        }else if(heightMeasureSpec == MeasureSpec.AT_MOST){
            View child = getChildAt(0);
            measureHeight = child.getMeasuredHeight();
            setMeasuredDimension(widthSpecSize,measureHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.v("MYTAG", "ViewPager onLayout start...");
        int childCount = getChildCount();
        int childLeft = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            child.layout(childLeft, 0, childLeft + measuredWidth, measuredHeight);

            //Log.v(TAG, "view count:" + childCount + "|childLeft:" + childLeft + ";view width:" + measuredWidth + ";height:" + measuredHeight + "|scrollX:" + child.getScrollX() + ";scrollY:" + child.getScrollY() + "|x:" + child.getX() + ";y:" + child.getY() + "|" + ";left:" + child.getLeft() + ";top" + child.getTop() + ";right:" + child.getRight() + ";bottom:" + child.getBottom() + "|transtionX:" + child.getTranslationX() + ";transtionY:" + child.getTranslationY());
            childLeft += measuredWidth;
        }
        //Log.v(TAG, "view pager width：" + getMeasuredWidth() + ";height:" + getMeasuredHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v("MYTAG", "ViewPager dispatchTouchEvent start..." + MotionEventUtil.getEventType(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.v("MYTAG", "ViewPager onInterceptTouchEvent start..." + MotionEventUtil.getEventType(ev));
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastXIntercept;
                int dy = y - mLastYIntercept;
                if(Math.abs(dx) > Math.abs(dy)){
                    intercept = true;
                }else{
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("MYTAG", "ViewPager onTouchEvent start..." + MotionEventUtil.getEventType(event));
        Log.v("MYTAG","onTouchEvent width:"+getWidth()+";height:"+getHeight());
        //Log.v("MYTAG", "ViewPager width:" +getWidth()+";height:"+getHeight()+";measureWidth:"+getMeasuredWidth()+";measureHeigh:"+getMeasuredHeight());
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        //Log.v(TAG, "onTouchEvent x:" + x + ";y:" + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                int dy = y - mLastY;
                if(Math.abs(dx) > Math.abs(dy)){
                    scrollBy(-dx, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起的时候，首先要计算的是要滚动到哪个位置上，然后在计算滚动的距离是多少
                //1.如果滑动没有过半儿，应该还在当前位置
                //2.如果已经过半则滑动到下一个位置
                //3.如果滑动的速度快也跳到下一个位置
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();

                int scrollX = getScrollX();
                View child = getChildAt(mChildIndex);
                int childWidth = child.getMeasuredWidth();

                if (Math.abs(xVelocity) > 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + childWidth / 2) / childWidth;
                }

                mChildIndex = Math.max(0, Math.min(mChildIndex, getChildCount() - 1));
                int delx = mChildIndex * childWidth - scrollX;
                smoothScrollTo(delx, 0);
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollTo(int x, int y) {
        mScroller.startScroll(getScrollX(), getScrollY(), x, y, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}

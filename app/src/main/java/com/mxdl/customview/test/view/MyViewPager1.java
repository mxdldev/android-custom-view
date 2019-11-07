package com.mxdl.customview.test.view;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Description: <MyViewPager1><br>
 * Author:      mxdl<br>
 * Date:        2019/10/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 *     1.计算控件的大小
 *     2.确定子控件的位置
 *     3.对左滑右滑的事件进行拦截
 *     4.实现拖动效果
 *     5.实现翻页效果
 */
public class MyViewPager1 extends ViewGroup {

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mLastX;
    private int mLastY;
    private int mLastXIntercept;
    private int mLastYIntercept;
    private int mChildIndex;

    public MyViewPager1(Context context) {
        super(context);
        init();
    }

    public MyViewPager1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyViewPager1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyViewPager1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        int childCount = getChildCount();
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            measureWidth = child.getMeasuredWidth() * childCount;
            measureHeight = child.getMeasuredHeight();
            setMeasuredDimension(measureWidth, measureHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            measureWidth = child.getMeasuredWidth() * childCount;
            setMeasuredDimension(measureWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            measureHeight = child.getMeasuredHeight();
            setMeasuredDimension(widthSpecSize, measureHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childLeft = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            child.layout(childLeft, 0, childLeft + measuredWidth, measuredHeight);
            childLeft += measuredWidth;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastXIntercept;
                int dy = y - mLastYIntercept;
                intercept = false;
                if (Math.abs(dx) > Math.abs(dy)) {
                    intercept = true;
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
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                scrollBy(-dx, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                View child = getChildAt(0);
                int measuredWidth = child.getMeasuredWidth();
                if (Math.abs(xVelocity) > 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + measuredWidth/2)/measuredWidth;
                }
                mChildIndex = Math.max(0,Math.min(mChildIndex,getChildCount() -1));
                int deltaX = mChildIndex * measuredWidth - scrollX;
                smoothScrollTo(deltaX);
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollTo(int deltaX) {
        mScroller.startScroll(getScrollX(),getScrollY(),deltaX,0,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}

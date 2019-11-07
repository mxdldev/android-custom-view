package com.mxdl.customview.test.view1;

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

/**
 * Description: <MyViewPager2><br>
 * Author:      mxdl<br>
 * Date:        2019/10/30<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 *     1.在onMeasure方法设定控件的大小
 *     2.在onLayout方法确定控件的位置
 *     3.在Intercept方法拦截左滑右滑事件
 *     4.在onTouchEvent方法消费事件实现左滑优化
 */
public class MyViewPager2 extends ViewGroup {

    private Scroller mScroller;
    private int mLastInterceptX;
    private int mLastInterceptY;
    private int mLastX;
    private int mLastY;
    private VelocityTracker mVelocityTracker;
    private int mChildIndex;

    public MyViewPager2(Context context) {
        super(context);
        init();
    }

    public MyViewPager2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyViewPager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyViewPager2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            setMeasuredDimension(measuredWidth * childCount, measuredHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            int measuredWidth = child.getMeasuredWidth();
            setMeasuredDimension(measuredWidth * childCount, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            int measuredHeight = child.getMeasuredHeight();
            setMeasuredDimension(widthSpecSize, measuredHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount > 0) {
            int childLeft = 0;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                int measuredWidth = child.getMeasuredWidth();
                int measuredHeight = child.getMeasuredHeight();
                child.layout(childLeft, 0, childLeft + measuredWidth, measuredHeight);
                childLeft += measuredWidth;
            }
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
                    intercept = true;
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastInterceptX;
                int dy = y - mLastInterceptY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                mLastInterceptX = 0;
                mLastInterceptY = 0;
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastInterceptX = x;
        mLastInterceptY = y;
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
                View child = getChildAt(0);
                int measuredWidth = child.getMeasuredWidth();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                Log.v("MYTAG","xVelocity:"+xVelocity);
                if (Math.abs(xVelocity) > 2000) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + measuredWidth / 2) / measuredWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, getChildCount() - 1));
                int deltaX = mChildIndex * measuredWidth - scrollX;
                smoothScrollTo(deltaX,0);
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollTo(int dx, int dy) {
        mScroller.startScroll(getScrollX(),0,dx,dy,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}

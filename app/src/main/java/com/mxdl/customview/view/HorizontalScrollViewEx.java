package com.mxdl.customview.view;

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
 * Description: <MyHorizontalScorllView><br>
 * Author:      mxdl<br>
 * Date:        2019/10/10<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 *     要求：
 *     有n个能上下滚动的RecycleView，要求他们横向摆放，而且能够左右滑动切换
 *     思路：
 *     很明显这是一个自定义的ViewGroup，使用ViewPager很轻松能实现这个效果，
 *     我们需要自定义实现这个View，以加深对ViewPager的理解，它既不是组合View，也不是自绘View
 *     而且是一个继承View，它有很多的子View，很显然他是一个ViewGruop
 *     1.确定控件的大小，它的大小由子View的大小所决定的
 *     2.确定控件的位置，主要不是确定他自身的位置，而是确定子控件的位置横向一字排开
 *     3.过滤拦截自已所需要的事件，左滑右滑事件
 *     4.消费事件a.实现左滑和右滑,b.松手后，如果小于一半则回弹，如果大于一半则切换到下一页面
 *
 *     常见错误：
 *     1.childLeft计算错误
 *     2.mChildIndex计算错误
 *     3.对于move事件没有过滤直接拦截
 *     4.对速度检测器没有添加事件，知道获取的速度一直是0
 *     5.computeScroll()方法里面没有postinvlate
 *     6.对于正在滑动中再次进行滑动没有做优化
 *     7.Math.abs(dx) >= Math.abs(dy) 加上等于会导致0,0的情况也被拦截，上滑，下滑会翻页
 */
public class HorizontalScrollViewEx extends ViewGroup {
    public static final String TAG = HorizontalScrollViewEx.class.getSimpleName();
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mLastX;
    private int mLastY;
    private int mChildIndex;
    private int mMeasuredWidth;
    private int mLastInterceptY;
    private int mLastInterceptX;

    public HorizontalScrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSzie = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        View child = getChildAt(0);
        int childViewWidth = child.getMeasuredWidth();
        int childViewHeight = child.getMeasuredHeight();
        if (widthMeasureMode == MeasureSpec.AT_MOST && heightMeasureMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(childViewWidth * childViewWidth, childViewHeight);
        } else if (widthMeasureMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(childViewWidth * childViewWidth, heightMeasureSzie);
        } else if (heightMeasureMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthMeasureSize, childViewHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childLeft = 0;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            mMeasuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            view.layout(childLeft, 0, childLeft + mMeasuredWidth, measuredHeight);
            childLeft += mMeasuredWidth;
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
                int dx = x - mLastInterceptX;
                int dy = y - mLastInterceptY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    intercept = true;
                } else {
                    intercept = false;
                    
                }
                Log.v(TAG,"dx:"+dx+";dy:"+dy+"intercept:"+intercept);
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
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
                int dy = y - mLastY;
                if (Math.abs(dx) >= Math.abs(dy)) {
                    scrollBy(-dx, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                Log.v("MYTAG", "xVelocity:" + xVelocity);
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mMeasuredWidth / 2) / mMeasuredWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, getChildCount() - 1));
                int deltaX = mChildIndex * mMeasuredWidth - scrollX;
                mScroller.startScroll(getScrollX(), 0, deltaX, 0, 500);
                invalidate();
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.v(TAG,"computeScroll x:"+mScroller.getCurrX()+";y:"+mScroller.getCurrY()+"|x:"+getX()+";y:"+getY()+"|"+";left:"+getLeft()+";top"+getTop()+";right:"+getRight()+";bottom:"+getBottom()+"|transtionX:"+getTranslationX()+";transtionY:"+getTranslationY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}

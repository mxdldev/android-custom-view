package com.mxdl.customview.test.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

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
        measureChildren(widthMeasureSpec, heightMeasureSpec);
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

            Log.v(TAG, "view count:" + childCount + "|childLeft:" + childLeft + ";view width:" + measuredWidth + ";height:" + measuredHeight + "|scrollX:" + child.getScrollX() + ";scrollY:" + child.getScrollY() + "|x:" + child.getX() + ";y:" + child.getY() + "|" + ";left:" + child.getLeft() + ";top" + child.getTop() + ";right:" + child.getRight() + ";bottom:" + child.getBottom() + "|transtionX:" + child.getTranslationX() + ";transtionY:" + child.getTranslationY());
            childLeft += measuredWidth;
        }
        Log.v(TAG, "view pager width：" + getMeasuredWidth() + ";height:" + getMeasuredHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        boolean consume = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.v(TAG, "onTouchEvent x:" + x + ";y:" + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                consume = true;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                int dy = y - mLastY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    scrollBy(-dx, 0);
                } else {
                    consume = false;
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

                if(Math.abs(xVelocity) > 50){
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1:mChildIndex + 1;
                }else{
                    mChildIndex = (scrollX + childWidth / 2) / childWidth;
                }

                mChildIndex = Math.max(0, Math.min(mChildIndex, getChildCount() - 1));
                int delx = mChildIndex * childWidth - scrollX;
                smoothScrollTo(delx,0);
                mVelocityTracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return consume;
    }

    private void smoothScrollTo(int x,int y) {
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

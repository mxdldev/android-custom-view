package com.mxdl.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.mxdl.customview.R;

/**
 * Description: <协调式折叠布局><br>
 * Author:      mxdl<br>
 * Date:        2019/10/9<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 * 主要功能：主要用它来协调headView和contentView的滚动冲突，Header是可以展开和收缩的，content是可以滚动的ListView
 * 难点：解决滑动冲突，因为header是可以上滑和下滑，并且可以收缩，而content也是可以上下滑动的，这样情况下，
 * 我们采用外部拦截法，来拦截header所需要的事件，不需要的事件就交给content处理了，我们主要思考的问题是：哪些事件是我们需要的？
 * 难题1：Acction_move是我们所需要的，哪些action_move是我们需要的？
 * 1.当前的y坐标小于headerHeight是不需要的
 * 2.当前的x的滑动距离大于y的滑动距离是不需要的
 * 3.展开情况下，上滑是需要的
 * 4.折叠情况下，下滑是需要的
 * 5.其他是不需要的
 * 难题2：
 * 需要的的事件已经拦截下来了，接下来的事情就是要事件处理了
 * 1.如果是摁下事件我们是不要处理的
 * 2.如果是移动的事件我们根据持续的y的距离来不断的是指header的高度即可
 * 3.需要手指抬起的需要有回弹的效果，如果当前距离大于一般则展开否则收缩
 *
 * ****常见错误****
 * 1.dx和dy比较大小条件搞错
 * 2.设置高度的是极端值条件搞错【第二个判断写错了】
 * 3.onTouchEvent方法最后好返回true
 * 4.setHeaderHeight方法最后要给currHeaderHeight重新赋值
 * 5.onInterceptTouchEvent方法mLastInterceptY赋值不对导致的事件没有被拦截
 */
public class StickyLayout extends LinearLayout {
    private View headerView;
    private int mOriginHeaderHeight;
    private int mCurrHeaderHeight;
    private int mScaledTouchSlop;
    private int mLastInterceptX;
    private int mLastInterceptY;
    private int mLastX;
    private int mLastY;
    private  int mStateExpand = 0;
    private  int mStateCollapsed = 1;
    private int mState = mStateExpand;
    private GiveUpTouchEventListener mGiveUpTouchEventListener;

    public void setGiveUpTouchEventListener(GiveUpTouchEventListener giveUpTouchEventListener) {
        mGiveUpTouchEventListener = giveUpTouchEventListener;
    }

    public interface  GiveUpTouchEventListener{
        boolean giveUpTouchEvent();
    }
    public StickyLayout(Context context) {
        super(context);
    }

    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus && headerView == null){
            int headerId = getResources().getIdentifier("sticky_header", "id", getContext().getPackageName());
            headerView = findViewById(headerId);
            headerView = findViewById(R.id.sticky_header);
            if(headerView != null){
                mOriginHeaderHeight = headerView.getMeasuredHeight();
                mCurrHeaderHeight = mOriginHeaderHeight;
                mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastInterceptX = x;
                mLastInterceptY = y;
                mLastX = x;
                mLastY = y;
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastInterceptX;
                int dy = y - mLastInterceptY;
                if(y <= mCurrHeaderHeight){
                    intercept = false;
                }else if(Math.abs(dx) > Math.abs(dy)){
                    intercept = false;
                }else if(mState == mStateExpand && dy <= - mScaledTouchSlop){
                    //上滑
                    intercept = true;
                }else if(mGiveUpTouchEventListener != null && mGiveUpTouchEventListener.giveUpTouchEvent() && dy > mScaledTouchSlop){
                    //下滑
                    intercept = true;
                }else{
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastInterceptX = 0;
                mLastInterceptY = 0;
                intercept = false;
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
                int dx = x - mLastX;
                int dy = y - mLastY;
                mCurrHeaderHeight += dy;
                setHeaderHeight(mCurrHeaderHeight);
                break;
            case MotionEvent.ACTION_UP:
                int dest = 0;
                if(mCurrHeaderHeight <= mOriginHeaderHeight * 0.5){
                    dest = 0;
                    mState = mStateCollapsed;
                }else{
                    dest = mOriginHeaderHeight;
                    mState = mStateExpand;
                }
                smoothSetHeaderHeight(mCurrHeaderHeight,dest,500);
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(event);
    }

    private void smoothSetHeaderHeight(int from,int to,int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to).setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setHeaderHeight((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    private void setHeaderHeight(int height) {
        if(height <= 0){
            height = 0;
        }else if(height >= mOriginHeaderHeight){
            height = mOriginHeaderHeight;
        }
        if(height == 0 ){
            mState = mStateCollapsed;
        }else{
            mState = mStateExpand;
        }
        headerView.getLayoutParams().height = height;
        headerView.requestLayout();
    }
}

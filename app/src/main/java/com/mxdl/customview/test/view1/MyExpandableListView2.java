package com.mxdl.customview.test.view1;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.mxdl.customview.R;

/**
 * Description: <MyExpandableListView2><br>
 * Author:      mxdl<br>
 * Date:        2019/10/30<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 *     1.添加Header，并确定他的位置
 *     2.实现滚动刷新Header的标题
 *     3.实现上滑下滑跟随
 *     4.实现点击Header实现折叠
 */
public class MyExpandableListView2 extends ExpandableListView {

    private View mHeader;
    private TextView mTxtTitle;

    public MyExpandableListView2(Context context) {
        super(context);
        init();
    }

    public MyExpandableListView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyExpandableListView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mHeader == null) {
                    return;
                }
                int firstGroup = getPackedPositionGroup(getExpandableListPosition(firstVisibleItem));
                int nextGroup = getPackedPositionGroup(getExpandableListPosition(firstVisibleItem + 1));
                View child = getChildAt(1);
                if (child == null) {
                    return;
                }
                int top = child.getTop();
                int measuredHeight = mHeader.getMeasuredHeight();
                int measuredWidth = mHeader.getMeasuredWidth();

                if ((firstGroup + 1) == nextGroup) {
                    if (top < measuredHeight) {
                        int dy = measuredHeight - top;
                        mHeader.layout(0, -dy, measuredWidth, measuredHeight - dy);
                    } else {
                        mHeader.layout(0, 0, measuredWidth, measuredHeight);
                    }
                } else {
                    mHeader.layout(0, 0, measuredWidth, measuredHeight);
                }

                mTxtTitle.setText("item_sticky_group-" + firstGroup);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyExpandableListView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void addHeader() {
        mHeader = LayoutInflater.from(getContext()).inflate(R.layout.item_sticky_group, this, false);
        mTxtTitle = mHeader.findViewById(R.id.group);
        mTxtTitle.setText("item_sticky_group-" + 0);
        requestLayout();
        postInvalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeader == null) {
            return;
        }
        drawChild(canvas, mHeader, getDrawingTime());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(mHeader, widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mHeader.layout(0, 0, mHeader.getMeasuredWidth(), mHeader.getMeasuredHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                if(mHeader != null && y > mHeader.getTop() && y < mHeader.getBottom()){
                    int packedPositionGroup = getPackedPositionGroup(getExpandableListPosition(pointToPosition(x, y)));
                    if(isGroupExpanded(packedPositionGroup)){
                        collapseGroup(packedPositionGroup);
                    }else{
                        expandGroup(packedPositionGroup);
                    }
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

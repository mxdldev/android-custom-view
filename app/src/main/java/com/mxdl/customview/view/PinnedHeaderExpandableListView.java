package com.mxdl.customview.view;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxdl.customview.R;

/**
 * Description: <头部固定的ExpandableListView><br>
 * Author:      mxdl<br>
 * Date:        2019/10/9<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 *     分析：通常情况下，我们的title都会跟随网上滚动，现在我们要把头部固定在顶部，那么我们只能在头部新建一个View固定在哪个位置上
 *     然后根据滚动的情况在不断的刷新它的title就OK了，然后就是当两组滚动挨在一起的时候，自定义的title要往上滚动的，最后就是自定义view的点击事件了
 *     1.整个布局加载完毕，添加自定义头部控件
 *     2.在滚动过程中，判断firstItem属于哪个组更新组的title信息
 *     3.当两个组头滚动一起时，上面的的组头应该网上滚
 *     4.点击自定义组头的时候要响应对应的点击事件
 */
public class PinnedHeaderExpandableListView extends ExpandableListView {

    private View mPinnedHeader;
    private int mPinnedHeaderWidth;
    private int mPinnedHeaderHeight;
    private TextView mTxtHeaderTitle;

    public PinnedHeaderExpandableListView(Context context) {
        super(context);
        initView();
    }

    public PinnedHeaderExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PinnedHeaderExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PinnedHeaderExpandableListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }
    public void initView(){
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //int firstVisiblePosition = getFirstVisiblePosition();
                if(mPinnedHeader == null){
                    return;
                }
                int firstGroup = getPackedPositionGroup(getExpandableListPosition(firstVisibleItem));
                int nextPosition = firstVisibleItem + 1;
                int nextGroup = getPackedPositionGroup(getExpandableListPosition(nextPosition));
                View childView = getChildAt(1);
                if(childView == null){
                    return;
                }
                int top = childView.getTop();
                if(nextGroup == firstGroup + 1){
                    if(top <= mPinnedHeaderHeight){
                        int delta = mPinnedHeaderHeight - top;
                        mPinnedHeader.layout(0,-delta,mPinnedHeaderWidth,mPinnedHeaderHeight - delta);
                    }else{
                        mPinnedHeader.layout(0,0,mPinnedHeaderWidth,mPinnedHeaderHeight);
                    }
                }else{
                    mPinnedHeader.layout(0,0,mPinnedHeaderWidth,mPinnedHeaderHeight);
                }
                mTxtHeaderTitle.setText("item_sticky_group-"+firstGroup);
            }
        });
    }
    public void showPinnedHeaderView(){
        mPinnedHeader = LayoutInflater.from(getContext()).inflate(R.layout.item_sticky_group, null);
        mPinnedHeader.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
        mTxtHeaderTitle = mPinnedHeader.findViewById(R.id.group);
        mTxtHeaderTitle.setText("item_sticky_group-1");
        requestLayout();
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mPinnedHeader == null){
            return;
        }
        measureChild(mPinnedHeader,widthMeasureSpec,heightMeasureSpec);
        mPinnedHeaderWidth = mPinnedHeader.getMeasuredWidth();
        mPinnedHeaderHeight = mPinnedHeader.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(mPinnedHeader == null){
            return;
        }
        mPinnedHeader.layout(0,0,mPinnedHeaderWidth,mPinnedHeaderHeight);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(mPinnedHeader == null){
            return;
        }
        drawChild(canvas,mPinnedHeader,getDrawingTime());
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                if(mPinnedHeader != null && y >= mPinnedHeader.getTop() && y <= mPinnedHeader.getBottom()){
                    int position = pointToPosition(x, y);
                    int positionGroup = getPackedPositionGroup(getExpandableListPosition(position));
                    if(positionGroup != INVALID_POSITION){
                        if(isGroupExpanded(positionGroup)){
                            collapseGroup(positionGroup);
                        }else{
                            expandGroup(positionGroup);
                        }
                    }
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

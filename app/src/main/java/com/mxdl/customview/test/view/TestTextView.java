package com.mxdl.customview.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Scroller;

/**
 * Description: <TestTextView><br>
 * Author:      mxdl<br>
 * Date:        2019/10/22<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TestTextView extends androidx.appcompat.widget.AppCompatTextView{

    private Scroller mScroller;

    public TestTextView(Context context) {
        super(context);
        initView();
    }

    public TestTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TestTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public void initView(){
        mScroller = new Scroller(getContext());
    }
    public void smoothScrollTo(int x,int y){
        mScroller.startScroll(getScrollX(),getScrollY(),x,y,500);
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

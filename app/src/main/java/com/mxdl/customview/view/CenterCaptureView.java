package com.mxdl.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mxdl.customview.R;

/**
 * Description: <CenterCaptureView><br>
 * Author:      mxdl<br>
 * Date:        2019/10/12<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class CenterCaptureView extends View {
    private int mWidth;
    private Rect mScreenRect = new Rect();
    private Rect mCaptureRect = new Rect();
    private Paint mBorderPaint = new Paint();
    private Path mCapturePath = new Path();
    private Paint mAreaPaint = new Paint();
    private Drawable mAnchorDrawable;
    private int mHalfAnchorWidth;
    private int mLastX;
    private int mLastY;
    private int mHitCorner;

    public CenterCaptureView(Context context) {
        super(context);
        initView();
    }

    public CenterCaptureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CenterCaptureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CenterCaptureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void initView() {
        mWidth = (int) (getResources().getDisplayMetrics().density * 216 + 0.5f);
        mBorderPaint.setStrokeWidth(getResources().getDisplayMetrics().density * 2);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.parseColor("#1E88E5"));

        mAreaPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mAreaPaint.setColor(Color.parseColor("#1E88E5"));
        mAreaPaint.setAlpha(20);

        mAnchorDrawable = getResources().getDrawable(R.mipmap.ic_edit_fence_dragger);
        mHalfAnchorWidth = mAnchorDrawable.getIntrinsicWidth() / 2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mScreenRect.set(left, top, right, bottom);
        int captureLeft = (right - mWidth) / 2;
        int captureTop = (bottom - mWidth) / 2;
        int captureRight = captureLeft + mWidth;
        int captureBottom = captureTop + mWidth;
        mCaptureRect.set(captureLeft, captureTop, captureRight, captureBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCapturePath.reset();
        mCapturePath.addRect(new RectF(mCaptureRect), Path.Direction.CW);
        //画矩形区域
        canvas.drawRect(mCaptureRect, mAreaPaint);
        //画矩形边框
        canvas.drawPath(mCapturePath, mBorderPaint);

        //画左上锚点
        
        mAnchorDrawable.setBounds(mCaptureRect.left - mHalfAnchorWidth, mCaptureRect.top - mHalfAnchorWidth, mCaptureRect.left + mHalfAnchorWidth, mCaptureRect.top + mHalfAnchorWidth);
        mAnchorDrawable.draw(canvas);

        //画右上锚点
        mAnchorDrawable.setBounds(mCaptureRect.right - mHalfAnchorWidth, mCaptureRect.top - mHalfAnchorWidth, mCaptureRect.right + mHalfAnchorWidth, mCaptureRect.top + mHalfAnchorWidth);
        mAnchorDrawable.draw(canvas);

        //画右下锚点
        mAnchorDrawable.setBounds(mCaptureRect.right - mHalfAnchorWidth, mCaptureRect.bottom - mHalfAnchorWidth, mCaptureRect.right + mHalfAnchorWidth, mCaptureRect.bottom + mHalfAnchorWidth);
        mAnchorDrawable.draw(canvas);

        //画左下锚点
        mAnchorDrawable.setBounds(mCaptureRect.left - mHalfAnchorWidth, mCaptureRect.bottom - mHalfAnchorWidth, mCaptureRect.left + mHalfAnchorWidth, mCaptureRect.bottom + mHalfAnchorWidth);
        mAnchorDrawable.draw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mHitCorner = getHitCorner(x, y);
                //如果没有点击在锚点上，则直接返回false
                if(mHitCorner == 0){
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mHitCorner = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if(mHitCorner == 0){
                    return false;
                }
                int dx = x - mLastX;
                int dy = y - mLastY;
                if(mHitCorner == 7){
                    mCaptureRect.left += dx;
                    mCaptureRect.top += dy;
                    mCaptureRect.right -= dx;
                    mCaptureRect.bottom -= dy;
                }else if(mHitCorner == 9){
                   mCaptureRect.left -= dx;
                    mCaptureRect.top += dy;
                    mCaptureRect.right += dx;
                    mCaptureRect.bottom -= dy;
                }else if(mHitCorner == 3){
                    mCaptureRect.left -= dx;
                    mCaptureRect.top -= dy;
                    mCaptureRect.right += dx;
                    mCaptureRect.bottom += dy;
                }else if(mHitCorner == 1){
                    mCaptureRect.left += dx;
                    mCaptureRect.top -= dy;
                    mCaptureRect.right -= dx;
                    mCaptureRect.bottom += dy;
                }
                break;
        }
        mLastX = x;
        mLastY = y;
        invalidate();
        return true;
    }
    public int getHitCorner(float x, float y) {
        int result = 0;
        if (y > mCaptureRect.top - mHalfAnchorWidth && y < mCaptureRect.top + mHalfAnchorWidth) {
            if (x > mCaptureRect.left - mHalfAnchorWidth && x < mCaptureRect.left + mHalfAnchorWidth) {
                result = 7;
            } else if (x > mCaptureRect.right - mHalfAnchorWidth && x < mCaptureRect.right + mHalfAnchorWidth) {
                result = 9;
            }
        } else if (y > mCaptureRect.bottom - mHalfAnchorWidth && y < mCaptureRect.bottom + mHalfAnchorWidth) {
            if (x > mCaptureRect.left - mHalfAnchorWidth && x < mCaptureRect.left + mHalfAnchorWidth) {
                result = 1;
            } else if (x > mCaptureRect.right - mHalfAnchorWidth && x < mCaptureRect.right + mHalfAnchorWidth) {
                result = 3;
            }
        }
        return result;
    }
}

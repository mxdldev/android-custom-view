package com.mxdl.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mxdl.customview.R;

/**
 * Description: <RectCaptureView><br>
 * Author:      mxdl<br>
 * Date:        2019/10/12<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class CaptureRectView extends View {
    public static final String TAG = CaptureRectView.class.getSimpleName();
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
    private Rect reverseRect = new Rect();
    private Path reversePath = new Path();
    public CaptureRectView(Context context) {
        super(context);
        initView();
    }

    public CaptureRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CaptureRectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CaptureRectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void initView() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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
        Log.v(TAG, "onLayout");
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

        //Log.v(TAG,"onDraw left:"+mCaptureRect.left+";top:"+mCaptureRect.top+";right:"+mCaptureRect.right+";bottom:"+mCaptureRect.bottom);
        canvas.save();
        //路径重新设置
        mCapturePath.reset();
        mCapturePath.addRect(new RectF(mCaptureRect), Path.Direction.CW);
        //当前的画布与指定的路径相交
        canvas.clipPath(mCapturePath, Region.Op.INTERSECT);
        //画矩形背景选区
        canvas.drawRect(mScreenRect, mAreaPaint);
        //画矩形背景边框

        //添加了一个当前的矩形区域
        boolean reverse = false;
        if(mCaptureRect.left > mCaptureRect.right){
            reverse = true;
            reversePath.reset();
            reverseRect.left = mCaptureRect.right;
            reverseRect.right = mCaptureRect.left;

            if(mCaptureRect.top > mCaptureRect.bottom){
                reverseRect.top = mCaptureRect.bottom;
                reverseRect.bottom = mCaptureRect.top;
            }else{
                reverseRect.top = mCaptureRect.top;
                reverseRect.bottom = mCaptureRect.bottom;
            }
        }else if(mCaptureRect.top > mCaptureRect.bottom){
            reverse = true;
            reversePath.reset();
            reverseRect.top = mCaptureRect.bottom;
            reverseRect.bottom = mCaptureRect.top;

            reverseRect.left = mCaptureRect.left;
            reverseRect.right = mCaptureRect.right;
        }

        if(reverse){
            reversePath.addRect(new RectF(reverseRect), Path.Direction.CW);
            canvas.drawPath(reversePath, mBorderPaint);
            Log.v("MYTAG","========================================"+reverseRect.toString());
        } else{
            canvas.drawPath(mCapturePath, mBorderPaint);
        }
        Log.v("MYTAG",mCaptureRect.toString());
        canvas.restore();

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

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHitCorner = getHitCorner(x, y);
                if (mHitCorner == 0) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mHitCorner = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mHitCorner == 0) {
                    return false;
                }
                int dx = x - mLastX;
                int dy = y - mLastY;

                if (mHitCorner == 7) {
                    mCaptureRect.left += dx;
                    mCaptureRect.top += dy;
                } else if (mHitCorner == 9) {
                    mCaptureRect.right += dx;
                    mCaptureRect.top += dy;
                } else if (mHitCorner == 3) {
                    mCaptureRect.right += dx;
                    mCaptureRect.bottom += dy;
                } else if (mHitCorner == 1) {
                    mCaptureRect.left += dx;
                    mCaptureRect.bottom += dy;
                }

                //防止选取越界
                int screenWidth = mScreenRect.right - mScreenRect.left;
                int screenHeight = mScreenRect.bottom - mScreenRect.top;
                int maxWidth = screenWidth - mHalfAnchorWidth * 2;
                if (mCaptureRect.left <= mHalfAnchorWidth) {
                    mCaptureRect.left = mHalfAnchorWidth;
                }
                if (mCaptureRect.top <= mHalfAnchorWidth) {
                    mCaptureRect.top = mHalfAnchorWidth;
                }
                if (mCaptureRect.right >= screenWidth - mHalfAnchorWidth) {
                    mCaptureRect.right = screenWidth - mHalfAnchorWidth;
                }
                if (mCaptureRect.bottom >= screenHeight - mHalfAnchorWidth) {
                    mCaptureRect.bottom = screenHeight - mHalfAnchorWidth;
                }
                if (mCaptureRect.left >= screenWidth - mHalfAnchorWidth) {
                    mCaptureRect.left = screenWidth - mHalfAnchorWidth;
                }
                if (mCaptureRect.top >= screenHeight - mHalfAnchorWidth) {
                    mCaptureRect.top = screenHeight - mHalfAnchorWidth;
                }
                if (mCaptureRect.right <= mHalfAnchorWidth) {
                    mCaptureRect.right = mHalfAnchorWidth;
                }
                if (mCaptureRect.bottom <= mHalfAnchorWidth) {
                    mCaptureRect.bottom = mHalfAnchorWidth;
                }

                boolean square = true;
                if (Math.abs(mCaptureRect.right - mCaptureRect.left) != Math.abs(mCaptureRect.bottom - mCaptureRect.top)) {
                    square = false;
                }
                Log.v(TAG, "mHitCorner" + mHitCorner + ";dx:" + dx + ";dy:" + dy + ";width:" + (mCaptureRect.right - mCaptureRect.left) + ";height:" + (mCaptureRect.bottom - mCaptureRect.top) + ";square:" + (square ? "ok" : "------------------"));


                break;
        }

        mLastX = x;
        mLastY = y;

        invalidate();
        return true;
    }

    public int getHitCorner(float x, float y) {
        int result = 0;
        if (y > mCaptureRect.top - mHalfAnchorWidth * 2 && y < mCaptureRect.top + mHalfAnchorWidth * 2) {
            if (x > mCaptureRect.left - mHalfAnchorWidth * 2 && x < mCaptureRect.left + mHalfAnchorWidth * 2) {
                result = 7;
            } else if (x > mCaptureRect.right - mHalfAnchorWidth * 2 && x < mCaptureRect.right + mHalfAnchorWidth * 2) {
                result = 9;
            }
        } else if (y > mCaptureRect.bottom - mHalfAnchorWidth * 2 && y < mCaptureRect.bottom + mHalfAnchorWidth * 2) {
            if (x > mCaptureRect.left - mHalfAnchorWidth * 2 && x < mCaptureRect.left + mHalfAnchorWidth * 2) {
                result = 1;
            } else if (x > mCaptureRect.right - mHalfAnchorWidth * 2 && x < mCaptureRect.right + mHalfAnchorWidth * 2) {
                result = 3;
            }
        }
        return result;
    }
}

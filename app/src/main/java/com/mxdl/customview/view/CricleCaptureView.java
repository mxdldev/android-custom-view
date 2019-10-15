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
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mxdl.customview.R;

import java.util.Random;

/**
 * Description: <CenterCaptureView><br>
 * Author:      mxdl<br>
 * Date:        2019/10/12<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class CricleCaptureView extends View {
    public static final String TAG = CricleCaptureView.class.getSimpleName();
    private int mRadius;
    private int mRadius1;
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
    private int mCricleX;
    private int mCricleY;

    public CricleCaptureView(Context context) {
        super(context);
        initView();
    }

    public CricleCaptureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CricleCaptureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CricleCaptureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void initView() {
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mRadius = (int) (getResources().getDisplayMetrics().density * 216 + 0.5f) / 2;
        mRadius1 = mRadius;
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
        int captureLeft = (right - mRadius * 2) / 2;
        int captureTop = (bottom - mRadius * 2) / 2;
        int captureRight = captureLeft + mRadius * 2;
        int captureBottom = captureTop + mRadius * 2;

        mCricleX = (right - left) / 2;
        mCricleY = (bottom - top) / 2;
        mCaptureRect.set(captureLeft, captureTop, captureRight, captureBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Log.v(TAG,"onDraw left:"+mCaptureRect.left+";top:"+mCaptureRect.top+";right:"+mCaptureRect.right+";bottom:"+mCaptureRect.bottom);
        canvas.save();
        mCapturePath.reset();
        //添加圆形路径
        mCapturePath.addCircle(mCricleX, mCricleY, mRadius1, Path.Direction.CW);
        //裁切一个圆形的画布区域
        canvas.clipPath(mCapturePath, Region.Op.INTERSECT);
        //画整个背景区域
        canvas.drawRect(mScreenRect, mAreaPaint);
        //画背景区域的边框
        canvas.drawPath(mCapturePath, mBorderPaint);
        canvas.restore();

        //画8号位锚点
        mAnchorDrawable.setBounds(mCaptureRect.left + mRadius - mHalfAnchorWidth, mCaptureRect.top - mHalfAnchorWidth, mCaptureRect.left + mRadius + mHalfAnchorWidth, mCaptureRect.top + mHalfAnchorWidth);
        mAnchorDrawable.draw(canvas);

        //画6号位锚点
        mAnchorDrawable.setBounds(mCaptureRect.right - mHalfAnchorWidth, mCaptureRect.top + mRadius - mHalfAnchorWidth, mCaptureRect.right + mHalfAnchorWidth, mCaptureRect.top + mRadius + mHalfAnchorWidth);
        mAnchorDrawable.draw(canvas);

        //画2号位锚点
        mAnchorDrawable.setBounds(mCaptureRect.left + mRadius - mHalfAnchorWidth, mCaptureRect.bottom - mHalfAnchorWidth, mCaptureRect.left + mRadius + mHalfAnchorWidth, mCaptureRect.bottom + mHalfAnchorWidth);
        mAnchorDrawable.draw(canvas);

        //画4号位锚点
        mAnchorDrawable.setBounds(mCaptureRect.left - mHalfAnchorWidth, mCaptureRect.top + mRadius - mHalfAnchorWidth, mCaptureRect.left + mHalfAnchorWidth, mCaptureRect.top + mRadius + mHalfAnchorWidth);
        mAnchorDrawable.draw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHitCorner = getHitCorner(x, y);
                //Log.v(TAG, "ACTION_DOWN:mHitCorner" + mHitCorner);
                //如果没有点击在锚点上，则直接返回false
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

                if (mHitCorner == 4) {
                    mRadius -= dx;
                    mCaptureRect.left += dx;
                    mCaptureRect.top += dx;
                    mCaptureRect.right -= dx;
                    mCaptureRect.bottom -= dx;
                }
                if (mHitCorner == 8) {
                    mRadius -= dy;
                    mCaptureRect.left += dy;
                    mCaptureRect.top += dy;
                    mCaptureRect.right -= dy;
                    mCaptureRect.bottom -= dy;
                }
                if (mHitCorner == 6) {
                    mRadius += dx;
                    mCaptureRect.left -= dx;
                    mCaptureRect.top -= dx;
                    mCaptureRect.right += dx;
                    mCaptureRect.bottom += dx;
                }
                if (mHitCorner == 2) {
                    mRadius += dy;
                    mCaptureRect.left -= dy;
                    mCaptureRect.top -= dy;
                    mCaptureRect.right += dy;
                    mCaptureRect.bottom += dy;
                }

                //防止选取越界
                int screenWidth = mScreenRect.right - mScreenRect.left;
                int screenHeight = mScreenRect.bottom - mScreenRect.top;
                int maxRaduis = (screenWidth - mHalfAnchorWidth * 2) / 2;
                if (Math.abs(mRadius) > maxRaduis) {
                    if (mRadius > 0) {
                        mRadius = maxRaduis;
                    } else {
                        mRadius = -maxRaduis;
                    }
                }
                mRadius1 = Math.abs(mRadius);
                if (mCaptureRect.left <= mHalfAnchorWidth) {
                    mCaptureRect.left = mHalfAnchorWidth;
                    int maxWidth = screenWidth - mHalfAnchorWidth * 2;
                    mCaptureRect.right = mCaptureRect.left + maxWidth;
                    mCaptureRect.top = (screenHeight - maxWidth) / 2;
                    mCaptureRect.bottom = mCaptureRect.top + maxWidth;
                } else if (mCaptureRect.left >= screenWidth - mHalfAnchorWidth) {
                    mCaptureRect.left = screenWidth - mHalfAnchorWidth;
                    int maxWidth = screenWidth - mHalfAnchorWidth * 2;
                    mCaptureRect.top = mCricleY + maxWidth/2;
                    mCaptureRect.right = mHalfAnchorWidth;
                    mCaptureRect.bottom = mCricleY - maxWidth/2;
                }

                boolean square = true;
                if (Math.abs(mCaptureRect.right - mCaptureRect.left) != Math.abs(mCaptureRect.bottom - mCaptureRect.top)) {
                    square = false;
                }
                Log.v(TAG, "mHitCorner" + mHitCorner + ";mRadius:" + mRadius + ";mRadius1:" + mRadius1 + ";dx:" + dx + ";dy:" + dy + ";width:" + (mCaptureRect.right - mCaptureRect.left) + ";height:" + (mCaptureRect.bottom - mCaptureRect.top) + ";square:" + (square ? "ok" : "------------------") + ";rect：" + mCaptureRect.toString());
                break;
        }
        mLastX = x;
        mLastY = y;
        invalidate();
        return true;
    }

    public int getHitCorner(float x, float y) {
        int result = 0;
        if (y > mCaptureRect.top + mRadius - mHalfAnchorWidth * 2 && y < mCaptureRect.top + mRadius + mHalfAnchorWidth * 2) {
            if (x > mCaptureRect.left - mHalfAnchorWidth * 2 && x < mCaptureRect.left + mHalfAnchorWidth * 2) {
                result = 4;
            } else if (x > mCaptureRect.right - mHalfAnchorWidth * 2 && x < mCaptureRect.right + mHalfAnchorWidth * 2) {
                result = 6;
            }
        } else if (x > mCaptureRect.left + mRadius - mHalfAnchorWidth * 2 && x < mCaptureRect.left + mRadius + mHalfAnchorWidth * 2) {
            if (y > mCaptureRect.top - mHalfAnchorWidth * 2 && y < mCaptureRect.top + mHalfAnchorWidth * 2) {
                result = 8;
            } else if (y > mCaptureRect.bottom - mHalfAnchorWidth * 2 && y < mCaptureRect.bottom + mHalfAnchorWidth * 2) {
                result = 2;
            }
        }
        return result;
    }
}

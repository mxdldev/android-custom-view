package com.mxdl.customview.refer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mxdl.customview.R;
import com.mxdl.customview.util.PixelUtil;


@SuppressLint({"NewApi"})
public class CaptureView extends View {

  private static final String TAG = "CaptureView";
  private Paint paint1 = new Paint();
  private Paint paint2 = new Paint();
  private Rect mWholeViewRect = new Rect();
  private Rect mCaptureRect = new Rect();
  private Rect mDefaultCaptureFrame = new Rect();
  private int mClickCorner;
  private float lastX;
  private float lastY;
  private Drawable drawable1;
  private Drawable drawable2;
  private int drawable1HalfWidth;
  private int drawable1HalfHeight;
  private int drawable2HalfWidth;
  private int drawable2HalfHeight;
  private CaptureView n;

  enum ModifyMode {
    None, Move, Grow
  }

  private ModifyMode mode = ModifyMode.None;
  // 地图的底边界，用于防止滑块脱出界面
  private int bottom;

  public static final int GROW_NONE = (1 << 0); // 1
  public static final int GROW_LEFT_EDGE = (1 << 1); // 2
  public static final int GROW_RIGHT_EDGE = (1 << 2); // 4
  public static final int GROW_TOP_EDGE = (1 << 3); // 8
  public static final int GROW_BOTTOM_EDGE = (1 << 4); // 16
  public static final int MOVE = (1 << 5);

  private Point point0;
  private Point point1;
  private Point point2;
  private Point point3;

  private int validPointerId;
  private boolean isMapLoaded;

  private static final int CAPTURE_MIN_LENGTH = 100;

  public CaptureView(Context context) {
    super(context);
  }

  public CaptureView(Context context, AttributeSet paramAttributeSet, Point... points) {
    this(context, paramAttributeSet);
  }

  public CaptureView(Context context, AttributeSet paramAttributeSet) {
    super(context, paramAttributeSet);
    setLayerType(1, null);
    this.paint2.setStrokeWidth(5.0F);
    this.paint2.setStyle(Paint.Style.STROKE);
    this.paint2.setAntiAlias(true);
    this.paint2.setColor(Color.BLUE);
    this.drawable1 = context.getResources().getDrawable(R.mipmap.ic_edit_fence_dragger);
    this.drawable2 = context.getResources().getDrawable(R.mipmap.ic_edit_fence_dragger);
    this.drawable1HalfWidth = (this.drawable1.getIntrinsicWidth() / 2);
    this.drawable1HalfHeight = (this.drawable1.getIntrinsicHeight() / 2);
    this.drawable2HalfWidth = (this.drawable2.getIntrinsicWidth() / 2);
    this.drawable2HalfHeight = (this.drawable2.getIntrinsicHeight() / 2);
    setFullScreen(true);
  }

  /**
   * 通过给的屏幕坐标，绘制截取匡
   */
  public void fromLatLng(Point... points) {
    isMapLoaded = true;
    point0 = points[0];
    point1 = points[1];
    point2 = points[2];
    point3 = points[3];
    mCaptureRect.set(point0.x, point0.y, point2.x, point2.y);
    // if ( mWholeViewRect == null ) {
    // mWholeViewRect = new Rect(point0.x, point0.y, point2.x, point2.y);
    // } else {
    // mWholeViewRect.set(point0.x, point0.y, point2.x, point2.y);
    // }
    invalidate();
  }

  /**
   * 添加围栏时初始化截取匡，无需从经纬度确定范围
   */
  public void initCaptureFrame() {
    isMapLoaded = true;
    mCaptureRect.set(mDefaultCaptureFrame.left, mDefaultCaptureFrame.top,
        mDefaultCaptureFrame.right, mDefaultCaptureFrame.bottom);
    invalidate();
  }

  private void b(float paramFloat1, float paramFloat2) {
    Rect localRect = new Rect(this.mCaptureRect);
    this.mCaptureRect.offset((int) paramFloat1, (int) paramFloat2);
    this.mCaptureRect.offset(Math.max(0, this.mWholeViewRect.left - this.mCaptureRect.left),
        Math.max(0, this.mWholeViewRect.top - this.mCaptureRect.top));
    this.mCaptureRect.offset(Math.min(0, this.mWholeViewRect.right - this.mCaptureRect.right),
        Math.min(0, this.mWholeViewRect.bottom - this.mCaptureRect.bottom));
    localRect.union(this.mCaptureRect);
    localRect.inset(-100, -100);
    invalidate(localRect);
  }

  public Rect getCaptureRect() {
    return this.mCaptureRect;
  }

  /**
   * 判断点击的是哪个顶点。参考小键盘键位 1 3 7 9，若返回-1则未点击四个顶点视为地图操作
   */
  public int getHitCorner(float x, float y) {
    int result = -1;
    if (y > this.mCaptureRect.top - this.drawable1HalfHeight
        && y < this.mCaptureRect.top + this.drawable1HalfHeight) {
      if (x > this.mCaptureRect.left - this.drawable1HalfWidth
          && x < this.mCaptureRect.left + this.drawable1HalfWidth) {
        result = 7;
      } else if (x > this.mCaptureRect.right - this.drawable1HalfWidth
          && x < this.mCaptureRect.right + this.drawable1HalfWidth) {
        result = 9;
      }
    } else if (y > this.mCaptureRect.bottom - this.drawable1HalfHeight
        && y < this.mCaptureRect.bottom + this.drawable1HalfHeight) {
      if (x > this.mCaptureRect.left - this.drawable1HalfWidth
          && x < this.mCaptureRect.left + this.drawable1HalfWidth) {
        result = 1;
      } else if (x > this.mCaptureRect.right - this.drawable1HalfWidth
          && x < this.mCaptureRect.right + this.drawable1HalfWidth) {
        result = 3;
      }
    }
    return result;
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    // 因为CaptureView的父类是RelativeLayout而不是map，所以top需要减去上方搜索栏的高度
    this.mWholeViewRect.set(left, top/* - PixelUtil.dip2px(44) */, right, bottom);
    this.bottom = bottom - PixelUtil.dip2px(60);
    int i1 = right - left;
    int i2 = bottom - top;
    int i3 = 3 * Math.min(i1, i2) / 5;
    int i4 = i2 * 2 / 5;
    int i5 = (i1 - i3) / 2;
    int i6 = (i2 - i4) / 2;
    this.mCaptureRect.set(i5, i6, i3 + i5, i4 + i6);
    this.mDefaultCaptureFrame.set(i5, i6, i3 + i5, i4 + i6);
  }

  private Path localPath = new Path();

  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (!isMapLoaded) {
      return;
    }
    canvas.save();
    localPath.reset();
    localPath.addRect(new RectF(this.mCaptureRect), Path.Direction.CW);
    canvas.clipPath(localPath, Region.Op.INTERSECT);
    paint1.setStyle(Paint.Style.FILL_AND_STROKE);
    paint1.setColor(Color.BLUE);
    paint1.setAlpha(40);
    canvas.drawRect(this.mWholeViewRect, this.paint1);
    canvas.drawPath(localPath, this.paint2);
    canvas.restore();
    // if (this.o == a.c) {
    int centerX = this.mCaptureRect.left + this.mCaptureRect.width() / 2;
    int centerY = this.mCaptureRect.top + this.mCaptureRect.height() / 2;
    // 用小键盘来表示绘制的滑块位置1379四个位置，即矩形的四个顶点
    // 7号位
    this.drawable1.setBounds(this.mCaptureRect.left - this.drawable1HalfWidth,
        this.mCaptureRect.top - this.drawable1HalfHeight,
        this.mCaptureRect.left + this.drawable1HalfWidth,
        this.mCaptureRect.top + this.drawable1HalfHeight);
    this.drawable1.draw(canvas);

    // 9号位
    this.drawable1.setBounds(this.mCaptureRect.right - this.drawable1HalfWidth,
        this.mCaptureRect.top - this.drawable1HalfHeight,
        this.mCaptureRect.right + this.drawable1HalfWidth,
        this.mCaptureRect.top + this.drawable1HalfHeight);
    this.drawable1.draw(canvas);

    // 3号位
    this.drawable2.setBounds(this.mCaptureRect.right - this.drawable2HalfWidth,
        this.mCaptureRect.bottom - this.drawable2HalfHeight,
        this.mCaptureRect.right + this.drawable2HalfWidth,
        this.mCaptureRect.bottom + this.drawable2HalfHeight);
    this.drawable2.draw(canvas);

    // 1号位
    this.drawable2.setBounds(this.mCaptureRect.left - this.drawable2HalfWidth,
        this.mCaptureRect.bottom - this.drawable2HalfHeight,
        this.mCaptureRect.left + this.drawable2HalfWidth,
        this.mCaptureRect.bottom + this.drawable2HalfHeight);
    this.drawable2.draw(canvas);

    // }
  }

  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN: // ACTION_DOWN
        int mClickCorner = getHitCorner(event.getX(), event.getY());
        // Logger.e(TAG, "被点击的边缘号：" + mClickCorner);
        // 如果不是点击的裁剪框边缘，则抛给外层地图做处理
        if (mClickCorner == -1) {
          return false;
        }
        this.mClickCorner = mClickCorner;
        this.n = this;
        lastX = event.getX();
        lastY = event.getY();
        // Prevent multiple touches from interfering with crop area
        // re-sizing
        validPointerId = event.getPointerId(event.getActionIndex());
        setMode((mClickCorner == -1) ? ModifyMode.Move : ModifyMode.Grow);
        break;
      case MotionEvent.ACTION_UP: // ACTION_UP
        this.mClickCorner = 0;
        setMode(ModifyMode.None);
        break;
      case MotionEvent.ACTION_MOVE: // ACTION_MOVE
        // 获得触碰的边之后，边的位置随着触摸点的位移改变
        if (this.mClickCorner == -1) {
          return super.onTouchEvent(event);
        }
        int dx = (int) (event.getX() - lastX);
        int dy = (int) (event.getY() - lastY);

        // 确保滑块不会拖出屏幕，由于滑块可能‘交叉’，所以每个角都要判断上下左右四个边界
        if (mCaptureRect.left <= drawable1HalfWidth) {
          mCaptureRect.left = drawable1HalfWidth;
        }
        if (mCaptureRect.right <= drawable1HalfWidth) {
          mCaptureRect.right = drawable1HalfWidth;
        }
        if (mCaptureRect.left >= PixelUtil.getScreenWidth() - drawable1HalfWidth) {
          mCaptureRect.left = PixelUtil.getScreenWidth() - drawable1HalfWidth;
        }
        if (mCaptureRect.right >= PixelUtil.getScreenWidth() - drawable1HalfWidth) {
          mCaptureRect.right = PixelUtil.getScreenWidth() - drawable1HalfWidth;
        }
        if (mCaptureRect.top <= drawable1HalfHeight + PixelUtil.dip2px(44)) {
          mCaptureRect.top = drawable1HalfHeight + PixelUtil.dip2px(44);
        }
        if (mCaptureRect.bottom <= drawable1HalfHeight + PixelUtil.dip2px(44)) {
          mCaptureRect.bottom = drawable1HalfHeight + PixelUtil.dip2px(44);
        }
        if (mCaptureRect.bottom >= bottom - drawable1HalfHeight) {
          mCaptureRect.bottom = bottom - drawable1HalfHeight;
        }
        if (mCaptureRect.top >= bottom - drawable1HalfHeight) {
          mCaptureRect.top = bottom - drawable1HalfHeight;
        }

        switch (this.mClickCorner) {
          case 7: // 左上
            // if( mCaptureRect.left > mCaptureRect.right -
            // CAPTURE_MIN_LENGTH ) break;
            mCaptureRect.left += dx;
            mCaptureRect.top += dy;
            break;
          case 9: // 右上
            // if( mCaptureRect.left > mCaptureRect.right -
            // CAPTURE_MIN_LENGTH ) break;
            mCaptureRect.right += dx;
            mCaptureRect.top += dy;
            break;
          case 1: // 左下
            // if( mCaptureRect.top > mCaptureRect.bottom -
            // CAPTURE_MIN_LENGTH ) break;
            mCaptureRect.left += dx;
            mCaptureRect.bottom += dy;
            break;
          case 3: // 右下
            // if( mCaptureRect.top > mCaptureRect.bottom -
            // CAPTURE_MIN_LENGTH ) break;
            mCaptureRect.right += dx;
            mCaptureRect.bottom += dy;
            break;
        }
        lastX = event.getX();
        lastY = event.getY();
        invalidate();
        break;
    }
    return true;
  }

  public void setFullScreen(boolean paramBoolean) {
    if (paramBoolean) {
      this.paint1.setARGB(100, 50, 50, 50);
      return;
    }
    this.paint1.setARGB(255, 0, 0, 0);
  }

  private void setMode(ModifyMode mode) {
    if (mode != this.mode) {
      this.mode = mode;
      invalidate();
    }
  }

  // ****************************************疑似要废弃的方法************************************************888

  /**
   * 获取触摸的边：上-9，下-17，左-3，右-5，裁剪框内-32，裁剪框外-1
   */
  private int getEdge(float x, float y) {
    int i1 = this.mCaptureRect.left;
    int i2 = this.mCaptureRect.top;
    int i3 = this.mCaptureRect.right;
    int i4 = this.mCaptureRect.bottom;
    int i5 = 0;
    int i6 = 0;

    final float hysteresis = 20F;
    int retval = GROW_NONE;

    // verticalCheck makes sure the position is between the top and
    // the bottom edge (with some tolerance). Similar for horizCheck.
    boolean verticalCheck =
        (y >= mCaptureRect.top - hysteresis) && (y < mCaptureRect.bottom + hysteresis);
    boolean horizCheck =
        (x >= mCaptureRect.left - hysteresis) && (x < mCaptureRect.right + hysteresis);

    // Check whether the position is near some edge(s)
    if ((Math.abs(mCaptureRect.left - x) < hysteresis) && verticalCheck) {
      retval |= GROW_LEFT_EDGE;
    }
    if ((Math.abs(mCaptureRect.right - x) < hysteresis) && verticalCheck) {
      retval |= GROW_RIGHT_EDGE;
    }
    if ((Math.abs(mCaptureRect.top - y) < hysteresis) && horizCheck) {
      retval |= GROW_TOP_EDGE;
    }
    if ((Math.abs(mCaptureRect.bottom - y) < hysteresis) && horizCheck) {
      retval |= GROW_BOTTOM_EDGE;
    }

    // Not near any edge but inside the rectangle: move
    if (retval == GROW_NONE && mCaptureRect.contains((int) x, (int) y)) {
      retval = MOVE;
    }
    return retval;
  }

}

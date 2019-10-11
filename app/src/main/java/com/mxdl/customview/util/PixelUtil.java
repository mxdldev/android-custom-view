package com.mxdl.customview.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇ ▇▇▇▇▇▇▇▇▇▇▇ 负责尺寸的转换以及屏幕尺寸相关的工具类 ▇▇▇▇▇▇▇▇▇▇▇▇
 * ▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇
 */
public class PixelUtil {

  public static final int DEFUALT_WIDTH = 720;
  public static final float TEXT_Y_SCALE = 0.4f;

  private static final String TAG = "PixelUtil";

  private static int sScreenWidth;
  private static int sScreenHeight;
  private static float sScale;
  private static Context sContext;

  public static void init(Context c) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    WindowManager windowManager = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    sScreenWidth = displayMetrics.widthPixels;
    sScreenHeight = displayMetrics.heightPixels;
    sContext = c;
    sScale = sScreenWidth / (float) DEFUALT_WIDTH;
  }

  public static int getScreenWidth() {
    return sScreenWidth;
  }

  public static int getScreenHeight() {
    return sScreenHeight;
  }

  public static float getTextYOffset(int size) {
    return size * TEXT_Y_SCALE;
  }

  public static int zoom(int size) {
    return (int) (size * sScale);
  }

  /**
   * 将sp值转换为px值，保证文字大小不变
   *
   * @param spValue （DisplayMetrics类中属性scaledDensity）
   * @return
   */
  public static int sp2px(float spValue) {
    final float scale = sContext.getResources().getDisplayMetrics().density;
    return (int) (spValue * scale + 0.5f);
  }

  public static int dip2px(float dpValue) {
    final float scale = sContext.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  public static int getStatusBarHeight(Context context) {
    Class<?> c = null;
    Object obj = null;
    Field field = null;
    int x = 0, sbar = 0;
    try {
      c = Class.forName("com.android.internal.R$dimen");
      obj = c.newInstance();
      field = c.getField("status_bar_height");
      x = Integer.parseInt(field.get(obj).toString());
      sbar = context.getResources().getDimensionPixelSize(x);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return sbar;
  }

}

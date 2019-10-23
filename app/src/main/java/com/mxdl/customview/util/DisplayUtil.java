package com.mxdl.customview.util;

import android.content.Context;

/**
 * Description: <DisplayUtil><br>
 * Author:      mxdl<br>
 * Date:        2019/10/23<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class DisplayUtil {
    public static int dp2px(Context context,int dp){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(dp * density+0.5f);
    }
}

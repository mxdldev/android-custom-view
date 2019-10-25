package com.mxdl.customview.util;

import android.view.MotionEvent;

/**
 * Description: <MotionEventUtil><br>
 * Author:      mxdl<br>
 * Date:        2019/10/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MotionEventUtil {
    public static String getEventType(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "action_down";
            case MotionEvent.ACTION_MOVE:
                return "action_move";
            case MotionEvent.ACTION_UP:
                return "action_down";
        }
        return null;
    }
}

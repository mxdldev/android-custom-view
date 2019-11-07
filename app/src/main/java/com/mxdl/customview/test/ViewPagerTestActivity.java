package com.mxdl.customview.test;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxdl.customview.R;
import com.mxdl.customview.test.view.MyViewPager1;
import com.mxdl.customview.util.MotionEventUtil;

public class ViewPagerTestActivity extends AppCompatActivity {

    private MyViewPager1 mViewPager;
    int[] colors = {Color.GREEN,Color.RED,Color.BLUE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_test);
        mViewPager = findViewById(R.id.view_my_pager);
        for(int i =0; i < 3; i++){
            LinearLayout layout  = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_test_view_pager, null);
            int widthPixels = getResources().getDisplayMetrics().widthPixels;
            int heightPixels = getResources().getDisplayMetrics().heightPixels;
            Log.v("MYTAG1","widthPixels:"+widthPixels+";height:"+heightPixels);
            layout.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, heightPixels));
            TextView txtContent = layout.findViewById(R.id.txt_content);
            txtContent.setText(String.valueOf(i));
            txtContent.setBackgroundColor(colors[i]);
            mViewPager.addView(layout);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v("MYTAG","Activity dispatchTouchEvent start..."+ MotionEventUtil.getEventType(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("MYTAG","Activity onTouchEvent start..."+ MotionEventUtil.getEventType(event));
        return super.onTouchEvent(event);
    }
}

package com.mxdl.customview.test;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.mxdl.customview.R;
import com.mxdl.customview.test.view.MyViewPager;

public class ViewPagerTestActivity extends AppCompatActivity {

    private MyViewPager mViewPager;
    int[] colors = {Color.BLACK,Color.GREEN,Color.BLUE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_test);
        mViewPager = findViewById(R.id.view_my_pager);
        for(int i =0; i < 3; i++){
            TextView txtContent  = (TextView) LayoutInflater.from(this).inflate(R.layout.item_test_view_pager, mViewPager,false);
            txtContent.setText(String.valueOf(i));
            txtContent.setBackgroundColor(colors[i]);
            mViewPager.addView(txtContent);
        }
    }
}

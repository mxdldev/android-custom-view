package com.mxdl.customview.test;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mxdl.customview.R;
import com.mxdl.customview.WindowManagerActivity;
import com.mxdl.customview.test.view1.ExpandableListView2Activity;
import com.mxdl.customview.test.view1.SticyLayout2Activity;
import com.mxdl.customview.test.view1.ViewPagerTest2Activity;

public class MainTestActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        findViewById(R.id.btn_scroll_smooth).setOnClickListener(this);
        findViewById(R.id.btn_scroll_to).setOnClickListener(this);
        findViewById(R.id.btn_view_pager).setOnClickListener(this);
        findViewById(R.id.btn_coordinator).setOnClickListener(this);
        findViewById(R.id.btn_view_pager2).setOnClickListener(this);
        findViewById(R.id.btn_view_expand).setOnClickListener(this);
        findViewById(R.id.btn_view_sticy2).setOnClickListener(this);
        findViewById(R.id.btn_window_manager).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_scroll_to:
                startActivity(new Intent(this, ScrollTestActivity.class));
                break;
            case R.id.btn_scroll_smooth:
                startActivity(new Intent(this, SmoothScrollTestActivity.class));
                break;
            case R.id.btn_view_pager:
                startActivity(new Intent(this, ViewPagerTestActivity.class));
                break;
            case R.id.btn_coordinator:
                startActivity(new Intent(this,CoordinatorLayoutTestActivity.class));
                break;
            case R.id.btn_view_pager2:
                startActivity(new Intent(this, ViewPagerTest2Activity.class));
                break;
            case R.id.btn_view_expand:
                startActivity(new Intent(this, ExpandableListView2Activity.class));
                break;
            case R.id.btn_view_sticy2:
                startActivity(new Intent(this, SticyLayout2Activity.class));
                break;
            case R.id.btn_window_manager:
                startActivity(new Intent(this, WindowManagerActivity.class));
                break;
        }

    }
}

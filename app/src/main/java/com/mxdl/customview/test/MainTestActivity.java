package com.mxdl.customview.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mxdl.customview.R;

public class MainTestActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        findViewById(R.id.btn_scroll_smooth).setOnClickListener(this);
        findViewById(R.id.btn_scroll_to).setOnClickListener(this);
        findViewById(R.id.btn_view_pager).setOnClickListener(this);
        findViewById(R.id.btn_coordinator).setOnClickListener(this);
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
        }

    }
}

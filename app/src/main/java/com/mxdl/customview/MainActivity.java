package com.mxdl.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mxdl.CaptureViewActivity;
import com.mxdl.CaptureViewActivity1;

/**
 * Description: <MainActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/10/10<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtnStickyLayout;
    private Button mBtnHorizontalScrollViewEx;
    private Button mBtnHorizontalScrollView;
    private Button mBtnPinnedHeader;
    private Button mBtnCaptrueView;
    private Button mBtnCaptrueView1;
    private Button mBtnCricleCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnStickyLayout = findViewById(R.id.btn_sticky_layout);
        mBtnHorizontalScrollViewEx = findViewById(R.id.btn_horizontal_scroll_view_ex);
        mBtnHorizontalScrollView = findViewById(R.id.btn_horizontal_scroll_view);
        mBtnPinnedHeader = findViewById(R.id.btn_pinned_header);
        mBtnCaptrueView = findViewById(R.id.btn_capture_view);
        mBtnCaptrueView1 = findViewById(R.id.btn_capture_view1);
        mBtnCricleCapture = findViewById(R.id.btn_cricle_capture);

        mBtnStickyLayout.setOnClickListener(this);
        mBtnHorizontalScrollViewEx.setOnClickListener(this);
        mBtnHorizontalScrollView.setOnClickListener(this);
        mBtnPinnedHeader.setOnClickListener(this);
        mBtnCaptrueView.setOnClickListener(this);
        mBtnCaptrueView1.setOnClickListener(this);
        mBtnCricleCapture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sticky_layout:
                startActivity(new Intent(this, StickyLayoutActivity.class));
                break;
            case R.id.btn_horizontal_scroll_view_ex:
                startActivity(new Intent(this, HorizontalScrollViewExActivity.class));
                break;
            case R.id.btn_horizontal_scroll_view:
                startActivity(new Intent(this, HorizontalScrollViewActivity.class));
                break;
            case R.id.btn_pinned_header:
                startActivity(new Intent(this,PinnedHeaderActivity.class));
                break;
            case R.id.btn_capture_view:
                startActivity(new Intent(this, CaptureViewActivity.class));
                break;
            case R.id.btn_capture_view1:
                startActivity(new Intent(this, CaptureViewActivity1.class));
                break;
            case R.id.btn_cricle_capture:
                startActivity(new Intent(this, CricleCaptureViewActivity.class));
                break;
        }
    }
}

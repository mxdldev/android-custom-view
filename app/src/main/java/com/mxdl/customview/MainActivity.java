package com.mxdl.customview;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;

/**
 * Description: <MainActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/10/10<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtnStickyLayout;
    private Button mBtnScrollView;
    private Button mBtnPinnedHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnStickyLayout = findViewById(R.id.btn_sticky_layout);
        mBtnScrollView = findViewById(R.id.btn_scroll_view);
        mBtnPinnedHeader = findViewById(R.id.btn_pinned_header);

        mBtnStickyLayout.setOnClickListener(this);
        mBtnScrollView.setOnClickListener(this);
        mBtnPinnedHeader.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sticky_layout:
                startActivity(new Intent(this, StickyLayoutActivity.class));
                break;
            case R.id.btn_scroll_view:
                startActivity(new Intent(this, HorizontalScrollViewActivity.class));
                break;
            case R.id.btn_pinned_header:
                startActivity(new Intent(this,PinnedHeaderActivity.class));
                break;
        }
    }
}

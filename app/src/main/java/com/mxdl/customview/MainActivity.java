package com.mxdl.customview;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.mxdl.customview.test.MainTestActivity;

/**
 * Description: <MainActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/10/10<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnStickyLayout;
    private Button mBtnHorizontalScrollViewEx;
    private Button mBtnPinnedHeader;
    private Button mBtnCaptrueView;
    private Button mBtnCricleCapture;
    private Button mBtnRectCapture;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnStickyLayout = findViewById(R.id.btn_sticky_layout);
        mBtnHorizontalScrollViewEx = findViewById(R.id.btn_horizontal_scroll_view_ex);
        mBtnPinnedHeader = findViewById(R.id.btn_pinned_header);
        mBtnCaptrueView = findViewById(R.id.btn_capture_square);
        mBtnCricleCapture = findViewById(R.id.btn_capture_cricle);
        mBtnRectCapture = findViewById(R.id.btn_capture_rect);

        mBtnStickyLayout.setOnClickListener(this);
        mBtnHorizontalScrollViewEx.setOnClickListener(this);
        mBtnPinnedHeader.setOnClickListener(this);
        mBtnCaptrueView.setOnClickListener(this);
        mBtnCricleCapture.setOnClickListener(this);
        mBtnRectCapture.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_horizontal_scroll_view_ex:
                startActivity(new Intent(this, HorizontalScrollViewExActivity.class));
                break;
            case R.id.btn_pinned_header:
                startActivity(new Intent(this, PinnedHeaderActivity.class));
                break;
            case R.id.btn_sticky_layout:
                startActivity(new Intent(this, StickyLayoutActivity.class));
                break;
            case R.id.btn_capture_rect:
                startActivity(new Intent(this, CaptureRectViewActivity.class));
                break;
            case R.id.btn_capture_square:
                startActivity(new Intent(this, CaptureSquareViewActivity.class));
                break;
            case R.id.btn_capture_cricle:
                startActivity(new Intent(this, CaptureCricleViewActivity.class));
                break;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.test){
            startActivity(new Intent(this, MainTestActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

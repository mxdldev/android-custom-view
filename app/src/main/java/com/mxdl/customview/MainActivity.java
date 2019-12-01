package com.mxdl.customview;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mxdl.customview.bitmap.DiskLruCache;
import com.mxdl.customview.bitmap.test.TestCacheActivity;
import com.mxdl.customview.test.MainTestActivity;
import com.mxdl.customview.thread.MyAsyncTask;

<<<<<<< HEAD
=======
import java.io.File;
import java.io.IOException;

>>>>>>> 75c4ddacbcbe9ad40d193590d1449f1fd1c37ad1
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
    private Button mBtnService;
    private Button mBtnAsyncTask;
<<<<<<< HEAD
=======
    private Button mBtnCache;
>>>>>>> 75c4ddacbcbe9ad40d193590d1449f1fd1c37ad1

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
        mBtnService = findViewById(R.id.btn_service);
        mBtnAsyncTask = findViewById(R.id.btn_async_task);
<<<<<<< HEAD
=======
        mBtnCache = findViewById(R.id.btn_cache);

>>>>>>> 75c4ddacbcbe9ad40d193590d1449f1fd1c37ad1
        mBtnStickyLayout.setOnClickListener(this);
        mBtnHorizontalScrollViewEx.setOnClickListener(this);
        mBtnPinnedHeader.setOnClickListener(this);
        mBtnCaptrueView.setOnClickListener(this);
        mBtnCricleCapture.setOnClickListener(this);
        mBtnRectCapture.setOnClickListener(this);
        mBtnService.setOnClickListener(this);
        mBtnAsyncTask.setOnClickListener(this);
<<<<<<< HEAD
=======
        mBtnCache.setOnClickListener(this);

>>>>>>> 75c4ddacbcbe9ad40d193590d1449f1fd1c37ad1
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
            case R.id.btn_service:
                startActivity(new Intent(this, TestServiceActivity.class));
                break;
            case R.id.btn_async_task:
<<<<<<< HEAD
                startActivity(new Intent(this,AsyncTaskActivity.class));
=======
                startActivity(new Intent(this, TestAsyncTaskActivity.class));
                break;
            case R.id.btn_cache:
                startActivity(new Intent(this, TestCacheActivity.class));
>>>>>>> 75c4ddacbcbe9ad40d193590d1449f1fd1c37ad1
                break;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.test) {
            startActivity(new Intent(this, MainTestActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

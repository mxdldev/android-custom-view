package com.mxdl.customview;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.mxdl.customview.test.MainTestActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

        mBtnStickyLayout.setOnClickListener(this);
        mBtnHorizontalScrollViewEx.setOnClickListener(this);
        mBtnPinnedHeader.setOnClickListener(this);
        mBtnCaptrueView.setOnClickListener(this);
        mBtnCricleCapture.setOnClickListener(this);
        mBtnRectCapture.setOnClickListener(this);
        mBtnService.setOnClickListener(this);



    }

    @TargetApi(Build.VERSION_CODES.N)
    public Locale getCurrentLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return getResources().getConfiguration().locale;
        }
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

                Uri uri = Uri.parse("content://com.test.provider");
                getContentResolver().query(uri,null,null,null,null);
                getContentResolver().query(uri,null,null,null,null);
                getContentResolver().query(uri,null,null,null,null);
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

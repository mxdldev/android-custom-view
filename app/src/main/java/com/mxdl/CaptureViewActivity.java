package com.mxdl;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mxdl.customview.R;
import com.mxdl.customview.view.CaptureView;

public class CaptureViewActivity extends AppCompatActivity {

    private CaptureView mCaptureView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_view);
        mCaptureView = findViewById(R.id.view_cpature);
        mCaptureView.initCaptureFrame();
    }
}

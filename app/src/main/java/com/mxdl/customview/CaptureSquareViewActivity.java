package com.mxdl.customview;

import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.mxdl.customview.view.CaptureSquareView;

public class CaptureSquareViewActivity extends AppCompatActivity {

    private CaptureSquareView mCaptureView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_square_view);
        mCaptureView = findViewById(R.id.view_cpature);
        //mCaptureView.initCaptureFrame();
    }
}

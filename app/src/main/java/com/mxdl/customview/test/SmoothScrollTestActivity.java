package com.mxdl.customview.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mxdl.customview.R;
import com.mxdl.customview.test.view.TestTextView;

public class SmoothScrollTestActivity extends AppCompatActivity {

    private Button mBtnScroll;
    private TestTextView mTxtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smooth_scroll);
        mBtnScroll = findViewById(R.id.btn_scroll);
        mTxtContent = findViewById(R.id.txt_scroll);
        mBtnScroll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mTxtContent.smoothScrollTo(-300,-300);
            }
        });
    }
}

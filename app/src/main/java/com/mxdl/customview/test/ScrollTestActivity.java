package com.mxdl.customview.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mxdl.customview.R;
import com.mxdl.customview.test.view.TestTextView;

public class ScrollTestActivity extends AppCompatActivity {

    private Button mBtnScroll;
    private TestTextView mTxtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test);
        mBtnScroll = findViewById(R.id.btn_scroll);
        mTxtContent = findViewById(R.id.txt_scroll);
        mBtnScroll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                float density = getResources().getDisplayMetrics().density;
                //mTxtContent.scrollTo(-(int)(density * 150+0.5f),-(int)(density * 150+0.5f));
                mTxtContent.scrollBy(-(int)(density * 150+0.5f),-(int)(density * 150+0.5f));
                //mTxtContent.smoothScrollTo(-(int)(density * 150+0.5f),-(int)(density * 150+0.5f));
            }
        });
    }
}

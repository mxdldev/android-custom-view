package com.mxdl.customview.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mxdl.customview.R;

public class ScrollTestActivity extends AppCompatActivity {
    public static final String TAG = ScrollTestActivity.class.getSimpleName();
    private TextView mTxtScroll;
    private Button mBtnScroll;
    private Button mBtnShowPositon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test);
        mTxtScroll = findViewById(R.id.txt_scroll_to);
        mBtnScroll = findViewById(R.id.btn_scroll_smooth);
        mBtnShowPositon = findViewById(R.id.btn_show_position);
        mBtnScroll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mTxtScroll.scrollBy(200,200);
            }
        });

        mBtnShowPositon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.v(TAG,"scrollX:"+mTxtScroll.getScrollX()+";scrollY:"+mTxtScroll.getScrollY()+"|x:"+mTxtScroll.getX()+";y:"+mTxtScroll.getY()+"|"+";left:"+mTxtScroll.getLeft()+";top:"+mTxtScroll.getTop()+";right:"+mTxtScroll.getRight()+";bottom:"+mTxtScroll.getBottom()+"|transtionX:"+mTxtScroll.getTranslationX()+";transtionY:"+mTxtScroll.getTranslationY());
            }
        });
    }
}

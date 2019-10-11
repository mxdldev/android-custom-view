package com.mxdl.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class HorizontalScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view);
        LinearLayout layoutContent = findViewById(R.id.layout_horizontal_scroll_view_content);
        for(int i = 0; i < 3; i++){
        }
    }
}

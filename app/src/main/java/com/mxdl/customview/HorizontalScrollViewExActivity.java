package com.mxdl.customview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxdl.customview.adapter.ScrollViewAdapter;
import com.mxdl.customview.view.HorizontalScrollViewEx;

/**
 * Description: <HorizontalScrollViewExActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/10/10<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class HorizontalScrollViewExActivity extends AppCompatActivity {

    private HorizontalScrollViewEx mHorizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view_ex);
        mHorizontalScrollView = findViewById(R.id.view_scroll_view);
        for (int i = 0; i < 3; i++) {
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.item_scroll_view, mHorizontalScrollView, false);
            TextView txtTitle = viewGroup.findViewById(R.id.txt_title);
            txtTitle.setText(String.valueOf(i));
            RecyclerView recView = viewGroup.findViewById(R.id.recview_content);
            recView.setLayoutManager(new LinearLayoutManager(this));
            recView.setAdapter(new ScrollViewAdapter(this));
            mHorizontalScrollView.addView(viewGroup);
        }
    }
}

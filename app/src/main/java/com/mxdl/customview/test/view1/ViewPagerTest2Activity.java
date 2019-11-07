package com.mxdl.customview.test.view1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxdl.customview.R;
import com.mxdl.customview.adapter.ScrollViewAdapter;

public class ViewPagerTest2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_test2);
        MyViewPager2 myViewPager2 = findViewById(R.id.view_view_pager2);
        for (int i = 0; i < 3; i++) {
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.item_scroll_view, myViewPager2, false);
            TextView txtTitle = viewGroup.findViewById(R.id.txt_title);
            txtTitle.setText(String.valueOf(i));
            RecyclerView recView = viewGroup.findViewById(R.id.recview_content);
            recView.setLayoutManager(new LinearLayoutManager(this));
            recView.setAdapter(new ScrollViewAdapter(this));
            myViewPager2.addView(viewGroup);
        }
    }
}

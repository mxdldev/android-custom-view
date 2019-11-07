package com.mxdl.customview.test.view1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.mxdl.customview.R;
import com.mxdl.customview.adapter.ExpandableListViewAdapter;

public class SticyLayout2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticy_layout2);
        MyExpandableListView2 mExpandableListView = findViewById(R.id.view_expand_listview);
        mExpandableListView.setAdapter(new ExpandableListViewAdapter(this));
        for (int i = 0; i < 3; i++) {
            mExpandableListView.expandGroup(i);
        }
        mExpandableListView.addHeader();
    }
}

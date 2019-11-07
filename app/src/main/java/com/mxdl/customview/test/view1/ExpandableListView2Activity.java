package com.mxdl.customview.test.view1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.mxdl.customview.R;
import com.mxdl.customview.adapter.ExpandableListViewAdapter;

public class ExpandableListView2Activity extends AppCompatActivity {

    private MyExpandableListView2 mMyExpandableListView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view2);
        mMyExpandableListView2 = findViewById(R.id.view_expand_listview);
        mMyExpandableListView2.setAdapter(new ExpandableListViewAdapter(this));
        for(int i = 0; i < 3; i++){
            mMyExpandableListView2.expandGroup(i);
        }
        mMyExpandableListView2.addHeader();
    }
}

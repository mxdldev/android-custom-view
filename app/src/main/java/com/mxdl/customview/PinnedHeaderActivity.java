package com.mxdl.customview;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.mxdl.customview.adapter.ExpandableListViewAdapter;
import com.mxdl.customview.view.PinnedHeaderExpandableListView;
/**
 * Description: <PinnedHeaderActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/10/10<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class PinnedHeaderActivity extends AppCompatActivity {
    public static final String TAG = PinnedHeaderActivity.class.getSimpleName();
    private PinnedHeaderExpandableListView mExpandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned_header);
        mExpandableListView = findViewById(R.id.view_expand_listview);
        mExpandableListView.setAdapter(new ExpandableListViewAdapter(this));
        for(int i = 0; i < 3; i++){
            mExpandableListView.expandGroup(i);
        }
        mExpandableListView.showPinnedHeaderView();
    }
}

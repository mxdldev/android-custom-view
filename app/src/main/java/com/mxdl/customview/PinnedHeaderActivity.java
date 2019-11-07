package com.mxdl.customview;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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

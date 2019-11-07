package com.mxdl.customview;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.mxdl.customview.adapter.ExpandableListViewAdapter;
import com.mxdl.customview.view.PinnedHeaderExpandableListView;
import com.mxdl.customview.view.StickyLayout;

/**
 * Description: <StickyLayoutActivity><br>
 * Author:      mxdl<br>
 * Date:        2019/10/10<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class StickyLayoutActivity extends AppCompatActivity {

    private PinnedHeaderExpandableListView mExpandableListView;
    private StickyLayout mStickyLayout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_layout);

        mExpandableListView = findViewById(R.id.view_expand_listview);
        mExpandableListView.setAdapter(new ExpandableListViewAdapter(this));

        for (int i = 0; i < 3; i++) {
            mExpandableListView.expandGroup(i);
        }
        mExpandableListView.showPinnedHeaderView();

        mStickyLayout = findViewById(R.id.view_sticky_layout);
        mStickyLayout.setGiveUpTouchEventListener(new StickyLayout.GiveUpTouchEventListener() {
            @Override
            public boolean giveUpTouchEvent() {
                int firstVisiblePosition = mExpandableListView.getFirstVisiblePosition();
                if (firstVisiblePosition == 0) {
                    View view = mExpandableListView.getChildAt(0);
                    if (view != null && view.getTop() >= 0) {
                        return true;
                    }
                }
                return false;
            }
        });
    }


}

package com.mxdl.customview.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxdl.customview.R;

/**
 * Description: <ScrollViewAdapter><br>
 * Author:      mxdl<br>
 * Date:        2019/10/10<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class ScrollViewAdapter extends RecyclerView.Adapter<ScrollViewAdapter.MyHolder> {

    private Context mContext;

    public ScrollViewAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_scroll_title, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Log.v("MYTAG", "i:" + i);
        myHolder.txtContent.setText(String.valueOf(i));
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView txtContent;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.txt_content);
        }
    }
}


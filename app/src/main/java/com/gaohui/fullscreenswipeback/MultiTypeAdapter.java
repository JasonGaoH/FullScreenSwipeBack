package com.gaohui.fullscreenswipeback;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MultiTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TEXT = 0;
    private static final int TYPE_IMG = 1;

    private ArrayList<Object> mDataSet;
    public MultiTypeAdapter(ArrayList<Object> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public int getItemViewType(int position) {
        if(mDataSet.get(position) instanceof String) {
            return TYPE_TEXT;
        }
        return TYPE_IMG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType == TYPE_IMG) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_img_layout,viewGroup,false);
            return new SimpleImageViewHolder(view);
        }
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text_layout,viewGroup,false);
        return new SimpleTextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
       if(viewHolder instanceof SimpleTextViewHolder) {
          TextView textView = ((SimpleTextViewHolder)viewHolder).itemView.findViewById(R.id.text_view);
          textView.setText(mDataSet.get(i).toString());
       } else if(viewHolder instanceof  SimpleImageViewHolder) {
           ((SimpleImageViewHolder)viewHolder).initImages();
       }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}

package com.gaohui.fullscreenswipeback;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SimpleTextViewHolder extends RecyclerView.ViewHolder  {
    private TextView mTextView;

    public SimpleTextViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.text_view);
    }

    public void initText(String text) {
        mTextView.setText(text);
    }


}

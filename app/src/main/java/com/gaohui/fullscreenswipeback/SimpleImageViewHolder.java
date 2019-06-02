package com.gaohui.fullscreenswipeback;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class SimpleImageViewHolder extends RecyclerView.ViewHolder  {
    private HorizontalRecyclerView mImageRecyclerView;

    public SimpleImageViewHolder(@NonNull View itemView) {
        super(itemView);
        mImageRecyclerView = itemView.findViewById(R.id.image_recycler_view);
        mImageRecyclerView.setSupportFullScreenBack(true);
        new LinearSnapHelper().attachToRecyclerView(mImageRecyclerView);
    }

    public void initImages() {
        mImageRecyclerView.setLayoutManager(
                new LinearLayoutManager(mImageRecyclerView.getContext(),
                        RecyclerView.HORIZONTAL,false));
        ArrayList<Drawable> data = new ArrayList<Drawable>();
        data.add(mImageRecyclerView.getContext().getResources().getDrawable(R.drawable.image1));
        data.add(mImageRecyclerView.getContext().getResources().getDrawable(R.drawable.image2));
        data.add(mImageRecyclerView.getContext().getResources().getDrawable(R.drawable.image3));
        data.add(mImageRecyclerView.getContext().getResources().getDrawable(R.drawable.image4));
        data.add(mImageRecyclerView.getContext().getResources().getDrawable(R.drawable.image5));
        data.add(mImageRecyclerView.getContext().getResources().getDrawable(R.drawable.image6));
        ImageAdapter imageAdapter = new ImageAdapter(data);

        mImageRecyclerView.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        public void setImage(Drawable o) {
            imageView.setImageDrawable(o);
        }
    }

    class ImageAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
        private ArrayList<Drawable> mDataSet;
        public ImageAdapter(ArrayList<Drawable> dataSet) {
            mDataSet = dataSet;
        }

        @NonNull
        @Override
        public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.img_layout,viewGroup,false);
            return new SimpleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SimpleViewHolder simpleViewHolder, int i) {
            simpleViewHolder.setImage((Drawable)mDataSet.get(i));
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
    }
}

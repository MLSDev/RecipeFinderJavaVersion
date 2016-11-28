package com.mlsdev.recipefinder.view.bindingutils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mlsdev.recipefinder.R;

public final class DataBinder {

    public DataBinder() {
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }
}

package com.mlsdev.recipefinder.view.bindingutils;

import android.animation.ObjectAnimator;
import android.databinding.BindingAdapter;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mlsdev.recipefinder.R;

public final class DataBinder {

    public DataBinder() {
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        if (imageUrl.isEmpty())
            return;

        Glide.with(imageView.getContext())
                .load(imageUrl)
                .override(600, 400)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter("progressValue")
    public static void setProgressValue(ContentLoadingProgressBar progressBar, int progressValue) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, progressValue);
        objectAnimator.setDuration(350);
        objectAnimator.setStartDelay(250);
        objectAnimator.start();
    }

}

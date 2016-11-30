package com.mlsdev.recipefinder.view.bindingutils;

import android.databinding.BindingAdapter;
import android.support.annotation.AnimRes;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    @BindingAdapter("onAnimate")
    public static void animateView(View view, int visibility) {
        @AnimRes int animResId = visibility == View.VISIBLE ? R.anim.fade_in : R.anim.fade_out;
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), animResId);
        view.startAnimation(animation);
        view.setVisibility(visibility);
    }
}

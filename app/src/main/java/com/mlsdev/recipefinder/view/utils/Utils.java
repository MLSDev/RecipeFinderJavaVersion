package com.mlsdev.recipefinder.view.utils;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import java.text.DecimalFormat;

public class Utils {

    public static @ColorInt int getColor(Context context, @ColorRes int colorResId) {
        @ColorInt int color = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            color = context.getResources().getColor(colorResId, context.getTheme());
        } else {
            color = context.getResources().getColor(colorResId);
        }
        return color;
    }

    public static String formatDecimalToString(double value) {
        return new DecimalFormat("#0.00").format(value);
    }
}

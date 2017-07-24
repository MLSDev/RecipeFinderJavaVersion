package com.mlsdev.recipefinder.view.message;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.view.BaseActivity;

public class SnackbarMessage extends Message {

    public SnackbarMessage(BaseActivity activity) {
        super(activity);
    }

    public void showSnackbar(@StringRes int message) {
        if (activity != null)
            showSnackbar(activity.getString(message));
    }

    public void showSnackbar(String message) {
        showSnackbar(message, null, null);
    }

    public void showSnackbar(@StringRes int message, @StringRes int action, View.OnClickListener listener) {
        if (activity != null)
            showSnackbar(activity.getString(message), activity.getString(action), listener);
    }

    public void showSnackbar(String message, String action, View.OnClickListener listener) {
        if (activity != null) {
            String tag = activity.getString(R.string.content_tag);
            View view = activity.getWindow().getDecorView().findViewWithTag(tag);
            if (view != null) {
                Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);

                if (action != null && listener != null)
                    snackbar.setAction(action, listener);

                snackbar.show();
            }

        }
    }
}

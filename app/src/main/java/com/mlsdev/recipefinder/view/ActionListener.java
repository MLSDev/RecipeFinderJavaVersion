package com.mlsdev.recipefinder.view;

import android.support.annotation.StringRes;
import android.view.View;

public interface ActionListener {
    void onStartFilter();

    void showProgressDialog(boolean show, String message);

    void showSnackbar(@StringRes int message);

    void showSnackbar(String message);

    void showSnackbar(String message, String action, View.OnClickListener listener);

    void showSnackbar(@StringRes int message, @StringRes int action, View.OnClickListener listener);
}

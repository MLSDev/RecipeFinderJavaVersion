package com.mlsdev.recipefinder.view.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.support.annotation.StringRes;
import android.view.View;

import com.mlsdev.recipefinder.view.ActionListener;
import com.mlsdev.recipefinder.view.BaseActivity;

public class BaseFragment extends LifecycleFragment implements ActionListener {

    @Override
    public void onStartFilter() {

    }

    @Override
    public void showProgressDialog(boolean show, String message) {
        ((BaseActivity) getActivity()).showProgressDialog(show, message);
    }

    @Override
    public void showSnackbar(@StringRes int message) {
        ((BaseActivity) getActivity()).showSnackbar(message);
    }

    @Override
    public void showSnackbar(String message) {
        ((BaseActivity) getActivity()).showSnackbar(message);
    }

    @Override
    public void showSnackbar(String message, String action, View.OnClickListener listener) {
        ((BaseActivity) getActivity()).showSnackbar(message, action, listener);
    }

    @Override
    public void showSnackbar(@StringRes int message, @StringRes int action, View.OnClickListener listener) {
        ((BaseActivity) getActivity()).showSnackbar(message, action, listener);
    }
}

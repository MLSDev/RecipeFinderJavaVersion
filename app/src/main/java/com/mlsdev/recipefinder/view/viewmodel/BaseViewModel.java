package com.mlsdev.recipefinder.view.viewmodel;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.source.repository.DataRepository;

import rx.subscriptions.CompositeSubscription;

public class BaseViewModel {
    protected Context context;
    protected DataRepository repository;
    protected CompositeSubscription subscriptions;
    private ProgressDialog progressDialog;

    public BaseViewModel(Context context) {
        this.context = context;
        repository = DataRepository.getInstance(context);
        subscriptions = new CompositeSubscription();
    }

    public void onDestroy() {
        subscriptions.clear();
    }

    protected void showProgressDialog(boolean isShow, @Nullable String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context, R.style.AlertDialogAppCompat);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(message);
        }

        if (isShow)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }
}

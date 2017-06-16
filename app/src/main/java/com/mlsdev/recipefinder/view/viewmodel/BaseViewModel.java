package com.mlsdev.recipefinder.view.viewmodel;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.source.BaseObserver;
import com.mlsdev.recipefinder.data.source.repository.DataRepository;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public class BaseViewModel {
    protected Context context;
    protected DataRepository repository;
    protected CompositeDisposable subscriptions;
    private ProgressDialog progressDialog;
    protected KeyboardListener keyboardListener;

    public BaseViewModel(Context context) {
        this.context = context;
        repository = DataRepository.getInstance(context);
        subscriptions = new CompositeDisposable();
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

    public interface KeyboardListener {
        void onHideKeyboard();
    }

    protected void showError(Throwable throwable) {
        String errorTitle = context.getString(R.string.error_title_common);
        String errorMessage = context.getString(R.string.error_message_common);
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            if (httpException.code() >= BaseObserver.SERVER_ERROR) {
                errorTitle = context.getString(R.string.error_title_technical);
                errorMessage = context.getString(R.string.error_message_technical);
            }
        }

        AlertDialog alert = new AlertDialog.Builder(context, R.style.AlertDialogAppCompat)
                .setTitle(errorTitle)
                .setMessage(errorMessage)
                .create();
        alert.show();
    }
}

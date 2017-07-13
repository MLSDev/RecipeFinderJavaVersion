package com.mlsdev.recipefinder.view.viewmodel;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.source.BaseObserver;
import com.mlsdev.recipefinder.data.source.repository.DataRepository;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public class BaseViewModel extends ViewModel {
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

    public void onStop() {

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
        String errorMessage = context.getString(R.string.error_message_common);

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            if (httpException.code() >= BaseObserver.SERVER_ERROR)
                errorMessage = context.getString(R.string.error_message_technical);
        } else if (throwable instanceof IOException) {
            errorMessage = context.getString(R.string.error_message_connection);
        }

        showSnackbar(errorMessage);
    }

    protected void showSnackbar(@StringRes int message) {
        if (context != null)
            showSnackbar(context.getString(message));
    }

    protected void showSnackbar(String message) {
        showSnackbar(message, null, null);
    }

    protected void showSnackbar(@StringRes int message, @StringRes int action, View.OnClickListener listener) {
        if (context != null)
            showSnackbar(context.getString(message), context.getString(action), listener);
    }

    protected void showSnackbar(String message, String action, View.OnClickListener listener) {
        if (context != null) {
            String tag = context.getString(R.string.content_tag);
            View view = ((AppCompatActivity) context).getWindow().getDecorView().findViewWithTag(tag);
            if (view != null) {
                Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);

                if (action != null && listener != null)
                    snackbar.setAction(action, listener);

                snackbar.show();
            }

        }
    }
}

package com.mlsdev.recipefinder.view.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.RecipeApplication;
import com.mlsdev.recipefinder.data.source.BaseObserver;
import com.mlsdev.recipefinder.data.source.repository.DataRepository;
import com.mlsdev.recipefinder.view.ActionListener;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public class BaseViewModel extends ViewModel {
    protected Context context = RecipeApplication.getInstance();
    protected DataRepository repository;
    protected CompositeDisposable subscriptions;
    protected ActionListener actionListener;

    public BaseViewModel() {
        subscriptions = new CompositeDisposable();
    }

    public void onDestroy() {
        subscriptions.clear();
    }

    public void onStop() {

    }

    public void onStart() {

    }

    public void setActionListener(@NonNull ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    protected void showError(Throwable throwable) {
        actionListener.showProgressDialog(false, null);
        String errorMessage = context.getString(R.string.error_message_common);

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            if (httpException.code() >= BaseObserver.SERVER_ERROR)
                errorMessage = context.getString(R.string.error_message_technical);
        } else if (throwable instanceof IOException) {
            errorMessage = context.getString(R.string.error_message_connection);
        }

        actionListener.showSnackbar(errorMessage);
    }
}

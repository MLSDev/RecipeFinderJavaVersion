package com.mlsdev.recipefinder.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mlsdev.recipefinder.view.message.ProgressDialogMessage;
import com.mlsdev.recipefinder.view.message.SnackbarMessage;

public class BaseActivity extends AppCompatActivity implements NavigationController, ActionListener {
    private ProgressDialogMessage progressDialogMessage;
    private SnackbarMessage snackbarMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialogMessage = new ProgressDialogMessage(this);
        snackbarMessage = new SnackbarMessage(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void hideSoftKeyboard() {
        View view = getCurrentFocus();

        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
        }
    }

    public void showSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void launchActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void launchActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finishCurrentActivity() {
        finish();
    }

    @Override
    public void finishWithResult(Intent data) {
        setActivityResult(data);
        finish();
    }

    @Override
    public void setActivityResult(Intent data) {
        setResult(Activity.RESULT_OK, data);
    }

    @Override
    public void onStartFilter() {

    }

    @Override
    public void showProgressDialog(boolean show, String message) {
        progressDialogMessage.showProgressDialog(show, message);
    }

    @Override
    public void showSnackbar(@StringRes int message) {
        snackbarMessage.showSnackbar(message);
    }

    @Override
    public void showSnackbar(String message) {
        snackbarMessage.showSnackbar(message);
    }

    @Override
    public void showSnackbar(String message, String action, View.OnClickListener listener) {
        snackbarMessage.showSnackbar(message, action, listener);
    }

    @Override
    public void showSnackbar(@StringRes int message, @StringRes int action, View.OnClickListener listener) {
        snackbarMessage.showSnackbar(message, action, listener);
    }
}

package com.mlsdev.recipefinder.view.message;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.view.BaseActivity;

public class ProgressDialogMessage extends Message {
    private ProgressDialog progressDialog;

    public ProgressDialogMessage(BaseActivity activity) {
        super(activity);
    }

    public void showProgressDialog(boolean isShow, @Nullable String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity, R.style.AlertDialogAppCompat);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(message);
        }

        if (isShow)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }
}

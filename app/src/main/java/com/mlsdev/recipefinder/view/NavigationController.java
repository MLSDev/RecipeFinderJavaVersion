package com.mlsdev.recipefinder.view;

import android.content.Intent;

public interface NavigationController {

    void launchActivity(Intent intent);

    void launchActivityForResult(Intent intent, int requestCode);

    void finishCurrentActivity();

    void finishWithResult(Intent data);

    void setActivityResult(Intent data);

}

package com.mlsdev.recipefinder.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "RECIPE_FINDER";
    private ActivityMainBinding binding;
    private NavigationManager navigationManager;
    private AppBroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        broadcastReceiver = new AppBroadcastReceiver();
        initNavigation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(broadcastReceiver, new IntentFilter(AppBroadcastReceiver.SHOW_ERROR_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(broadcastReceiver);
    }

    private void initNavigation() {
        navigationManager = new NavigationManager(getSupportFragmentManager());
        binding.bnvNavigationView.setOnNavigationItemSelectedListener(navigationManager);
        navigationManager.onNavigationItemSelected(binding.bnvNavigationView.getMenu().getItem(0));
    }

    public void hideSoftKeyboard() {
        View view = getCurrentFocus();

        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void addFragmentToBackstack(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack("RecipeDetails")
                .replace(R.id.fl_content, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public class AppBroadcastReceiver extends BroadcastReceiver {
        public static final String SHOW_ERROR_ACTION = "show_error";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SHOW_ERROR_ACTION)){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogAppCompat);

                if (intent.hasExtra(Extras.ALERT_DIALOG_TITLE))
                    builder.setTitle(intent.getStringExtra(Extras.ALERT_DIALOG_TITLE));

                if (intent.hasExtra(Extras.ALERT_DIALOG_MESSAGE))
                    builder.setMessage(intent.getStringExtra(Extras.ALERT_DIALOG_MESSAGE));

                builder.setPositiveButton(android.R.string.ok, null);
                builder.create().show();
            }
        }
    }
}

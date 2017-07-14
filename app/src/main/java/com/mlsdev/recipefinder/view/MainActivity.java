package com.mlsdev.recipefinder.view;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.ActivityMainBinding;
import com.mlsdev.recipefinder.view.viewmodel.ViewModelFactory;

public class MainActivity extends BaseActivity implements LifecycleRegistryOwner {
    public static final String LOG_TAG = "RECIPE_FINDER";
    private ActivityMainBinding binding;
    private BottonNavigationItemSelectedListener bottonNavigationItemSelectedListener;
    private AppBroadcastReceiver broadcastReceiver;
    private ViewModelFactory viewModelFactory;
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);



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
        viewModelFactory = new ViewModelFactory(this);
        bottonNavigationItemSelectedListener = ViewModelProviders.of(this, viewModelFactory).get(BottonNavigationItemSelectedListener.class);
        getLifecycle().addObserver(bottonNavigationItemSelectedListener);
        bottonNavigationItemSelectedListener.setCurrentMenuItem(binding.bnvNavigationView.getMenu().getItem(0));
        bottonNavigationItemSelectedListener.setFragmentManager(getSupportFragmentManager());
        binding.bnvNavigationView.setOnNavigationItemSelectedListener(bottonNavigationItemSelectedListener);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    public class AppBroadcastReceiver extends BroadcastReceiver {
        public static final String SHOW_ERROR_ACTION = "show_error";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SHOW_ERROR_ACTION)) {
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

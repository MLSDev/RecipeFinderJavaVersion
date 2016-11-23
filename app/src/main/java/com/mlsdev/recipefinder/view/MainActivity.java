package com.mlsdev.recipefinder.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initNavigation();
    }

    private void initNavigation() {
        navigationManager = new NavigationManager(getSupportFragmentManager());
        binding.bnvNavigationView.setOnNavigationItemSelectedListener(navigationManager);
        binding.bnvNavigationView.getMenu().getItem(0).setChecked(false);
        binding.bnvNavigationView.getMenu().getItem(1).setChecked(true);
        navigationManager.onNavigationItemSelected(binding.bnvNavigationView.getMenu().getItem(1));
    }
}

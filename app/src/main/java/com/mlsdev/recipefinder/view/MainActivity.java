package com.mlsdev.recipefinder.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.ActivityMainBinding;
import com.mlsdev.recipefinder.view.enums.NavigationItem;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        navigationManager = new NavigationManager(getSupportFragmentManager());
        binding.bnvNavigationView.setOnNavigationItemSelectedListener(navigationManager);
    }
}

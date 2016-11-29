package com.mlsdev.recipefinder.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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
                .addToBackStack("RecipeDetails")
                .replace(R.id.fl_content, fragment)
                .commit();
    }
}

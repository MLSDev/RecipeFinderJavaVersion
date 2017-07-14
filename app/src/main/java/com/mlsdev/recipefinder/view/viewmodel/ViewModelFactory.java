package com.mlsdev.recipefinder.view.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.mlsdev.recipefinder.view.BottonNavigationItemSelectedListener;
import com.mlsdev.recipefinder.view.searchrecipes.SearchViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(context);
        } else if (modelClass.isAssignableFrom(BottonNavigationItemSelectedListener.class)) {
            return (T) new BottonNavigationItemSelectedListener();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}

package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

public class RecipeAnalysisViewModel extends BaseViewModel {
    private OnAddIngredientListener onAddIngredientListener;
    public final ObservableField<String> title;
    public final ObservableField<String> preparation;
    public final ObservableField<String> yield;


    public RecipeAnalysisViewModel(Context context, OnAddIngredientListener onAddIngredientListener) {
        super(context);
        this.onAddIngredientListener = onAddIngredientListener;
        title = new ObservableField<>();
        preparation = new ObservableField<>();
        yield = new ObservableField<>();
    }

    public void onAnalyzeButtonClick(View view) {

    }

    public void onAddIngredientButtonClick(View view) {
        onAddIngredientListener.onAddIngredient(title.get());
    }

    public interface OnAddIngredientListener {
        void onAddIngredient(String text);
    }

}

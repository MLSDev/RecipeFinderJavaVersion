package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.view.View;

import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

public class RecipeAnalysisViewModel extends BaseViewModel {
    public static final int ADD_INGREDIENT_REQUEST_CODE = 0;
    private Fragment fragment;
    public final ObservableField<String> title;
    public final ObservableField<String> preparation;
    public final ObservableField<String> yield;
    private AddIngredientDialogFragment dialogFragment;

    public RecipeAnalysisViewModel(Fragment fragment) {
        super(fragment.getActivity());
        this.fragment = fragment;
        title = new ObservableField<>();
        preparation = new ObservableField<>();
        yield = new ObservableField<>();
        dialogFragment = new AddIngredientDialogFragment();
    }

    public void onAnalyzeButtonClick(View view) {
        // TODO: 12/13/16 make a request to the server
    }

    /**
     * Handles the {@link android.widget.EditText}'s text changing.
     * This method is added into the {@link android.widget.EditText}'s attribute list in the layout.
     */
    public void onTitleTextChanged(CharSequence text, int start, int before, int count) {
        title.set(text.toString());
    }

    /**
     * Handles the {@link android.widget.EditText}'s text changing.
     * This method is added into the {@link android.widget.EditText}'s attribute list in the layout.
     */
    public void onPreparationTextChanged(CharSequence text, int start, int before, int count) {
        preparation.set(text.toString());
    }

    /**
     * Handles the {@link android.widget.EditText}'s text changing.
     * This method is added into the {@link android.widget.EditText}'s attribute list in the layout.
     */
    public void onYieldTextChanged(CharSequence text, int start, int before, int count) {
        yield.set(text.toString());
    }

}

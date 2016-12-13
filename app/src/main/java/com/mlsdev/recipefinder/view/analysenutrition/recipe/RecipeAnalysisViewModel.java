package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.RecipeAnalysisParams;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecipeAnalysisViewModel extends BaseViewModel {
    public static final int ADD_INGREDIENT_REQUEST_CODE = 0;
    public final ObservableField<String> title;
    public final ObservableField<String> preparation;
    public final ObservableField<String> yield;
    private List<String> ingredients = new ArrayList<>();

    public RecipeAnalysisViewModel(Context context) {
        super(context);
        title = new ObservableField<>();
        preparation = new ObservableField<>();
        yield = new ObservableField<>();
    }

    public void onAnalyzeButtonClick(View view) {
        RecipeAnalysisParams recipeAnalysisParams = new RecipeAnalysisParams();
        recipeAnalysisParams.setTitle(title.get());
        recipeAnalysisParams.setYield(yield.get());
        recipeAnalysisParams.setPrep(preparation.get());
        recipeAnalysisParams.setIngr(ingredients);

        subscriptions.clear();

        Subscription subscription = repository.getRecipeAnalysisData(recipeAnalysisParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NutritionAnalysisResult>() {
                    @Override
                    public void onCompleted() {
                        // TODO: 12/13/16 progress bar handling
                        Log.d(MainActivity.LOG_TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 12/13/16 handle errors
                        Log.e(MainActivity.LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(NutritionAnalysisResult nutritionAnalysisResult) {
                        // TODO: 12/13/16 show results
                        Log.d(MainActivity.LOG_TAG, "onNext()");
                    }
                });

        subscriptions.add(subscription);
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

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

}

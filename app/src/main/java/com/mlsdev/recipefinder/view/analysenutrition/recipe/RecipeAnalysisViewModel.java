package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.RecipeAnalysisParams;
import com.mlsdev.recipefinder.data.source.BaseObserver;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.listener.OnDataLoadedListener;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeAnalysisViewModel extends BaseViewModel implements LifecycleObserver {
    public static final int ADD_INGREDIENT_REQUEST_CODE = 0;
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> preparation = new ObservableField<>();
    public final ObservableField<String> yield = new ObservableField<>();
    private List<String> ingredients = new ArrayList<>();
    private OnAddIngredientClickListener addIngredientListener;
    private OnDataLoadedListener<List<String>> dataLoadedListener;

    public RecipeAnalysisViewModel(@NonNull Context context) {
        super(context);
    }

    public void setDataLoadedListener(OnDataLoadedListener<List<String>> dataLoadedListener) {
        this.dataLoadedListener = dataLoadedListener;
    }

    public void setAddIngredientListener(OnAddIngredientClickListener addIngredientListener) {
        this.addIngredientListener = addIngredientListener;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void start() {
        dataLoadedListener.onDataLoaded(ingredients);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        Log.d("RF", "lifecycle stop");
    }

    public void onAnalyzeButtonClick(View view) {
        if (ingredients.isEmpty()) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addIngredientListener.onAddIngredientButtonClick();
                }
            };

            showSnackbar(R.string.no_ingredients_error_message, R.string.btn_add, listener);
            return;
        }

        showProgressDialog(true, "Analysing...");

        RecipeAnalysisParams recipeAnalysisParams = new RecipeAnalysisParams();
        recipeAnalysisParams.setTitle(title.get());
        recipeAnalysisParams.setYield(yield.get());
        recipeAnalysisParams.setPrep(preparation.get());
        recipeAnalysisParams.setIngr(ingredients);

        subscriptions.clear();

        repository.getRecipeAnalysisData(recipeAnalysisParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<NutritionAnalysisResult>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull NutritionAnalysisResult nutritionAnalysisResult) {
                        showProgressDialog(false, null);
                        Log.d(MainActivity.LOG_TAG, "onNext()");
                        Intent intent = new Intent(context, RecipeAnalysisDetailsActivity.class);
                        intent.putExtra(RecipeAnalysisDetailsActivity.RECIPE_ANALYSING_RESULT_KEY, nutritionAnalysisResult);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        subscriptions.add(d);
                        showProgressDialog(false, null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showError(e);
                    }
                });

    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

}

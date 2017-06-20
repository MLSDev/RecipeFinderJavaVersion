package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.RecipeAnalysisParams;
import com.mlsdev.recipefinder.data.source.BaseObserver;
import com.mlsdev.recipefinder.view.Extras;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeAnalysisViewModel extends BaseViewModel {
    public static final int ADD_INGREDIENT_REQUEST_CODE = 0;
    public final ObservableField<String> title;
    public final ObservableField<String> preparation;
    public final ObservableField<String> yield;
    private List<String> ingredients = new ArrayList<>();

    public RecipeAnalysisViewModel(@NonNull Context context, @NonNull KeyboardListener keyboardListener) {
        super(context);
        this.keyboardListener = keyboardListener;
        title = new ObservableField<>();
        preparation = new ObservableField<>();
        yield = new ObservableField<>();
    }

    public void onAnalyzeButtonClick(View view) {
        if (ingredients.isEmpty()) {
            showErrorAlertDialog(context.getString(R.string.no_ingredients_error_title),
                    context.getString(R.string.no_ingredients_error_message));
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
                });

    }

    private void showErrorAlertDialog(String title, String message) {
        Intent showErrorIntent = new Intent(MainActivity.AppBroadcastReceiver.SHOW_ERROR_ACTION);
        showErrorIntent.putExtra(Extras.ALERT_DIALOG_TITLE, title);
        showErrorIntent.putExtra(Extras.ALERT_DIALOG_MESSAGE, message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(showErrorIntent);
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

}

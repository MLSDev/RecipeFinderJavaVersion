package com.mlsdev.recipefinder.view.analysenutrition;

import android.databinding.ObservableField;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mlsdev.recipefinder.data.entity.nutrition.IngredientAnalysisResult;
import com.mlsdev.recipefinder.data.source.remote.ParameterKeys;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IngredientAnalysisViewModel extends BaseViewModel {
    public final ObservableField<String> ingredientText;
    public final ObservableField<String> nutrientText;
    public final ObservableField<String> fatText;
    public final ObservableField<String> proteinText;
    public final ObservableField<String> carbsText;
    public final ObservableField<String> energyText;
    private AppCompatActivity activity;

    public IngredientAnalysisViewModel(AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
        ingredientText = new ObservableField<>();
        nutrientText = new ObservableField<>();
        fatText = new ObservableField<>();
        proteinText = new ObservableField<>();
        carbsText = new ObservableField<>();
        energyText = new ObservableField<>();
    }

    public void onAnalyzeButtonClick(View view) {
        ((MainActivity) activity).hideSoftKeyboard();

        loadContentProgressBarVisibility.set(View.VISIBLE);

        Map<String, String> params = new ArrayMap<>();
        params.put(ParameterKeys.INGREDIENT, ingredientText.get());
        Subscription subscription = repository.getIngredientData(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<IngredientAnalysisResult>() {
                    @Override
                    public void onCompleted() {
                        loadContentProgressBarVisibility.set(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadContentProgressBarVisibility.set(View.INVISIBLE);
                        Log.e(MainActivity.LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(IngredientAnalysisResult ingredientAnalysisResult) {
                        nutrientText.set(ingredientText.get());
                        fatText.set(ingredientAnalysisResult.getTotalNutrients().getFat().getFormattedFullText());
                        proteinText.set(ingredientAnalysisResult.getTotalNutrients().getProtein().getFormattedFullText());
                        carbsText.set(ingredientAnalysisResult.getTotalNutrients().getCarbs().getFormattedFullText());
                        energyText.set(ingredientAnalysisResult.getTotalNutrients().getEnergy().getFormattedFullText());
                    }
                });

        subscriptions.add(subscription);
    }

    public void onTextChanged(CharSequence text, int start, int before, int count) {
        ingredientText.set(text.toString());
    }
}

package com.mlsdev.recipefinder.view.analysenutrition.ingredient;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.TotalNutrients;
import com.mlsdev.recipefinder.data.source.remote.ParameterKeys;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.listener.OnIngredientAnalyzedListener;
import com.mlsdev.recipefinder.view.utils.DiagramUtils;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IngredientAnalysisViewModel extends BaseViewModel {
    private OnIngredientAnalyzedListener onIngredientAnalyzedListener;
    public final ObservableField<String> ingredientText;
    public final ObservableField<String> nutrientText;
    public final ObservableField<String> fatText;
    public final ObservableField<String> proteinText;
    public final ObservableField<String> carbsText;
    public final ObservableField<String> energyText;
    public final ObservableInt diagramVisibility;
    public final ObservableInt energyLabelVisibility;
    public final ObservableInt fatLabelVisibility;
    public final ObservableInt carbsLabelVisibility;
    public final ObservableInt proteinLabelVisibility;
    public final ObservableInt analysisResultsWrapperVisibility;
    private AppCompatActivity activity;

    public IngredientAnalysisViewModel(@NonNull AppCompatActivity activity, @NonNull OnIngredientAnalyzedListener listener) {
        super(activity);
        this.activity = activity;
        onIngredientAnalyzedListener = listener;
        ingredientText = new ObservableField<>();
        nutrientText = new ObservableField<>();
        fatText = new ObservableField<>();
        proteinText = new ObservableField<>();
        carbsText = new ObservableField<>();
        energyText = new ObservableField<>();
        diagramVisibility = new ObservableInt(View.GONE);
        energyLabelVisibility = new ObservableInt(View.GONE);
        fatLabelVisibility = new ObservableInt(View.GONE);
        carbsLabelVisibility = new ObservableInt(View.GONE);
        proteinLabelVisibility = new ObservableInt(View.GONE);
        analysisResultsWrapperVisibility = new ObservableInt(View.INVISIBLE);
    }

    public void onAnalyzeButtonClick(View view) {
        ((MainActivity) activity).hideSoftKeyboard();

        showProgressDialog(true, "Analysing...");

        Map<String, String> params = new ArrayMap<>();
        params.put(ParameterKeys.INGREDIENT, ingredientText.get());
        Subscription subscription = repository.getIngredientData(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NutritionAnalysisResult>() {
                    @Override
                    public void onCompleted() {
                        showProgressDialog(false, null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showProgressDialog(false, null);
                        Log.e(MainActivity.LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(NutritionAnalysisResult ingredientAnalysisResult) {
                        TotalNutrients totalNutrients = ingredientAnalysisResult.getTotalNutrients();
                        analysisResultsWrapperVisibility.set(View.VISIBLE);

                        nutrientText.set(ingredientText.get());
                        fatText.set(totalNutrients.getFat() != null
                                ? ingredientAnalysisResult.getTotalNutrients().getFat().getFormattedFullText() : "");

                        proteinText.set(totalNutrients.getProtein() != null
                                ? ingredientAnalysisResult.getTotalNutrients().getProtein().getFormattedFullText() : "");

                        carbsText.set(totalNutrients.getCarbs() != null
                                ? ingredientAnalysisResult.getTotalNutrients().getCarbs().getFormattedFullText() : "");

                        energyText.set(totalNutrients.getEnergy() != null
                                ? ingredientAnalysisResult.getTotalNutrients().getEnergy().getFormattedFullText() : "");

                        carbsLabelVisibility.set(carbsText.get().isEmpty() ? View.GONE : View.VISIBLE);
                        proteinLabelVisibility.set(proteinText.get().isEmpty() ? View.GONE : View.VISIBLE);
                        fatLabelVisibility.set(fatText.get().isEmpty() ? View.GONE : View.VISIBLE);
                        energyLabelVisibility.set(energyText.get().isEmpty() ? View.GONE : View.VISIBLE);


                        prepareDiagramData(totalNutrients);
                    }
                });

        subscriptions.add(subscription);
    }

    public void onTextChanged(CharSequence text, int start, int before, int count) {
        ingredientText.set(text.toString());
    }

    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

        if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
            onAnalyzeButtonClick(null);
            ((MainActivity) activity).hideSoftKeyboard();
            return true;
        }

        return false;
    }

    private void prepareDiagramData(TotalNutrients nutrients) {

        ArrayList<PieEntry> entries = DiagramUtils.preparePieEntries(nutrients);
        diagramVisibility.set(entries.isEmpty() ? View.GONE : View.VISIBLE);
        PieDataSet pieDataSet = DiagramUtils.createPieDataSet(context, entries, "Nutrients", null);
        PieData pieData = DiagramUtils.createPieData(context, pieDataSet);

        onIngredientAnalyzedListener.onIngredientAnalyzed(pieData);
        diagramVisibility.set(View.VISIBLE);
    }
}

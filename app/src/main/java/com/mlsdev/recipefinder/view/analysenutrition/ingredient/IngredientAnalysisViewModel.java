package com.mlsdev.recipefinder.view.analysenutrition.ingredient;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.TotalNutrients;
import com.mlsdev.recipefinder.data.source.remote.ParameterKeys;
import com.mlsdev.recipefinder.view.MainActivity;
import com.mlsdev.recipefinder.view.listener.OnIngredientAnalyzedListener;
import com.mlsdev.recipefinder.view.utils.DiagramUtils;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IngredientAnalysisViewModel extends BaseViewModel {
    private OnIngredientAnalyzedListener onIngredientAnalyzedListener;
    public final ObservableField<String> ingredientText = new ObservableField<>("");
    public final ObservableField<String> nutrientText = new ObservableField<>("");
    public final ObservableField<String> fatText = new ObservableField<>("");
    public final ObservableField<String> proteinText = new ObservableField<>("");
    public final ObservableField<String> carbsText = new ObservableField<>("");
    public final ObservableField<String> energyText = new ObservableField<>("");
    public final ObservableInt diagramVisibility = new ObservableInt(View.GONE);
    public final ObservableInt energyLabelVisibility = new ObservableInt(View.GONE);
    public final ObservableInt fatLabelVisibility = new ObservableInt(View.GONE);
    public final ObservableInt carbsLabelVisibility = new ObservableInt(View.GONE);
    public final ObservableInt proteinLabelVisibility = new ObservableInt(View.GONE);
    public final ObservableInt analysisResultsWrapperVisibility = new ObservableInt(View.INVISIBLE);
    public final ObservableBoolean ingredientTextFocused = new ObservableBoolean(false);
    private AppCompatActivity activity;

    public IngredientAnalysisViewModel(@NonNull AppCompatActivity activity, @NonNull OnIngredientAnalyzedListener listener) {
        super(activity);
        this.activity = activity;
        onIngredientAnalyzedListener = listener;
    }

    public void onAnalyzeButtonClick(View view) {
        ((MainActivity) activity).hideSoftKeyboard();

        if (ingredientText.get().isEmpty()) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ingredientTextFocused.set(true);
                    ((MainActivity) activity).showSoftKeyboard();
                }
            };

            showSnackbar(R.string.error_empty_ingredient_field, R.string.btn_fill_in, listener);
            return;
        }

        showProgressDialog(true, "Analysing...");
        subscriptions.clear();

        Map<String, String> params = new ArrayMap<>();
        params.put(ParameterKeys.INGREDIENT, ingredientText.get());
        repository.getIngredientData(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<NutritionAnalysisResult>() {

                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        showProgressDialog(false, null);
                        subscriptions.add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull NutritionAnalysisResult result) {
                        TotalNutrients totalNutrients = result.getTotalNutrients();
                        analysisResultsWrapperVisibility.set(View.VISIBLE);

                        nutrientText.set(ingredientText.get());
                        fatText.set(totalNutrients.getFat() != null
                                ? result.getTotalNutrients().getFat().getFormattedFullText() : "");

                        proteinText.set(totalNutrients.getProtein() != null
                                ? result.getTotalNutrients().getProtein().getFormattedFullText() : "");

                        carbsText.set(totalNutrients.getCarbs() != null
                                ? result.getTotalNutrients().getCarbs().getFormattedFullText() : "");

                        energyText.set(totalNutrients.getEnergy() != null
                                ? result.getTotalNutrients().getEnergy().getFormattedFullText() : "");

                        carbsLabelVisibility.set(carbsText.get().isEmpty() ? View.GONE : View.VISIBLE);
                        proteinLabelVisibility.set(proteinText.get().isEmpty() ? View.GONE : View.VISIBLE);
                        fatLabelVisibility.set(fatText.get().isEmpty() ? View.GONE : View.VISIBLE);
                        energyLabelVisibility.set(energyText.get().isEmpty() ? View.GONE : View.VISIBLE);

                        prepareDiagramData(totalNutrients);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showProgressDialog(false, null);
                        showError(e);
                    }

                });

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

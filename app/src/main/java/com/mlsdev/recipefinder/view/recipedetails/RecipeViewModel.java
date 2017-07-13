package com.mlsdev.recipefinder.view.recipedetails;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.view.View;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.recipe.Ingredient;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.BaseObserver;
import com.mlsdev.recipefinder.view.Extras;
import com.mlsdev.recipefinder.view.utils.Utils;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.Collection;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class RecipeViewModel extends BaseViewModel {
    private Recipe recipe;
    public final ObservableField<String> recipeTitle = new ObservableField<>();
    public final ObservableField<String> recipeImageUrl = new ObservableField<>("");
    public final ObservableField<String> recipeHealthLabels = new ObservableField<>();
    public final ObservableField<String> recipeDietLabels = new ObservableField<>();
    public final ObservableField<String> recipeIngredients = new ObservableField<>();
    public final ObservableBoolean favoriteImageStateChecked = new ObservableBoolean(false);
    public final ObservableInt proteinProgressValue = new ObservableInt(80);
    public final ObservableInt carbsProgressValue = new ObservableInt(15);
    public final ObservableInt fatProgressValue = new ObservableInt(43);

    public RecipeViewModel(Context context, Bundle recipeData) {
        super(context);
        subscriptions = new CompositeDisposable();

        if (recipeData != null && recipeData.containsKey(Extras.DATA)) {
            recipe = (Recipe) recipeData.getSerializable(Extras.DATA);
            if (recipe != null) {
                recipeTitle.set(recipe.getLabel());
                recipeImageUrl.set(!recipeData.containsKey(Extras.IMAGE_DATA) ? recipe.getImage() : "");
                recipeHealthLabels.set(getLabelsAsString(recipe.getHealthLabels()));
                recipeDietLabels.set(getLabelsAsString(recipe.getDietLabels()));
                recipeIngredients.set(getIngredientsAsString(recipe.getIngredients()));

                setUpNutrients();
            }
        }
    }

    private void setUpNutrients() {
        double totalWeight = recipe.getTotalWeight();

        double proteinValue = recipe.getTotalNutrients().getProtein() != null
                ? recipe.getTotalNutrients().getProtein().getQuantity() : 0d;

        double carbsValue = recipe.getTotalNutrients().getCarbs() != null
                ? recipe.getTotalNutrients().getCarbs().getQuantity() : 0d;

        double fatValue = recipe.getTotalNutrients().getFat() != null
                ? recipe.getTotalNutrients().getFat().getQuantity() : 0d;

        proteinProgressValue.set(Utils.getPersents(totalWeight, proteinValue));
        carbsProgressValue.set(Utils.getPersents(totalWeight, carbsValue));
        fatProgressValue.set(Utils.getPersents(totalWeight, fatValue));
    }

    public void onStart() {
        checkIsTheRecipeInFavorites();
    }

    private String getIngredientsAsString(Collection<Ingredient> ingredients) {
        String ingredientsAsString = "";

        for (Ingredient ingredient : ingredients) {
            if (!ingredientsAsString.isEmpty())
                ingredientsAsString = ingredientsAsString.concat(",\n");

            ingredientsAsString = ingredientsAsString.concat(
                    context.getString(
                            R.string.ingredient_item,
                            ingredient.getText(),
                            Utils.formatDecimalToString(ingredient.getWeight()))
            );
        }

        return ingredientsAsString;
    }

    private String getLabelsAsString(List<String> labels) {
        String labelsAsString = "";

        for (String label : labels) {
            if (!labelsAsString.isEmpty())
                labelsAsString = labelsAsString.concat(", ");

            labelsAsString = labelsAsString.concat(label);
        }

        return labelsAsString;
    }

    public void onFavoriteButtonClick(View view) {

        if (favoriteImageStateChecked.get()) {
            repository.removeFromFavorites(recipe)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            favoriteImageStateChecked.set(false);
                        }
                    });
        } else {
            repository.addToFavorites(recipe)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            favoriteImageStateChecked.set(true);
                        }
                    });
        }
    }

    private void checkIsTheRecipeInFavorites() {
        repository.isInFavorites(recipe)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean exist) {
                        favoriteImageStateChecked.set(exist);
                    }
                });
    }
}

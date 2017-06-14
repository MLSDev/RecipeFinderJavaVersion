package com.mlsdev.recipefinder.data.source.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.RecipeAnalysisParams;
import com.mlsdev.recipefinder.data.entity.recipe.Hit;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.entity.recipe.SearchResult;
import com.mlsdev.recipefinder.data.source.DataSource;
import com.mlsdev.recipefinder.data.source.local.LocalDataSource;
import com.mlsdev.recipefinder.data.source.remote.ParameterKeys;
import com.mlsdev.recipefinder.data.source.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class DataRepository {
    private final int offset = 10;
    private int from = 0;
    private int to = offset;
    private boolean more = true;

    private static DataRepository instance;
    private DataSource localDataSource;

    private List<Recipe> cachedRecipes;
    private boolean cacheIsDirty = false;

    private DataRepository(Context context) {
        localDataSource = new LocalDataSource(context);
        cachedRecipes = new ArrayList<>();
    }

    public static DataRepository getInstance(Context context) {
        if (instance == null)
            instance = new DataRepository(context);

        return instance;
    }

    public void setCacheIsDirty() {
        cacheIsDirty = true;
    }

    public Single<List<Recipe>> searchRecipes(Map<String, String> params) {
        if (!cacheIsDirty) {
            return Single.just(cachedRecipes);
        }

        more = true;
        cachedRecipes.clear();

        params.put(ParameterKeys.FROM, String.valueOf(0));
        params.put(ParameterKeys.TO, String.valueOf(offset));

        return getRecipes(params);
    }

    public Single<List<Recipe>> loadMore(Map<String, String> params) {

        if (!more)
            return Single.amb(new ArrayList<SingleSource<? extends List<Recipe>>>());

        params.put(ParameterKeys.FROM, String.valueOf(from));
        params.put(ParameterKeys.TO, String.valueOf(to));

        return getRecipes(params);
    }

    @NonNull
    private Single<List<Recipe>> getRecipes(Map<String, String> params) {
        return RemoteDataSource.getInstance().searchRecipes(params)
                .map(new Function<SearchResult, List<Recipe>>() {
                    @Override
                    public List<Recipe> apply(@io.reactivex.annotations.NonNull SearchResult searchResult) throws Exception {
                        from = searchResult.getTo();
                        to = from + offset;
                        more = searchResult.isMore();

                        List<Recipe> recipes = new ArrayList<>();
                        for (Hit hit : searchResult.getHits())
                            recipes.add(hit.getRecipe());

                        cachedRecipes.addAll(recipes);

                        return recipes;
                    }
                })
                .doOnSuccess(new Consumer<List<Recipe>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<Recipe> recipes) throws Exception {
                        cacheIsDirty = false;
                    }
                });
    }

    public Single<List<Recipe>> getFavoriteRecipes() {
        return localDataSource.getFavorites();
    }

    public Single<Boolean> addToFavorites(Recipe favoriteRecipe) {
        return localDataSource.addToFavorites(favoriteRecipe);
    }

    public Single<Boolean> removeFromFavorites(Recipe removedRecipe) {
        return localDataSource.removeFromFavorites(removedRecipe);
    }

    public Single<Boolean> isInFavorites(Recipe recipe) {
        return localDataSource.isInFavorites(recipe);
    }

    public Single<NutritionAnalysisResult> getIngredientData(final Map<String, String> params) {
        return RemoteDataSource.getInstance().getIngredientData(params)
                .flatMap(new Function<NutritionAnalysisResult, SingleSource<? extends NutritionAnalysisResult>>() {
                    @Override
                    public SingleSource<? extends NutritionAnalysisResult> apply(@io.reactivex.annotations.NonNull NutritionAnalysisResult analysisResult) throws Exception {
                        if (analysisResult == null) {
                            return RemoteDataSource.getInstance().getIngredientData(params)
                                    .doOnSuccess(new Consumer<NutritionAnalysisResult>() {
                                        @Override
                                        public void accept(@io.reactivex.annotations.NonNull NutritionAnalysisResult analysisResult) throws Exception {
                                            localDataSource.addAnalyzingResult(analysisResult, params);
                                        }
                                    });
                        } else {
                            return Single.just(analysisResult);
                        }
                    }
                });
    }

    public Single<NutritionAnalysisResult> getRecipeAnalysisData(final RecipeAnalysisParams params) {
        return RemoteDataSource.getInstance().getRecipeAnalysingResult(params);
    }

}

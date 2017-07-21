package com.mlsdev.recipefinder.view.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.TransitionInflater;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.databinding.RecipeListItemBinding;
import com.mlsdev.recipefinder.view.Extras;
import com.mlsdev.recipefinder.view.fragments.TabFragment;
import com.mlsdev.recipefinder.view.listener.OnDataLoadedListener;
import com.mlsdev.recipefinder.view.recipedetails.RecipeDetailsFragment;
import com.mlsdev.recipefinder.view.searchrecipes.RecipeListAdapter;
import com.mlsdev.recipefinder.view.utils.UtilsUI;

import java.util.List;

import javax.inject.Inject;

public class RecipeListFragment extends TabFragment implements RecipeListAdapter.OnItemClickListener,
        RecipeListAdapter.OnLastItemShownListener, OnDataLoadedListener<List<Recipe>> {
    protected RecipeListAdapter recipeListAdapter;
    protected RecyclerView recipeRecyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    UtilsUI utilsUI;

    @Override
    public void onItemClicked(Recipe recipe, RecipeListItemBinding itemBinding) {
        Bundle recipeData = new Bundle();
        Fragment fragment = new RecipeDetailsFragment();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementReturnTransition(TransitionInflater.from(getActivity())
                    .inflateTransition(R.transition.change_image_transform));
            setExitTransition(new Fade());

            fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity())
                    .inflateTransition(R.transition.change_image_transform));
            fragment.setEnterTransition(new Fade());
        }

        recipeData.putSerializable(Extras.DATA, recipe);

        if (itemBinding.ivRecipeImage.getDrawable() != null) {
            Bitmap bitmapImage = itemBinding.ivRecipeImage.getDrawable() instanceof GlideBitmapDrawable ?
                    ((GlideBitmapDrawable) itemBinding.ivRecipeImage.getDrawable()).getBitmap() :
                    ((BitmapDrawable) itemBinding.ivRecipeImage.getDrawable()).getBitmap();
            recipeData.putParcelable(Extras.IMAGE_DATA, bitmapImage);
        }

        recipeData.putString(Extras.IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(itemBinding.ivRecipeImage));

        fragment.setArguments(recipeData);

        getFragmentManager()
                .beginTransaction()
                .addToBackStack("RecipeDetails")
                .replace(R.id.fl_content, fragment)
                .addSharedElement(itemBinding.ivRecipeImage, ViewCompat.getTransitionName(itemBinding.ivRecipeImage))
                .commit();
    }

    @Override
    public void onLastItemShown() {

    }

    protected void initRecyclerView(RecyclerView recyclerView) {
        int columns = getActivity().getResources().getConfiguration().orientation;

        if (recipeListAdapter == null)
            recipeListAdapter = new RecipeListAdapter(this, this);

        recipeRecyclerView = recyclerView;
        recipeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columns, GridLayoutManager.VERTICAL, false));
        recipeRecyclerView.setHasFixedSize(true);
        recipeRecyclerView.setAdapter(recipeListAdapter);
    }

    protected void initSwipeRefreshLayout(SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout = refreshLayout;
        swipeRefreshLayout.setOnRefreshListener(listener);
        swipeRefreshLayout.setColorSchemeColors(
                utilsUI.getColor(R.color.colorPrimaryDark),
                utilsUI.getColor(R.color.colorPrimary),
                utilsUI.getColor(R.color.colorAccent)
        );
    }

    @Override
    public void onDataLoaded(List<Recipe> recipes) {
        recipeListAdapter.setData(recipes);
        stopRefreshing();
    }

    @Override
    public void onMoreDataLoaded(List<Recipe> moreRecipes) {
        recipeListAdapter.setMoreData(moreRecipes);
        stopRefreshing();
    }

    protected void stopRefreshing() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }
}

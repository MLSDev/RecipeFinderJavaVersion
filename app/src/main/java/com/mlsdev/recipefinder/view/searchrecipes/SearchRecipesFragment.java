package com.mlsdev.recipefinder.view.searchrecipes;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.databinding.FragmentSearchRecipesBinding;
import com.mlsdev.recipefinder.databinding.RecipeListItemBinding;
import com.mlsdev.recipefinder.view.fragment.RecipeListFragment;
import com.mlsdev.recipefinder.view.recipedetails.RecipeDetailsActivity;
import com.mlsdev.recipefinder.view.recipedetails.RecipeDetailsFragment;
import com.mlsdev.recipefinder.view.recipedetails.RecipeViewModel;

public class SearchRecipesFragment extends RecipeListFragment implements RecipeListAdapter.OnLastItemShownListener,
        SwipeRefreshLayout.OnRefreshListener {
    private FragmentSearchRecipesBinding binding;
    private SearchViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_recipes, container, false);

        if (viewModel == null)
            viewModel = new SearchViewModel(this, this);

        binding.setViewModel(viewModel);
        swipeRefreshLayout = binding.swipeToRefreshView;
        initRecyclerView(binding.rvRecipeList);
        initSwipeRefreshLayout(binding.swipeToRefreshView, this);
        return binding.getRoot();
    }

    @Override
    public void scrollToTop() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) binding.appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null)
            behavior.onNestedFling(binding.clSearchRecipes, binding.appbar, null, 0, -1000, true);

        binding.rvRecipeList.smoothScrollToPosition(0);
    }

    @Override
    public void onItemClicked(Recipe recipe, RecipeListItemBinding itemBinding) {
        Bundle recipeData = new Bundle();
        recipeData.putSerializable(RecipeViewModel.RECIPE_DATA_KEY, recipe);
        recipeData.putParcelable("image", ((GlideBitmapDrawable) itemBinding.ivRecipeImage.getDrawable()).getBitmap());
        Fragment fragment = new RecipeDetailsFragment();

        ViewCompat.setTransitionName(itemBinding.ivRecipeImage, "sharedImage");
        ViewCompat.setTransitionName(itemBinding.tvRecipeTitle, "sharedText");
        Pair<View, String> pair1 = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            pair1 = Pair.create((View) itemBinding.ivRecipeImage, itemBinding.ivRecipeImage.getTransitionName());
            Pair<View, String> pair2 = Pair.create((View) itemBinding.tvRecipeTitle, itemBinding.tvRecipeTitle.getTransitionName());
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pair1, pair2);
            Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
            intent.putExtras(recipeData);
            startActivity(intent, options.toBundle());
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setSharedElementReturnTransition(TransitionInflater.from(getActivity())
//                    .inflateTransition(R.transition.change_image_transform));
//            setExitTransition(TransitionInflater.from(getActivity())
//                    .inflateTransition(android.R.transition.fade));
//
//            fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity())
//                    .inflateTransition(R.transition.change_image_transform));
//            fragment.setEnterTransition(TransitionInflater.from(getActivity())
//                    .inflateTransition(android.R.transition.fade));
//        }
//
//        recipeData.putSerializable(RecipeViewModel.RECIPE_DATA_KEY, recipe);
//        recipeData.putParcelable("image", ((GlideBitmapDrawable) itemBinding.ivRecipeImage.getDrawable()).getBitmap());
//
//        fragment.setArguments(recipeData);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            itemBinding.ivRecipeImage.setTransitionName("sharedImage");
//        }
//
//        getFragmentManager()
//                .beginTransaction()
//                .addToBackStack("RecipeDetails")
//                .replace(R.id.fl_content, fragment)
//                .addSharedElement(itemBinding.ivRecipeImage, "sharedImage")
//                .commit();
    }

    @Override
    public void onDestroy() {
        viewModel.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLastItemShown() {
        viewModel.loadMoreRecipes();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SearchViewModel.FILTER_REQUEST_CODE && resultCode == Activity.RESULT_OK)
            viewModel.onApplyFilterOptions(data.getExtras());
    }

    @Override
    public void onRefresh() {
        viewModel.refresh();
    }
}

package com.mlsdev.recipefinder.view.searchrecipes;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.databinding.ProgressViewBinding;
import com.mlsdev.recipefinder.databinding.RecipeListItemBinding;
import com.mlsdev.recipefinder.view.analysenutrition.adapter.BaseViewHolder;
import com.mlsdev.recipefinder.view.analysenutrition.adapter.ProgressViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private OnLastItemShownListener onLastItemShownListener;
    private OnItemClickListener onItemClickListener;
    private static final int ITEM = 0;
    private static final int PROGRESS_VIEW = 1;
    private boolean isLoadMoreItems = true;
    private List<Recipe> recipes;

    public RecipeListAdapter(@NonNull OnLastItemShownListener onLastItemShownListener,
                             @NonNull OnItemClickListener onItemClickListener) {
        this.onLastItemShownListener = onLastItemShownListener;
        this.onItemClickListener = onItemClickListener;
        recipes = new ArrayList<>();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM) {
            RecipeListItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.recipe_list_item,
                    parent,
                    false
            );

            return new RecipeViewHolder(binding);
        } else {
            ProgressViewBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.progress_view,
                    parent,
                    false
            );

            return new ProgressViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindViewModel();

        // if 1 item before the end of the list
        if ((position == getItemCount() - 1) && isLoadMoreItems)
            onLastItemShownListener.onLastItemShown();
    }

    @Override
    public int getItemCount() {
        int itemCount = recipes.size();
        itemCount = (itemCount % 10 == 0) && (itemCount > 0) && isLoadMoreItems ? itemCount + PROGRESS_VIEW : itemCount;
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        return (!recipes.isEmpty()) && (position < recipes.size()) ? ITEM : PROGRESS_VIEW;
    }

    public void setData(List<Recipe> recipes) {
        isLoadMoreItems = true;
        this.recipes.clear();
        this.recipes.addAll(recipes);
        notifyDataSetChanged();
    }

    public void setMoreData(List<Recipe> moreRecipes) {
        int insertPosition = recipes.size();
        isLoadMoreItems = !moreRecipes.isEmpty();

        if (moreRecipes.isEmpty()) {
            notifyItemRemoved(getItemCount() - 1);
        } else {
            recipes.addAll(moreRecipes);
            notifyItemRangeInserted(insertPosition, moreRecipes.size());
        }
    }

    public class RecipeViewHolder extends BaseViewHolder implements View.OnClickListener {
        final RecipeListItemBinding binding;

        public RecipeViewHolder(RecipeListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClicked(binding.getViewModel().getRecipe(), binding);
        }

        @Override
        public void bindViewModel() {
            Recipe recipe = recipes.get(getAdapterPosition());

            if (binding.getViewModel() == null)
                binding.setViewModel(new RecipeListItemViewModel(recipe));
            else
                binding.getViewModel().setRecipe(recipe);

            binding.getRoot().setOnClickListener(this);
            ViewCompat.setTransitionName(binding.ivRecipeImage, binding.getRoot().getContext()
                    .getString(R.string.shared_image_prefix, String.valueOf(getAdapterPosition())));
        }
    }

    public interface OnLastItemShownListener {
        void onLastItemShown();
    }

    public interface OnItemClickListener {
        void onItemClicked(Recipe recipe, RecipeListItemBinding binding);
    }

}

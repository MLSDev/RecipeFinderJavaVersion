package com.mlsdev.recipefinder.view.searchrecipes;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.databinding.RecipeListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private OnLastItemShownListener onLastItemShownListener;
    private OnItemClickListener onItemClickListener;
    private List<Recipe> recipes;

    public RecipeListAdapter(@NonNull OnLastItemShownListener onLastItemShownListener,
                             @NonNull OnItemClickListener onItemClickListener) {
        this.onLastItemShownListener = onLastItemShownListener;
        this.onItemClickListener = onItemClickListener;
        recipes = new ArrayList<>();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecipeListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.recipe_list_item,
                parent,
                false
        );
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bindRecipe(recipes.get(position));
        if (position == getItemCount() - 1)
            onLastItemShownListener.onLastItemShown();
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setData(List<Recipe> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);
        notifyDataSetChanged();
    }

    public void setMoreData(List<Recipe> moreRecipes) {
        recipes.addAll(moreRecipes);
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final RecipeListItemBinding binding;

        public RecipeViewHolder(RecipeListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindRecipe(Recipe recipe) {
            if (binding.getViewModel() == null)
                binding.setViewModel(new RecipeListItemViewModel(recipe));
            else
                binding.getViewModel().setRecipe(recipe);

            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClicked(binding.getViewModel().getRecipe(), binding);
        }
    }

    public interface OnLastItemShownListener {
        void onLastItemShown();
    }

    public interface OnItemClickListener {
        void onItemClicked(Recipe recipe, RecipeListItemBinding binding);
    }

}

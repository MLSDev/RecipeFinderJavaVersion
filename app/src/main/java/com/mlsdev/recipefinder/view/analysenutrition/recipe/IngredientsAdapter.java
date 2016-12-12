package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.IngredientListItemBinding;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<String> ingredientList = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IngredientListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.ingredient_list_item,
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindIngredient();
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public void addItem(String text) {
        ingredientList.add(text);
        notifyItemInserted(ingredientList.size() - 1);
    }

    public void removeItem(int position) {
        ingredientList.remove(position);
        notifyItemRangeChanged(position, ingredientList.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private IngredientListItemBinding binding;

        public ViewHolder(IngredientListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindIngredient() {
            if (binding.getViewModel() == null)
                binding.setViewModel(new IngredientViewModel(binding.getRoot().getContext()));

            binding.getViewModel().setIngredient(ingredientList.get(getAdapterPosition()), getAdapterPosition() + 1);
        }
    }

    public class IngredientViewModel extends BaseViewModel {
        public final ObservableField<String> text;
        public final ObservableField<String> number;

        public IngredientViewModel(Context context) {
            super(context);
            text = new ObservableField<>();
            number = new ObservableField<>();
        }

        public void onCancelButtonClick(View button) {
            removeItem(Integer.parseInt(number.get()) - 1);
        }

        public void setIngredient(String text, int position) {
            this.text.set(text);
            number.set(String.valueOf(position));
        }
    }
}

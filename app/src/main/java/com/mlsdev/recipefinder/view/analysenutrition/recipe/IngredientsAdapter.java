package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.AddIngredientButtonBinding;
import com.mlsdev.recipefinder.databinding.IngredientListItemBinding;
import com.mlsdev.recipefinder.view.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<String> ingredientList = new ArrayList<>();
    private OnAddIngredientClickListener onAddIngredientClickListener;
    private static final int INGREDIENT_VIEW_TYPE = 0;
    private static final int ADD_INGREDIENT_VIEW_TYPE = 1;

    public IngredientsAdapter(OnAddIngredientClickListener onAddIngredientClickListener) {
        this.onAddIngredientClickListener = onAddIngredientClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position < ingredientList.size() ? INGREDIENT_VIEW_TYPE : ADD_INGREDIENT_VIEW_TYPE;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == INGREDIENT_VIEW_TYPE) {
            IngredientListItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.ingredient_list_item,
                    parent,
                    false
            );
            return new ViewHolder(binding);
        } else {
            AddIngredientButtonBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.add_ingredient_button,
                    parent,
                    false
            );
            return new AddIngredientButtonHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindViewModel();
    }

    @Override
    public int getItemCount() {
        return ingredientList.size() + ADD_INGREDIENT_VIEW_TYPE;
    }

    public void addItem(String text) {
        ingredientList.add(text);
        notifyItemInserted(ingredientList.size() - 1);
    }

    public void removeItem(int position) {
        ingredientList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ingredientList.size() - 1);
    }

    public void addItem(Intent data) {
        if (data.hasExtra(AddIngredientDialogFragment.INGREDIENT_TITLE_KEY))
            addItem(data.getStringExtra(AddIngredientDialogFragment.INGREDIENT_TITLE_KEY));
    }

    public class ViewHolder extends BaseViewHolder {
        private IngredientListItemBinding binding;

        public ViewHolder(IngredientListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindViewModel() {
            if (binding.getViewModel() == null)
                binding.setViewModel(new IngredientViewModel(binding.getRoot().getContext()));

            binding.getViewModel().setIngredient(ingredientList.get(getAdapterPosition()), getAdapterPosition() + 1);
        }
    }

    public class AddIngredientButtonHolder extends BaseViewHolder {
        AddIngredientButtonBinding binding;

        public AddIngredientButtonHolder(AddIngredientButtonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bindViewModel() {
            if (binding.getViewModel() == null)
                binding.setViewModel(new AddIngredientViewModel(binding.getRoot().getContext()));
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

    public class AddIngredientViewModel extends BaseViewModel {

        public AddIngredientViewModel(Context context) {
            super(context);
        }

        public void onAddIngredientButtonClick(View view) {
            if (onAddIngredientClickListener != null)
                onAddIngredientClickListener.onAddIngredientButtonClick();
        }
    }
}

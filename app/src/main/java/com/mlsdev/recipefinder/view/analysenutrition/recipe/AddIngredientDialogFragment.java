package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.DialogFragmentAddIngredientBinding;
import com.mlsdev.recipefinder.view.BaseActivity;

public class AddIngredientDialogFragment extends DialogFragment {
    public static final String INGREDIENT_TITLE_KEY = "ingredient_label_key";
    private DialogFragmentAddIngredientBinding binding;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(LayoutInflater.from(getActivity()), R.layout.dialog_fragment_add_ingredient, null, false);

        ((BaseActivity) getActivity()).showSoftKeyboard();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogAppCompat);
        builder.setTitle("Add Ingredient")
                .setView(binding.getRoot())
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(R.string.btn_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent data = new Intent();
                        data.putExtra(INGREDIENT_TITLE_KEY, binding.etIngredientInput.getText().toString());

                        if (getTargetFragment() != null) {
                            getTargetFragment().onActivityResult(
                                    RecipeAnalysisViewModel.ADD_INGREDIENT_REQUEST_CODE,
                                    Activity.RESULT_OK,
                                    data);
                        }

                        dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        ((BaseActivity) getActivity()).hideSoftKeyboard();
        super.onDismiss(dialog);
    }

}

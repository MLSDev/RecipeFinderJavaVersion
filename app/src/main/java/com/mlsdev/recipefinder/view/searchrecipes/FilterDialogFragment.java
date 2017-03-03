package com.mlsdev.recipefinder.view.searchrecipes;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.databinding.DialogFragmentSearchFilterBinding;

public class FilterDialogFragment extends DialogFragment {
    private DialogFragmentSearchFilterBinding binding;
    public static final String HEALTH_LABEL_KEY = "health_label_key";
    public static final String DIET_LABEL_KEY = "diet_label_key";
    private int healthSelectedIndex;
    private int dietSelectedIndex;

    @Override
    public void onStart() {
        super.onStart();
        binding.spHealthLabels.setSelection(healthSelectedIndex);
        binding.spDietLabels.setSelection(dietSelectedIndex);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_search_filter, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogAppCompat);
        builder.setView(binding.getRoot())
                .setTitle(R.string.dialog_title_filter)
                .setPositiveButton(R.string.btn_apply_filter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent data = new Intent();
                        healthSelectedIndex = binding.spHealthLabels.getSelectedItemPosition();
                        dietSelectedIndex = binding.spDietLabels.getSelectedItemPosition();
                        data.putExtra(HEALTH_LABEL_KEY, (String) binding.spHealthLabels.getSelectedItem());
                        data.putExtra(DIET_LABEL_KEY, (String) binding.spDietLabels.getSelectedItem());

                        if (getTargetFragment() != null) {
                            getTargetFragment().onActivityResult(SearchRecipesFragment.FILTER_REQUEST_CODE,
                                    Activity.RESULT_OK, data);
                        }

                        dismiss();
                    }
                });


        return builder.create();
    }
}

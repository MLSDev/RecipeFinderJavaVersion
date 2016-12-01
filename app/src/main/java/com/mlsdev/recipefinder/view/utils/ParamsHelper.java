package com.mlsdev.recipefinder.view.utils;

public class ParamsHelper {

    public static String formatLabel(String label) {
        if (label == null)
            throw new IllegalArgumentException("'label' can't be 'null'");

        String formattedLabel = label.toLowerCase();
        formattedLabel = formattedLabel.replaceAll(" ", "-");
        return formattedLabel;
    }

}

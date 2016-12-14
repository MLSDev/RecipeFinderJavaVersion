package com.mlsdev.recipefinder.view.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mlsdev.recipefinder.R;

import java.util.ArrayList;
import java.util.List;

public class DiagramUtils {

    public static PieChart preparePieChrt(Context context, PieChart pieChart) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.96f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(android.R.color.transparent);

        pieChart.setTransparentCircleColor(ResourcesUtils.getColor(context, android.R.color.white));
        pieChart.setTransparentCircleAlpha(50);

        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);

        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("Balance");

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelColor(ResourcesUtils.getColor(context, android.R.color.white));
        pieChart.setEntryLabelTextSize(15f);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        return pieChart;
    }

    public static PieDataSet createPieDataSet(Context context,
                                              List<PieEntry> pieEntryList,
                                              @Nullable String label,
                                              @Nullable List<Integer> colors) {
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, label);
        pieDataSet.setSliceSpace(1.5f);
        pieDataSet.setSelectionShift(2f);

        pieDataSet.setDrawValues(true);

        if (colors == null) {
            colors = new ArrayList<>(3);
            colors.add(ResourcesUtils.getColor(context, R.color.colorPrimaryDark));
            colors.add(ResourcesUtils.getColor(context, R.color.colorPrimary));
            colors.add(ResourcesUtils.getColor(context, R.color.colorAccent));
        }

        pieDataSet.setColors(colors);
        return pieDataSet;
    }

    public static PieData createPieData(Context context, PieDataSet pieDataSet) {
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(ResourcesUtils.getColor(context, android.R.color.white));
        return pieData;
    }

    public static void setData(PieChart pieChart, PieData pieData) {
        pieChart.setData(pieData);
        pieChart.highlightValue(null);
        pieChart.invalidate();
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

}

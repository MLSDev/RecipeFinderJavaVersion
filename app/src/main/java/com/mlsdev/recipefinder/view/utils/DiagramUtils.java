package com.mlsdev.recipefinder.view.utils;

import android.support.annotation.Nullable;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.nutrition.TotalNutrients;

import java.util.ArrayList;
import java.util.List;

public class DiagramUtils {
    private UtilsUI utilsUI;

    public DiagramUtils(UtilsUI utilsUI) {
        this.utilsUI = utilsUI;
    }

    public ArrayList<PieEntry> preparePieEntries(TotalNutrients nutrients) {
        ArrayList<PieEntry> entries = new ArrayList<>(3);
        if (nutrients.getProtein() != null)
            entries.add(new PieEntry((float) nutrients.getProtein().getQuantity(), nutrients.getProtein().getLabel()));
        if (nutrients.getFat() != null)
            entries.add(new PieEntry((float) nutrients.getFat().getQuantity(), nutrients.getFat().getLabel()));
        if (nutrients.getCarbs() != null)
            entries.add(new PieEntry((float) nutrients.getCarbs().getQuantity(), nutrients.getCarbs().getLabel()));

        return entries;
    }

    public PieChart preparePieChart(PieChart pieChart) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.96f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(android.R.color.transparent);

        pieChart.setTransparentCircleColor(utilsUI.getColor(android.R.color.white));
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
        pieChart.setEntryLabelColor(utilsUI.getColor(android.R.color.white));
        pieChart.setEntryLabelTextSize(15f);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        return pieChart;
    }

    public PieDataSet createPieDataSet(List<PieEntry> pieEntryList,
                                       @Nullable String label,
                                       @Nullable List<Integer> colors) {
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, label);
        pieDataSet.setSliceSpace(1.5f);
        pieDataSet.setSelectionShift(2f);

        pieDataSet.setDrawValues(true);

        if (colors == null) {
            colors = new ArrayList<>(3);
            colors.add(utilsUI.getColor(R.color.colorPrimaryDark));
            colors.add(utilsUI.getColor(R.color.colorPrimary));
            colors.add(utilsUI.getColor(R.color.colorAccent));
        }

        pieDataSet.setColors(colors);
        return pieDataSet;
    }

    public PieData createPieData(PieDataSet pieDataSet) {
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(utilsUI.getColor(android.R.color.white));
        return pieData;
    }

    public void setData(PieChart pieChart, PieData pieData) {
        pieChart.setData(pieData);
        pieChart.highlightValue(null);
        pieChart.invalidate();
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

}

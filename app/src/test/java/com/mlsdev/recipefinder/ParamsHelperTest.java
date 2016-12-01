package com.mlsdev.recipefinder;

import com.mlsdev.recipefinder.view.utils.ParamsHelper;

import org.junit.Assert;
import org.junit.Test;

public class ParamsHelperTest {
    private final String expectedFormatLabelResult = "alcohol-free";
    private final String testableFormatLabelValue = "Alcohol Free";

    @Test
    public void testFilterLabelFormatting() {
        String actualResult = ParamsHelper.formatLabel(testableFormatLabelValue);
        Assert.assertEquals(expectedFormatLabelResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFilterLabelFormatting_WithNullAsArgument() {
        ParamsHelper.formatLabel(null);
    }

}

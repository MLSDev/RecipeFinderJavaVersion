package com.mlsdev.recipefinder;

import com.mlsdev.recipefinder.data.entity.nutrition.Nutrient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NutrientTest {
    private double quantity = 100.081;
    private String label = "Protein";
    private String unit = "g";
    private String expectedResult;
    private Nutrient nutrient;

    @Before
    public void setUp() {
        expectedResult = label + " 100.08 " + unit;
        nutrient = new Nutrient(label, quantity, unit);
    }

    @Test
    public void testGetFormattedFullText() {
        String actualResult = nutrient.getFormattedFullText();
        Assert.assertEquals(expectedResult, actualResult);
    }

}

package com.mlsdev.recipefinder.view.analysenutrition.ingredient;

import android.support.test.rule.ActivityTestRule;

import com.mlsdev.recipefinder.AssetUtils;
import com.mlsdev.recipefinder.BaseTest;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.view.MainActivity;
import com.squareup.spoon.Spoon;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class IngredientAnalysisFragmentTest extends BaseTest {
    private final String spoonTag = "Ingredient_Analysis";

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws IOException {
        super.setUp();
    }

    @After
    public void tearDown() throws IOException {
        super.tearDown();
    }

    @Test
    public void testIngredientAnalysis() {
        String energy = "Energy 151.20 kcal";
        String fat = "Fat 0.96 g";
        String protein = "Protein 2.07 g";
        String carbs = "Carbs 37.75 g";
        mockWebServer.enqueue(new MockResponse().setBody(AssetUtils.getIngredientAnalysisJsonData(context)));

        selectTab();
        inputFields();

        Spoon.screenshot(rule.getActivity(), spoonTag);

        onView(withId(R.id.btn_analyze_ingredient)).perform(click());

        onView(withId(R.id.tv_ingredient_title)).check(matches(isDisplayed()));
        onView(withText(energy)).check(matches(isDisplayed()));
        onView(withText(fat)).check(matches(isDisplayed()));
        onView(withText(protein)).check(matches(isDisplayed()));
        onView(withText(carbs)).check(matches(isDisplayed()));

        Spoon.screenshot(rule.getActivity(), spoonTag);
    }

    @Test
    public void testServerError() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));
        selectTab();
        inputFields();
        onView(withId(R.id.btn_analyze_ingredient)).perform(click());
        onView(withText(R.string.error_message_technical)).check(matches(isDisplayed()));
        Spoon.screenshot(rule.getActivity(), spoonTag);
    }

    @Test
    public void testNetworkError() {
        mockWebServer.enqueue(new MockResponse().throttleBody(1024, 1, TimeUnit.SECONDS));
        selectTab();
        inputFields();
        onView(withId(R.id.btn_analyze_ingredient)).perform(click());
        onView(withText(R.string.error_message_connection)).check(matches(isDisplayed()));
        Spoon.screenshot(rule.getActivity(), spoonTag);
    }

    @Test
    public void testIngredientAnalysis_EmptyField() {
        selectTab();
        onView(withId(R.id.btn_analyze_ingredient)).perform(click());
        onView(withText(R.string.error_empty_ingredient_field)).check(matches(isDisplayed()));
        Spoon.screenshot(rule.getActivity(), spoonTag);
        onView(withText(R.string.btn_fill_in)).perform(click());
    }

    private void selectTab() {
        onView(withId(R.id.action_analyse_nutrition)).perform(click());
    }

    private void inputFields() {
        String ingredient = "1 small mango";
        onView(withId(R.id.et_ingredient_input)).perform(typeText(ingredient));
        closeSoftKeyboard();
    }

}

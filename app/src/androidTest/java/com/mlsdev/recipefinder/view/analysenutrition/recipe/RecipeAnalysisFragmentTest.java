package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;

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

import okhttp3.mockwebserver.MockResponse;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class RecipeAnalysisFragmentTest extends BaseTest {
    private final String spoonTag = "Recipe_Analysis";

    @Rule
    public IntentsTestRule<MainActivity> rule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws IOException {
        super.setUp();
    }

    @After
    public void tearDown() throws IOException {
        super.tearDown();
    }

    @Test
    public void testRecipeAnalysis() {
        String title = "Lemonade";
        String preparation = "Add lemon slices and water then shake it.";
        String yield = "15";
        String ingredient1 = "5 small lemons";
        String ingredient2 = "1l water";
        mockWebServer.enqueue(new MockResponse().setBody(AssetUtils.getRecipeAnalysisJsonData(context)));

        onView(withId(R.id.action_analyse_nutrition)).perform(click());
        onView(withId(R.id.vp_analysis_pages)).perform(swipeLeft());

        onView(withId(R.id.et_recipe_title)).perform(typeText(title));
        onView(withId(R.id.et_recipe_title)).perform(typeText(title));
        onView(withId(R.id.et_preparation)).perform(typeText(preparation));
        onView(withId(R.id.et_yield)).perform(typeText(yield));
        closeSoftKeyboard();

        addIngredient(ingredient1);
        addIngredient(ingredient2);

        onView(withId(R.id.btn_analyze)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(RecipeAnalysisDetailsActivity.class.getName()));

        Spoon.screenshot(rule.getActivity(), spoonTag);
    }

    private void addIngredient(String ingredient) {
        onView(withId(R.id.btn_add_ingredient)).perform(click());
        onView(withId(R.id.et_ingredient_input))
                .check(matches(isDisplayed()))
                .perform(typeText(ingredient));

        onView(withText(R.string.btn_add)).perform(click());

        closeSoftKeyboard();
    }

}

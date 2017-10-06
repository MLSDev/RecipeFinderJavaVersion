package com.mlsdev.recipefinder.view.analysenutrition.recipe;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.mlsdev.recipefinder.AssetUtils;
import com.mlsdev.recipefinder.MockApp;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.entity.nutrition.RecipeAnalysisParams;
import com.mlsdev.recipefinder.data.source.repository.DataRepository;
import com.mlsdev.recipefinder.di.MockApplicationComponent;
import com.mlsdev.recipefinder.view.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.HttpException;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecipeAnalysisFragmentTest {
    private Context context;
    private final String spoonTag = "Recipe_Analysis";

    @Inject
    DataRepository repository;

    @Rule
    public IntentsTestRule<MainActivity> rule = new IntentsTestRule<>(MainActivity.class, true, false);

    @Before
    public void setUp() throws IOException {
        context = InstrumentationRegistry.getContext();
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockApp app = (MockApp) instrumentation.getTargetContext().getApplicationContext();
        MockApplicationComponent component = app.getComponent();
        component.inject(this);
    }

    @Test
    public void testRecipeAnalysis() {
        when(repository.getRecipeAnalysisData(ArgumentMatchers.<RecipeAnalysisParams>any()))
                .thenReturn(Single.just(AssetUtils.getRecipeAnalysisResult(context)));

        rule.launchActivity(new Intent());

        selectTab();
        inputFields();

        onView(withId(R.id.btn_analyze)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(RecipeAnalysisDetailsActivity.class.getName()));
    }

    @Test
    public void testRecipeAnalyses_NoIngredients() {
        rule.launchActivity(new Intent());
        selectTab();
        onView(withId(R.id.btn_analyze)).perform(click());
        onView(withText(R.string.no_ingredients_error_message)).check(matches(isDisplayed()));

        onView(withText(R.string.btn_add)).perform(click());
        onView(withId(R.id.et_ingredient_input))
                .check(matches(isDisplayed()))
                .perform(typeText("Ingredient"));

        onView(withText(R.string.btn_add)).perform(click());

        closeSoftKeyboard();
    }

    @Test
    public void testServerError() {
        HttpException exception = mock(HttpException.class);
        when(exception.code()).thenReturn(500);
        when(exception.getMessage()).thenReturn("");
        when(repository.getRecipeAnalysisData(ArgumentMatchers.<RecipeAnalysisParams>any()))
                .thenReturn(Single.<NutritionAnalysisResult>error(exception));

        rule.launchActivity(new Intent());

        selectTab();
        inputFields();
        onView(withId(R.id.btn_analyze)).perform(click());
        onView(withText(R.string.error_message_technical)).check(matches(isDisplayed()));
    }

    @Test
    public void testNetworkError() {
        when(repository.getRecipeAnalysisData(ArgumentMatchers.<RecipeAnalysisParams>any()))
                .thenReturn(Single.<NutritionAnalysisResult>error(new IOException("Network error")));
        rule.launchActivity(new Intent());
        selectTab();
        inputFields();
        onView(withId(R.id.btn_analyze)).perform(click());
        onView(withText(R.string.error_message_connection)).check(matches(isDisplayed()));
    }

    private void inputFields() {
        String title = "Lemonade";
        String preparation = "Add lemon slices and water then shake it.";
        String yield = "15";
        String ingredient1 = "5 small lemons";
        String ingredient2 = "1l water";

        onView(withId(R.id.et_recipe_title)).perform(typeText(title));
        onView(withId(R.id.et_preparation)).perform(typeText(preparation));
        onView(withId(R.id.et_yield)).perform(typeText(yield));
        closeSoftKeyboard();

        addIngredient(ingredient1);
        addIngredient(ingredient2);
    }

    private void addIngredient(String ingredient) {
        onView(withId(R.id.btn_add_ingredient)).perform(click());
        onView(withId(R.id.et_ingredient_input))
                .check(matches(isDisplayed()))
                .perform(typeText(ingredient));

        onView(withText(R.string.btn_add)).perform(click());

        closeSoftKeyboard();
    }

    private void selectTab() {
        onView(withId(R.id.action_analyse_nutrition)).perform(click());
        onView(withId(R.id.vp_analysis_pages)).perform(swipeLeft());
        onView(withId(R.id.vp_analysis_pages)).perform(swipeLeft());
    }

}

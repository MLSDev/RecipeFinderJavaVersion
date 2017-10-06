package com.mlsdev.recipefinder.view.analysenutrition.ingredient;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.mlsdev.recipefinder.AssetUtils;
import com.mlsdev.recipefinder.MockApp;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.nutrition.NutritionAnalysisResult;
import com.mlsdev.recipefinder.data.source.repository.DataRepository;
import com.mlsdev.recipefinder.di.MockApplicationComponent;
import com.mlsdev.recipefinder.view.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.HttpException;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IngredientAnalysisFragmentTest {
    private Context context;
    private final String spoonTag = "Ingredient_Analysis";

    @Inject
    DataRepository repository;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void setUp() throws IOException {
        context = InstrumentationRegistry.getContext();
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockApp app = (MockApp) instrumentation.getTargetContext().getApplicationContext();
        MockApplicationComponent component = app.getComponent();
        component.inject(this);
    }

    @Test
    public void testIngredientAnalysis() {
        String energy = "Energy 151.20 kcal";
        String fat = "Fat 0.96 g";
        String protein = "Protein 2.07 g";
        String carbs = "Carbs 37.75 g";

        Mockito.when(repository.getIngredientData(Mockito.<String, String>anyMap()))
                .thenReturn(Single.just(AssetUtils.getNutritionAnalysisResult(context)));

        rule.launchActivity(new Intent());
        selectTab();
        inputFields();

        onView(withId(R.id.btn_analyze_ingredient)).perform(click());

        onView(withId(R.id.tv_ingredient_title)).check(matches(isDisplayed()));
        onView(withText(energy)).check(matches(isDisplayed()));
        onView(withText(fat)).check(matches(isDisplayed()));
        onView(withText(protein)).check(matches(isDisplayed()));
        onView(withText(carbs)).check(matches(isDisplayed()));
    }

    @Test
    public void testServerError() {
        HttpException exception = mock(HttpException.class);
        when(exception.code()).thenReturn(500);
        when(exception.getMessage()).thenReturn("");

        Mockito.when(repository.getIngredientData(Mockito.<String, String>anyMap()))
                .thenReturn(Single.<NutritionAnalysisResult>error(exception));

        rule.launchActivity(new Intent());
        selectTab();
        inputFields();
        onView(withId(R.id.btn_analyze_ingredient)).perform(click());
        onView(withText(R.string.error_message_technical)).check(matches(isDisplayed()));
    }

    @Test
    public void testNetworkError() {
        Mockito.when(repository.getIngredientData(Mockito.<String, String>anyMap()))
                .thenReturn(Single.<NutritionAnalysisResult>error(new IOException("Network error")));

        rule.launchActivity(new Intent());
        selectTab();
        inputFields();
        onView(withId(R.id.btn_analyze_ingredient)).perform(click());
        onView(withText(R.string.error_message_connection)).check(matches(isDisplayed()));
    }

    @Test
    public void testIngredientAnalysis_EmptyField() {
        rule.launchActivity(new Intent());
        selectTab();
        onView(withId(R.id.btn_analyze_ingredient)).perform(click());
        onView(withText(R.string.error_empty_ingredient_field)).check(matches(isDisplayed()));
        onView(withText(R.string.btn_fill_in)).perform(click());
        onView(withId(R.id.et_ingredient_input)).check(matches(hasFocus()));
    }

    private void selectTab() {
        closeSoftKeyboard();
        onView(withId(R.id.action_analyse_nutrition)).perform(click());
    }

    private void inputFields() {
        String ingredient = "1 small mango";
        onView(withId(R.id.et_ingredient_input)).perform(typeText(ingredient));
        closeSoftKeyboard();
    }

}

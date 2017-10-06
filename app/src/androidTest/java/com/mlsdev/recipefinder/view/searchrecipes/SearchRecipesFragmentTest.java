package com.mlsdev.recipefinder.view.searchrecipes;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.mlsdev.recipefinder.AssetUtils;
import com.mlsdev.recipefinder.MockApp;
import com.mlsdev.recipefinder.R;
import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.data.source.repository.DataRepository;
import com.mlsdev.recipefinder.di.MockApplicationComponent;
import com.mlsdev.recipefinder.view.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.HttpException;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchRecipesFragmentTest {
    private Context context;
    private final String searchText = "chicken";
    private final String firstRecipeTitle = "Grilled Deviled Chickens Under a Brick";
    private final String lastRecipeTitle = "Herb-Roasted Chickens";

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
    public void testStartSearching() throws InterruptedException {
        when(repository.searchRecipes(Mockito.<String, String>anyMap()))
                .thenReturn(Single.just(AssetUtils.getRecipeList(context)));
        when(repository.loadMore(Mockito.<String, String>anyMap()))
                .thenReturn(Single.just(AssetUtils.getMoreRecipeList(context)));
        when(repository.isInFavorites(Mockito.<Recipe>any()))
                .thenReturn(Single.just(true));
        rule.launchActivity(new Intent());
        performSearch();

        onView(withText(firstRecipeTitle)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.scrollToPosition(9));
        onView(withText(lastRecipeTitle)).check(matches(isDisplayed()));

        // Scroll to top and click upon the first item
        Recipe recipe = AssetUtils.getRecipeEntity(InstrumentationRegistry.getContext());
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.scrollToPosition(0));

        onView(withText(recipe.getLabel())).perform(ViewActions.click());

        onView(withId(R.id.tv_health_labels)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_recipe_image)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    private void performSearch() throws InterruptedException {
        onView(withId(R.id.action_search)).perform(ViewActions.click());
        onView(withId(R.id.ed_search_text)).perform(ViewActions.typeText(searchText));
        onView(withId(R.id.ed_search_text)).perform(ViewActions.pressImeActionButton());
        closeSoftKeyboard();
        TimeUnit.MILLISECONDS.sleep(500);
    }

    @Test
    public void testNothingFound() throws InterruptedException {
        when(repository.searchRecipes(ArgumentMatchers.<String, String>anyMap()))
                .thenReturn(Single.just(Collections.<Recipe>emptyList()));
        rule.launchActivity(new Intent());
        performSearch();
        onView(withText(R.string.label_search_nothing_found)).check(matches(isDisplayed()));
    }

    @Test
    public void testServerError() throws InterruptedException {
        HttpException exception = mock(HttpException.class);
        when(exception.code()).thenReturn(500);
        when(exception.getMessage()).thenReturn("");
        when(repository.searchRecipes(ArgumentMatchers.<String, String>anyMap()))
                .thenReturn(Single.<List<Recipe>>error(exception));
        rule.launchActivity(new Intent());

        performSearch();
        onView(withText(R.string.error_message_technical)).check(matches(isDisplayed()));
    }

    @Test
    public void testNetworkError() throws InterruptedException {
        when(repository.searchRecipes(ArgumentMatchers.<String, String>anyMap()))
                .thenReturn(Single.<List<Recipe>>error(new IOException("Network error")));
        rule.launchActivity(new Intent());
        performSearch();
        onView(withText(R.string.error_message_connection)).check(matches(isDisplayed()));
    }
}

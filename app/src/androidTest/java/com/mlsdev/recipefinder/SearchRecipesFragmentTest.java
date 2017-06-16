package com.mlsdev.recipefinder;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.mlsdev.recipefinder.data.entity.recipe.Recipe;
import com.mlsdev.recipefinder.view.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SearchRecipesFragmentTest extends BaseTest {
    private String searchText = "chicken";
    private String firstRecipeTitle = "Grilled Deviled Chickens Under a Brick";
    private String lastRecipeTitle = "Herb-Roasted Chickens";

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
    public void testStartSearching() {
        mockWebServer.enqueue(new MockResponse().setBody(AssetUtils.getSearchResultJsonData(getContext())));
        mockWebServer.enqueue(new MockResponse().setBody(AssetUtils.getMoreRecipesJsonData(getContext())));
        performSearch();

        onView(withText(firstRecipeTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.scrollToPosition(9));
        onView(withText(lastRecipeTitle)).check(matches(isDisplayed()));

        // Scroll to top and click upon the first item
        Recipe recipe = AssetUtils.getRecipeEntity(InstrumentationRegistry.getContext());
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withText(recipe.getLabel())).perform(ViewActions.click());
        onView(withId(R.id.tv_health_labels)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_recipe_image)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    private Context getContext() {
        return InstrumentationRegistry.getContext();
    }

    private void performSearch() {
        onView(withId(R.id.action_search)).perform(ViewActions.click());

        onView(withId(R.id.ed_search_text))
                .perform(ViewActions.typeText(searchText))
                .perform(ViewActions.pressImeActionButton());

        closeSoftKeyboard();
    }

    @Test
    public void testNothingFound() {
        mockWebServer.enqueue(new MockResponse().setBody(AssetUtils.getEmptySearchResultJsonData(getContext())));
        performSearch();
        onView(withText(R.string.label_search_nothing_found)).check(matches(isDisplayed()));
    }

    @Test
    public void testServerError() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));
        performSearch();
        onView(withText(R.string.error_message_technical)).check(matches(isDisplayed()));
    }

    @Test
    public void testNetworkError() {
        mockWebServer.enqueue(new MockResponse().throttleBody(1024, 1, TimeUnit.SECONDS));
        performSearch();
        onView(withText(R.string.error_message_common)).check(matches(isDisplayed()));
    }
}

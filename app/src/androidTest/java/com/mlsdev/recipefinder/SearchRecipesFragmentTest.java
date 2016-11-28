package com.mlsdev.recipefinder;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.mlsdev.recipefinder.data.source.remote.RemoteDataSource;
import com.mlsdev.recipefinder.idlingutils.BetterIdlingResource;
import com.mlsdev.recipefinder.view.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class SearchRecipesFragmentTest {
    private String searchText = "chicken";
    private String firstRecipeTitle = "Grilled Deviled Chickens Under a Brick";
    private String lastRecipeTitle = "Herb-Roasted Chickens";
    private int offset = 10;
    public MockWebServer mockWebServer;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws IOException {
        Espresso.registerIdlingResources(new BetterIdlingResource());
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        RemoteDataSource.setBaseUrl(mockWebServer.url("/").url().toString());
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testStartSearching() {
        mockWebServer.enqueue(new MockResponse().setBody(AssetUtils.getSearchResultJsonData(rule.getActivity())));

        Espresso.onView(ViewMatchers.withId(R.id.et_search))
                .perform(ViewActions.typeText(searchText))
                .perform(ViewActions.pressImeActionButton())
                .perform(ViewActions.closeSoftKeyboard());
        
    }
}

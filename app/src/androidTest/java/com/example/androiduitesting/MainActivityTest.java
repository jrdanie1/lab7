package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    // -------------------------------------
    // ORIGINAL TESTS (UNCHANGED)
    // -------------------------------------

    @Test
    public void testAddCity() {
        // Click on Add City button
        onView(withId(R.id.button_add)).perform(click());

        // Type "Edmonton" in the editText
        onView(withId(R.id.editText_name))
                .perform(ViewActions.typeText("Edmonton"));

        // Click on Confirm
        onView(withId(R.id.button_confirm)).perform(click());

        // Check if text "Edmonton" is displayed
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity() {
        // Add first city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name))
                .perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Add another city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name))
                .perform(ViewActions.typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Clear the list
        onView(withId(R.id.button_clear)).perform(click());

        // Verify both cities no longer exist
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    @Test
    public void testListView() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name))
                .perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Check if the data in the adapter at position 0 matches "Edmonton"
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .check(matches(withText("Edmonton")));
    }

    // -------------------------------------
    // NEW TESTS FOR SHOWACTIVITY
    // -------------------------------------
    // ⚠️ NOTE: For these to work, you must add the following to your MainActivity:
    // cityListView.setOnItemClickListener((parent, view, position, id) -> {
    //     String cityName = (String) parent.getItemAtPosition(position);
    //     Intent intent = new Intent(MainActivity.this, ShowActivity.class);
    //     intent.putExtra("city_name", cityName);
    //     startActivity(intent);
    // });

    // 1️⃣ Check whether the activity correctly switched
    @Test
    public void testActivitySwitch() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click the city in the list to open ShowActivity
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Verify ShowActivity is displayed by checking city name TextView
        onView(withId(R.id.textView_cityName)).check(matches(isDisplayed()));
    }

    // 2️⃣ Test whether the city name is consistent
    @Test
    public void testCityNameConsistency() {
        // Add "Calgary" city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Calgary"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Open ShowActivity
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Verify that ShowActivity displays the same city name
        onView(withId(R.id.textView_cityName)).check(matches(withText("Calgary")));
    }

    // 3️⃣ Test the "Back" button functionality
    @Test
    public void testBackButton() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Toronto"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Open ShowActivity
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Click Back button
        onView(withId(R.id.button_back)).perform(click());

        // Verify we’re back to MainActivity (city list should be visible)
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}

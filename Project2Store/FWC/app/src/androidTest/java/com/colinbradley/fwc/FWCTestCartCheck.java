package com.colinbradley.fwc;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FWCTestCartCheck {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //Checking all items added to cart go to cartList
    @Test
    public void fWCTestCartCheck() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(4, click()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton.perform(click());

        pressBack();

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recycler), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton2.perform(click());

        pressBack();

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recycler), isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(11, click()));

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton3.perform(click());

        pressBack();

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.recycler), isDisplayed()));
        recyclerView4.perform(actionOnItemAtPosition(15, click()));

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton4.perform(click());

        pressBack();

        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.recycler), isDisplayed()));
        recyclerView5.perform(actionOnItemAtPosition(19, click()));

        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton5.perform(click());

        pressBack();

        ViewInteraction floatingActionButton6 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton6.perform(click());

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.rellayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.shopping_cart_rv),
                                        0),
                                0),
                        isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));

        ViewInteraction relativeLayout2 = onView(
                allOf(withId(R.id.rellayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.shopping_cart_rv),
                                        1),
                                0),
                        isDisplayed()));
        relativeLayout2.check(matches(isDisplayed()));

        ViewInteraction relativeLayout3 = onView(
                allOf(withId(R.id.rellayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.shopping_cart_rv),
                                        2),
                                0),
                        isDisplayed()));
        relativeLayout3.check(matches(isDisplayed()));

        ViewInteraction relativeLayout4 = onView(
                allOf(withId(R.id.rellayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.shopping_cart_rv),
                                        3),
                                0),
                        isDisplayed()));
        relativeLayout4.check(matches(isDisplayed()));

        ViewInteraction relativeLayout5 = onView(
                allOf(withId(R.id.rellayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.shopping_cart_rv),
                                        4),
                                0),
                        isDisplayed()));
        relativeLayout5.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

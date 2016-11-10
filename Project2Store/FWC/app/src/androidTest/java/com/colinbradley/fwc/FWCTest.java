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
import org.hamcrest.core.IsInstanceOf;
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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FWCTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //Testing for almost all functionality of app
    //adding items to cart
    //checking cart price
    //changing quantity of items in cart and checking updated price(both adding and removing)
    //checkout cart and check the price has gone away
    @Test
    public void fWCTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton.perform(click());

        pressBack();

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recycler), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(15, click()));

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton2.perform(click());

        pressBack();

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.shopping_cart_rv),
                        childAtPosition(
                                allOf(withId(R.id.content_shopping_cart),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                1)),
                                0),
                        isDisplayed()));
        recyclerView3.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.total_price), withText("570"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                2),
                        isDisplayed()));
        textView.check(matches(withText("570")));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.add1button), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(withId(R.id.shopping_cart_rv), 0),
                                        0
                                ),
                                3
                        ),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.add1button), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(withId(R.id.shopping_cart_rv), 0),
                                        0
                                ),
                                3
                        ),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.update_price_button), withText("Update Cart"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.total_price), withText("710"),
                        isDisplayed()));
        textView2.check(matches(withText("710")));

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.remove1button), withText("-"),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(withId(R.id.shopping_cart_rv), 0),
                                        0
                                ),
                                4
                        ),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.remove1button), withText("-"),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(withId(R.id.shopping_cart_rv), 0),
                                        0
                                ),
                                4
                        ),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.update_price_button), withText("Update Cart"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.total_price), withText("140"),
                        isDisplayed()));
        textView3.check(matches(withText("140")));

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton4.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.total_price), withText("0"),
                        isDisplayed()));
        textView4.check(matches(withText("0")));

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

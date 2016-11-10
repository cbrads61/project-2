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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FWCTestCartPersistingData {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);



    @Test
    public void fWCTestCartPersistingData() {

//ADDING ITEMS TO THE SHOPPING CART (2 ITEMS)

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

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

//CHECKS IF BOTH ITEMS ARE IN CART

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton3.perform(click());

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

//GOES BACK TO LIST THEN BACK INTO CART TO CHECK IF CART PERSISTS DATA
        //ALSO CHECKS PRICE

        pressBack();

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton4.perform(click());

        ViewInteraction relativeLayout3 = onView(
                allOf(withId(R.id.rellayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.shopping_cart_rv),
                                        0),
                                0),
                        isDisplayed()));
        relativeLayout3.check(matches(isDisplayed()));

        ViewInteraction relativeLayout4 = onView(
                allOf(withId(R.id.rellayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.shopping_cart_rv),
                                        1),
                                0),
                        isDisplayed()));
        relativeLayout4.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.total_price), withText("140"),
                        isDisplayed()));
        textView.check(matches(withText("140")));

//GO BACK TO LIST AND ADDS MORE ITEMS TO CART (2 MORE ITEMS)

        pressBack();

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recycler), isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton5.perform(click());

        pressBack();

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.recycler), isDisplayed()));
        recyclerView4.perform(actionOnItemAtPosition(12, click()));

        ViewInteraction floatingActionButton6 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton6.perform(click());

        pressBack();

//CHECKS LIST FOR ORIGINAL 2 ITEMS AND NEW 2 ITEMS (4 TOTAL)
        //ALSO CHECKS TOTAL PRICE

        ViewInteraction floatingActionButton7 = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton7.perform(click());

        ViewInteraction relativeLayout5 = onView(
                allOf(withId(R.id.rellayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.shopping_cart_rv),
                                        0),
                                0),
                        isDisplayed()));
        relativeLayout5.check(matches(isDisplayed()));

        ViewInteraction relativeLayout6 = onView(
                allOf(withId(R.id.rellayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.shopping_cart_rv),
                                        1),
                                0),
                        isDisplayed()));
        relativeLayout6.check(matches(isDisplayed()));

        ViewInteraction relativeLayout7 = onView(
                allOf(withId(R.id.rellayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.shopping_cart_rv),
                                        2),
                                0),
                        isDisplayed()));
        relativeLayout7.check(matches(isDisplayed()));

        ViewInteraction relativeLayout8 = onView(
                allOf(withId(R.id.rellayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.shopping_cart_rv),
                                        3),
                                0),
                        isDisplayed()));
        relativeLayout8.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.total_price), withText("380"),
                        isDisplayed()));
        textView2.check(matches(withText("380")));

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

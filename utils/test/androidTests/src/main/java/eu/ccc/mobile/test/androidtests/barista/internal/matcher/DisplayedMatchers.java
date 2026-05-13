package eu.ccc.mobile.test.androidtests.barista.internal.matcher;

import android.view.View;

import org.hamcrest.Matcher;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class DisplayedMatchers {

    @NonNull
    public static Matcher<View> displayedWithIdAndClass(@IdRes int id, final Class<? extends View> viewClass) {
        return allOf(isDisplayed(), withId(id), isAssignableFrom(viewClass));
    }

    @NonNull
    public static Matcher<View> displayedAnd(Matcher<View> anotherMatcher) {
        return allOf(isDisplayed(), anotherMatcher);
    }
}

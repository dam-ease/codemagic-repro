package eu.ccc.mobile.test.androidtests.barista.internal.matcher;

import android.view.View;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import androidx.annotation.IdRes;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;

public class HelperMatchers {

    public static <T> Matcher<T> firstViewOf(final Matcher<T> matcher) {
        return new BaseMatcher<T>() {
            private boolean isFirst = true;

            @Override
            public boolean matches(final Object item) {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false;
                    return true;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("should return first matching item");
            }
        };
    }

    public static Matcher<View> withParentId(@IdRes int parentId) {
        return withParent(withId(parentId));
    }
}

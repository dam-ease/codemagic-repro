package eu.ccc.mobile.test.androidtests.barista.interaction;

import java.util.concurrent.TimeUnit;

import eu.ccc.mobile.test.androidtests.barista.internal.viewaction.SleepViewAction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

public class BaristaSleepInteractions {

    public static void sleep(long millis) {
        onView(isRoot()).perform(SleepViewAction.sleep(millis));
    }

    public static void sleep(long units, TimeUnit timeunit) {
        sleep(timeunit.toMillis(units));
    }
}

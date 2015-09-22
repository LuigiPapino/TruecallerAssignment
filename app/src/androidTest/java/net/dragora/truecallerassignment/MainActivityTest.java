package net.dragora.truecallerassignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Created by Luigi Papino on 22/09/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity_> {
    public MainActivityTest() {
        super(MainActivity_.class);
    }

MainActivity mainActivity;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(getInstrumentation());
        mainActivity = getActivity();
    }

    public void testSimple(){
        setPrefs(false, 0);
        onView(withId(R.id.fab))
                .perform(ViewActions.click());


        onView(withId(R.id.progressBar10thChar))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.progressBarEvery10thChar))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.progressBarWordCounter))
                .check(matches(not(isDisplayed())));
    }

    public void testDelayWithoutError(){
        setPrefs(false, 2000);
        onView(withId(R.id.fab))
                .perform(ViewActions.click());


        onView(withId(R.id.progressBar10thChar))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.progressBarEvery10thChar))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.progressBarWordCounter))
                .check(matches(not(isDisplayed())));
    }

    public void testDelayErrorWithoutDelay(){
        setPrefs(true, 0);
        onView(withId(R.id.fab))
                .perform(ViewActions.click());


        onView(withId(R.id.textTask10thChar))
                .check(matches(withError()));
        onView(withId(R.id.textTaskEvery10thChar))
                .check(matches(withError()));;
        onView(withId(R.id.textTaskWordCounter))
                .check(matches(withError()));
    }

    public void testDelayErrorWithDelay(){
        setPrefs(true, 2000);
        onView(withId(R.id.fab))
                .perform(ViewActions.click());


        onView(withId(R.id.textTask10thChar))
                .check(matches(withError()));
        onView(withId(R.id.textTaskEvery10thChar))
                .check(matches(withError()));;
        onView(withId(R.id.textTaskWordCounter))
                .check(matches(withError()));
    }

    private void setPrefs(boolean injectError, int injectDelay){
        SharedPreferences pref = mainActivity.getSharedPreferences("MainActivity__MyPrefs", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("injectError", injectError);
        editor.putInt("injectDelay", injectDelay);
        editor.apply();
    }


    private static Matcher<View> withError() {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextView)) {
                    return false;
                }
                TextView textView = (TextView) view;
                return textView.getError() != null;
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}

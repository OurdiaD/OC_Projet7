package com.openclassrooms.go4lunch;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withInputType;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.go4lunch.services.Notification;
import com.openclassrooms.go4lunch.ui.PreferenceActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SharedPreferenceTest {

    @Rule
    public ActivityScenarioRule<PreferenceActivity> activityRule = new ActivityScenarioRule<>(PreferenceActivity.class);

    @Test
    public void setSharedPrefTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        onView(withText("Notification")).perform(click());
        assertFalse(Notification.isStatNotif(appContext));

        onView(withText("Notification")).perform(click());
        assertTrue(Notification.isStatNotif(appContext));
    }
}

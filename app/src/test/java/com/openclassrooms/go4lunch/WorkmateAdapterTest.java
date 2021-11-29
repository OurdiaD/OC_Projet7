package com.openclassrooms.go4lunch;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import com.openclassrooms.go4lunch.ui.main.workmates.WorkmateAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.Q)
public class WorkmateAdapterTest {

    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void getTextWorkmateTest(){
        WorkmateAdapter adapter = new WorkmateAdapter(new ArrayList<>());
        String value = adapter.generateWorkmateText("user", "place name", context);
        assertEquals("user is eating place name", value);
        value = adapter.generateWorkmateText("user", null, context);
        assertEquals("user hasn't decided yet", value);
    }
}

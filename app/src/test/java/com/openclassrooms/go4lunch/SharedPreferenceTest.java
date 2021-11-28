package com.openclassrooms.go4lunch;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SharedPreferenceTest {
    SharedPreferences sharedPrefs;
    Context context;
    String PREFS = "PREFS_GO4LUNCH";
    String PREFS_NOTIF = "PREFS_NOTIF";

    @Mock
    SharedPreferences sharedPreferences;
    @Mock
    SharedPreferences.Editor sharedPreferencesEditor;

/*    @Before
    public void before() throws Exception {
        this.sharedPrefs = Mockito.mock(SharedPreferences.class);
        this.context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences(PREFS, MODE_PRIVATE)).thenReturn(sharedPrefs);
        MockitoAnnotations.initMocks(this);
        when(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor);
        when(sharedPreferencesEditor.putBoolean(PREFS_NOTIF, true)).thenReturn(sharedPreferencesEditor);

    }
    @Test
    public void sharedPrefsTest() throws Exception {
        when(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor);
        when(sharedPreferencesEditor.putBoolean(PREFS_NOTIF, false)).thenReturn(sharedPreferencesEditor);
        //verify(sharedPreferencesEditor.putBoolean(PREFS_NOTIF, true)).apply();
        Mockito.when(sharedPrefs.getBoolean(PREFS_NOTIF, true)).thenReturn(false);
        //sharedPrefs.edit().putBoolean(PREFS_NOTIF, false).apply();
        *//*boolean value = sharedPrefs.getBoolean(PREFS_NOTIF, true);
        assertFalse(value);*//*

    }*/

    @Test
    public void canRemoveUUID() {
        // Arrange
        SharedPreferences.Editor mockEdit = Mockito.mock(SharedPreferences.Editor.class);
        when(mockEdit.putBoolean(PREFS_NOTIF, false)).thenReturn(mockEdit);

        SharedPreferences mockedPrefs = Mockito.mock(SharedPreferences.class);
        when(mockedPrefs.edit()).thenReturn(mockEdit);

        Context mockedContext = Mockito.mock(Context.class);
        when(mockedContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE))
                .thenReturn(mockedPrefs);


        // Act
        //SessionHandler.removeAppInstanceID(mockedContext);

        // Assert
        verify(mockEdit, times(1)).putBoolean(PREFS_NOTIF, false);
        verify(mockEdit, times(1)).commit();
    }
/*   @Before
   public void setup() {
       MockitoAnnotations.initMocks(this);
       when(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor);
       when(sharedPreferencesEditor.putBoolean(PREFS_NOTIF, true)).thenReturn(sharedPreferencesEditor);
   }

    @Test
    public void saveString() {
        verify(sharedPreferencesEditor).putBoolean(PREFS_NOTIF, true);
        when(sharedPreferences.getBoolean(PREFS_NOTIF, true)).thenReturn(true);
    }

    @Test
    public void getString() {


        *//*String preferenceString = sharedPreferenceManager.doSomething();

        assertEquals(preferenceString, savedString);*//*
    }*/
}

package com.openclassrooms.go4lunch;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.firebase.ui.auth.AuthUI;
import com.openclassrooms.go4lunch.databinding.ActivityLoginBinding;


import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.ic_dining)
                        .build(),
                RC_SIGN_IN);
    }
}

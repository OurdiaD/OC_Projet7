package com.openclassrooms.go4lunch.ui.details;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.openclassrooms.go4lunch.databinding.ActivityDetailsBinding;
import com.openclassrooms.go4lunch.databinding.ActivityMainBinding;
import com.openclassrooms.go4lunch.models.maps.Result;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    Result place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initView();
        binding.detailsCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("lol details", "click");
            }
        });
    }

    public void initView() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String placeId = extras.getString("place_id");
            Log.d("lol details", placeId);
        }
    }

}

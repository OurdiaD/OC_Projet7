package com.openclassrooms.go4lunch.ui.details;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.openclassrooms.go4lunch.BuildConfig;
import com.openclassrooms.go4lunch.databinding.ActivityDetailsBinding;
import com.openclassrooms.go4lunch.databinding.ActivityMainBinding;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.models.maps.ResultDetails;
import com.openclassrooms.go4lunch.ui.main.list.ListViewModel;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    Result place;
    private DetailsViewModel detailsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);

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

            /*ResultDetails result = detailsViewModel.getDetailsOfPlace(placeId);
            Log.d("lol details", "" + result);
            if (result != null){
                binding.detailsName.setText(result.getName());
                binding.detailsAddress.setText(result.getVicinity());
                if (result.getPhotos() != null) {
                    String reference = result.getPhotos().get(0).photo_reference;
                    String apiKey = BuildConfig.API_KEY;
                    String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="+reference+"&key="+apiKey;
                    Log.d("lol adp photo", url);
                    Glide.with(getParent())
                            .load(url)
                            .into(binding.detailsPic);
                }
            }*/
            detailsViewModel.getDetailsOfPlace(placeId).observe(this, new Observer<ResultDetails>() {
                @Override
                public void onChanged(ResultDetails result) {
                    Log.d("lol actidetail", ""+ result);
                    setview(result);

                }
            });
        }
    }

    public void setview(ResultDetails result){
        if (result != null){
            binding.detailsName.setText(result.getName());
            binding.detailsAddress.setText(result.getVicinity());
            if (result.getPhotos() != null) {
                String reference = result.getPhotos().get(0).photo_reference;
                String apiKey = BuildConfig.API_KEY;
                String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="+reference+"&key="+apiKey;
                Log.d("lol adp photo", url);
                Glide.with(this)
                        .load(url)
                        .into(binding.detailsPic);
            }
        }
    }

}

package com.openclassrooms.go4lunch.ui.details;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.openclassrooms.go4lunch.BuildConfig;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.databinding.ActivityDetailsBinding;
import com.openclassrooms.go4lunch.models.User;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.models.maps.ResultDetails;
import com.openclassrooms.go4lunch.ui.main.workmates.WorkmateAdapter;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    ResultDetails place;
    private DetailsViewModel detailsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);

        initView();
    }

    public void initView() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String placeId = extras.getString("place_id");

            detailsViewModel.getDetailsOfPlace(placeId).observe(this, new Observer<ResultDetails>() {
                @Override
                public void onChanged(ResultDetails result) {
                    setview(result);
                }
            });
        }
    }

    public void setview(ResultDetails result){
        if (result != null){
            place = result;
            binding.detailsName.setText(result.getName());
            binding.detailsAddress.setText(result.getVicinity());
            if (result.getPhotos() != null) {
                String reference = result.getPhotos().get(0).getPhoto_reference();
                String apiKey = BuildConfig.API_KEY;
                String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="+reference+"&key="+apiKey;
                Glide.with(this).load(url).into(binding.detailsPic);
            }
            binding.detailsCall.setOnClickListener(setClickListener());
            binding.detailsSelect.setOnClickListener(setClickListener());
            binding.detailsLike.setOnClickListener(setClickListener());
            binding.detailsWebsite.setOnClickListener(setClickListener());

            detailsViewModel.getUserByPlaceId(result.getPlaceId()).observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(List<User> users) {
                    WorkmateAdapter workmateAdapter = new WorkmateAdapter(users);
                    binding.detailsList.setAdapter(workmateAdapter);
                }
            });

            detailsViewModel.getCurrentUser().observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user.getPlaceId().equals(result.getPlaceId())) {
                        binding.detailsSelect.setImageResource(R.drawable.ic_check_circle);
                    } else {
                        binding.detailsSelect.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
                    }
                    Drawable top;
                    if (user.getFavorite() != null && user.getFavorite().contains(result.getPlaceId())) {
                        top = getResources().getDrawable(R.drawable.ic_star_rate);
                    } else {
                        top = getResources().getDrawable(R.drawable.ic_star_outline);
                    }
                    binding.detailsLike.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                }
            });
        }
    }

    public View.OnClickListener setClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.details_select:
                        detailsViewModel.addSelectPlace(place.getPlaceId(), place.getName(), place.getVicinity());
                        break;
                    case R.id.details_call:
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getParent(),
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    PackageManager.PERMISSION_GRANTED);
                        }
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(place.getFormattedPhoneNumber()));
                        startActivity(intent);
                        break;
                    case R.id.details_like:
                        detailsViewModel.editFavPlace(place.getPlaceId());
                        break;
                    case R.id.details_website:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(place.getUrl()));
                        startActivity(browserIntent);
                        break;
                }
            }
        };
        return listener;
    }
}

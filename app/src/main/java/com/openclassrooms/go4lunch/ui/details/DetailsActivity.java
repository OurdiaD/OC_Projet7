package com.openclassrooms.go4lunch.ui.details;

import static com.openclassrooms.go4lunch.services.Notification.setupNotif;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.openclassrooms.go4lunch.BuildConfig;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.databinding.ActivityDetailsBinding;
import com.openclassrooms.go4lunch.models.maps.ResultDetails;
import com.openclassrooms.go4lunch.ui.main.workmates.WorkmateAdapter;

import java.util.Objects;

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
            detailsViewModel.getDetailsOfPlace(placeId).observe(this, this::setDataView);
        }
    }

    public void setDataView(ResultDetails result){
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

            detailsViewModel.getUserByPlaceId(result.getPlaceId()).observe(this, users -> {
                WorkmateAdapter workmateAdapter = new WorkmateAdapter(users);
                binding.detailsList.setAdapter(workmateAdapter);
            });

            detailsViewModel.getCurrentUser().observe(this, user -> {
                if (Objects.requireNonNull(user).getPlaceId() != null && user.getPlaceId().equals(result.getPlaceId())) {
                    binding.detailsSelect.setImageResource(R.drawable.ic_check_circle);
                } else {
                    binding.detailsSelect.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
                }
                Drawable top;
                if (user.getFavorite() != null && user.getFavorite() != null && user.getFavorite().contains(result.getPlaceId())) {
                    top = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_rate, null);

                } else {
                    top = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_outline, null);
                }
                binding.detailsLike.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            });
        }
    }

    public View.OnClickListener setClickListener() {
        return view -> {
            if (view.getId() == R.id.details_select){
                detailsViewModel.addSelectPlace(place.getPlaceId(), place.getName(), place.getVicinity());
                setupNotif(getApplicationContext());
            } else if (view.getId() == R.id.details_call) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + place.getFormattedPhoneNumber()));
                startActivity(intent);
            } else if (view.getId() == R.id.details_like){
                detailsViewModel.editFavPlace(place.getPlaceId());
            } else if (view.getId() == R.id.details_website){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(place.getWebsite()));
                startActivity(browserIntent);
            }
        };
    }

}

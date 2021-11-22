package com.openclassrooms.go4lunch.ui.details;

import static com.openclassrooms.go4lunch.services.Notification.setupNotif;
import static com.openclassrooms.go4lunch.services.PlaceUtils.getPhotoUrl;

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
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.databinding.ActivityDetailsBinding;
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
            detailsViewModel.getDetailsOfPlace(placeId).observe(this, this::setDataView);
        }
    }

    public void setDataView(ResultDetails result){
        if (result != null){
            place = result;
            binding.detailsName.setText(result.getName());
            binding.detailsAddress.setText(result.getVicinity());

            Glide.with(this).load(getPhotoUrl(result.getPhotos())).into(binding.detailsPic);

            binding.detailsCall.setOnClickListener(setClickListener());
            binding.detailsSelect.setOnClickListener(setClickListener());
            binding.detailsLike.setOnClickListener(setClickListener());
            binding.detailsWebsite.setOnClickListener(setClickListener());

            detailsViewModel.getUserByPlaceId(result.getPlaceId()).observe(this, users -> {
                WorkmateAdapter workmateAdapter = new WorkmateAdapter(users);
                binding.detailsList.setAdapter(workmateAdapter);
            });

            detailsViewModel.getCurrentUser().observe(this, user -> {
                binding.detailsSelect.setImageResource(getCheckDrawable(user.getPlaceId(), result.getPlaceId()));
                Drawable top = ResourcesCompat.getDrawable(getResources(), getFavStar(user.getFavorite(), result.getPlaceId()), null);
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

    public static int getFavStar(List<String> list, String placeId) {
        if (list != null && list.contains(placeId)) {
            return R.drawable.ic_star_rate;

        } else {
            return R.drawable.ic_star_outline;
        }
    }

    public static int getCheckDrawable(String userPlace, String curentPlace) {
        if (userPlace != null && userPlace.equals(curentPlace)) {
            return R.drawable.ic_check_circle;
        } else {
            return R.drawable.ic_baseline_check_circle_outline_24;
        }
    }

}

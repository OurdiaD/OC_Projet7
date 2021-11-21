package com.openclassrooms.go4lunch.ui.main.list;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.go4lunch.BuildConfig;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.datas.repositories.PlaceRepository;
import com.openclassrooms.go4lunch.models.maps.Location;
import com.openclassrooms.go4lunch.models.maps.OpeningHours;
import com.openclassrooms.go4lunch.models.maps.Photo;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.ui.details.DetailsActivity;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ListPlaceAdapter extends RecyclerView.Adapter<ListPlaceAdapter.ListPlaceViewHolder> {
    private List<Result> results;
    private Context context;

    public ListPlaceAdapter(){

    }

    @NonNull
    @Override
    public ListPlaceAdapter.ListPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_places, parent, false);
        return new ListPlaceAdapter.ListPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPlaceAdapter.ListPlaceViewHolder holder, int position) {
        Result result = results.get(position);
        holder.placeName.setText(result.getName());
        holder.placeAdress.setText(result.getVicinity());
        holder.placeOpen.setText(getOpeningText(result.getOpening_hours(),holder.placeOpen));
        holder.placeDistance.setText(getDistance(result.getGeometry().getLocation()));

        if (result.getRating() != null ){
            holder.placerating.setRating(result.getRating());
        }

        /*Glide.with(context)
            .load(getPhotoUrl(result.getPhotos()))
            .into(holder.placePic);*/

        int countUser = result.getListUser().size();
        if (countUser > 0) {
            holder.placePerson.setVisibility(View.VISIBLE);
            holder.placePerson.setText(String.valueOf(countUser));
        } else {
            holder.placePerson.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("place_id", result.getPlace_id());
            ActivityCompat.startActivity(context, intent, null);
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public int getOpeningText(OpeningHours openingHour, @Nullable TextView view){
        if (openingHour == null) {
            return R.string.no_data;
        } else if (openingHour.getOpen_now()){
            if (view != null) view.setTextColor(context.getResources().getColor(R.color.teal_200));
            return R.string.open_now;
        } else {
            if (view != null) view.setTextColor(context.getResources().getColor(R.color.orange_dark));
            return R.string.closed;
        }
    }

    public String getPhotoUrl(List<Photo> photos){
        if (photos != null){
            String reference = photos.get(0).getPhoto_reference();
            String apiKey = BuildConfig.API_KEY;
            return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="+reference+"&key="+apiKey;
        }
        return "";
    }

    public String getDistance(Location location){
        android.location.Location point = new android.location.Location("point");
        point.setLatitude(location.getLat());
        point.setLongitude(location.getLng());
        float distance = point.distanceTo(PlaceRepository.getCurrentLocation());
        return String.format("%.0f",distance)+"m";
    }

    static class ListPlaceViewHolder extends RecyclerView.ViewHolder {
        private final TextView placeName;
        private final TextView placeAdress;
        private final TextView placeOpen;
        private final ImageView placePic;
        private final TextView placeDistance;
        private final TextView placePerson;
        private final RatingBar placerating;

        public ListPlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.place_name);
            placeAdress = itemView.findViewById(R.id.place_address);
            placeOpen = itemView.findViewById(R.id.place_open);
            placePic = itemView.findViewById(R.id.place_pic);
            placeDistance = itemView.findViewById(R.id.place_distance);
            placePerson = itemView.findViewById(R.id.place_person);
            placerating = itemView.findViewById(R.id.place_rating);
        }
    }
}

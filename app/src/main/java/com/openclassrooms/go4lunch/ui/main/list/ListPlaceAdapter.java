package com.openclassrooms.go4lunch.ui.main.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.models.User;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.ui.main.workmates.WorkmateAdapter;

import java.util.List;

public class ListPlaceAdapter extends RecyclerView.Adapter<ListPlaceAdapter.ListPlaceViewHolder> {
    private List<Result> results;
    private Context context;

    public ListPlaceAdapter(List<Result> results){
        this.results = results;
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
        if (result.getOpening_hours() != null) {
            holder.placeOpen.setText(String.valueOf(result.getOpening_hours().isOpen_now()));
        } else {
            holder.placeOpen.setText("null");
        }


        /*Glide.with(context)
                .load(result.photos.get(0))
                .circleCrop()
                .into(holder.placePic);*/
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ListPlaceViewHolder extends RecyclerView.ViewHolder {
        private final TextView placeName;
        private final TextView placeAdress;
        private final TextView placeOpen;
        private final ImageView placePic;

        public ListPlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.place_name);
            placeAdress = itemView.findViewById(R.id.place_address);
            placeOpen = itemView.findViewById(R.id.place_open);
            placePic = itemView.findViewById(R.id.place_pic);
        }
    }
}

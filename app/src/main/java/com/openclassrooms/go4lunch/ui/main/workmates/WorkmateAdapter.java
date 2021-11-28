package com.openclassrooms.go4lunch.ui.main.workmates;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.models.User;
import com.openclassrooms.go4lunch.ui.details.DetailsActivity;

import java.util.List;

public class WorkmateAdapter extends RecyclerView.Adapter<WorkmateAdapter.WorkmateViewHolder> {
    private final List<User> users;
    private Context context;

    public WorkmateAdapter(List<User> users){
        this.users = users;
    }

    @NonNull
    @Override
    public WorkmateAdapter.WorkmateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_workmate, parent, false);
        return new WorkmateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmateAdapter.WorkmateViewHolder holder, int position) {
        User user = users.get(position);
        String text = user.getFullname();
        if (user.getPlaceName() != null){
            /*text = text + " " +
                    context.getResources().getString(R.string.eating) + " " +
                    user.getPlaceName();*/
            holder.workmateText.setTypeface(Typeface.DEFAULT_BOLD);
            holder.itemView.setOnClickListener(clickListener(user.getPlaceId()));
        } else {
            //text = text + " " + context.getResources().getString(R.string.no_decided);
            holder.workmateText.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            holder.workmateText.setTextColor(context.getResources().getColor(R.color.grey));
        }
        holder.workmateText.setText(generateWorkmateText(text, user.getPlaceName()));
        Glide.with(context)
                .load(user.getPhotoUrl())
                .circleCrop()
                .into(holder.workmateImage);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public View.OnClickListener clickListener(String placeId){
        return view -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("place_id", placeId);
            ActivityCompat.startActivity(context, intent, null);
        };
    }

    public String generateWorkmateText(String name, String placeName) {
        if (placeName != null){
            return name + " " +
                    context.getResources().getString(R.string.eating) + " " +
                    placeName;
        } else {
            return name + " " + context.getResources().getString(R.string.no_decided);
        }
    }

    static class WorkmateViewHolder extends RecyclerView.ViewHolder {
        private final ImageView workmateImage;
        private final TextView workmateText;

        public WorkmateViewHolder(@NonNull View itemView) {
            super(itemView);
            workmateImage = itemView.findViewById(R.id.workmate_image);
            workmateText = itemView.findViewById(R.id.workmate_text);
        }
    }
}

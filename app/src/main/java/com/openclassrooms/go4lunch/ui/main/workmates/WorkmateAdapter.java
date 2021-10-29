package com.openclassrooms.go4lunch.ui.main.workmates;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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

import java.util.List;

public class WorkmateAdapter extends RecyclerView.Adapter<WorkmateAdapter.WorkmateViewHolder> {
    private List<User> users;
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
        holder.workmateText.setText(user.getFullname());
        Log.d("lol adapter", user.getFullname());
        Glide.with(context)
                .load(user.getPhotoUrl())
                .circleCrop()
                .into(holder.workmateImage);
    }

    @Override
    public int getItemCount() {
        return users.size();
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

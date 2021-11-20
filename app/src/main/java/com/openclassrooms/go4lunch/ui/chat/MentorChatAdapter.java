package com.openclassrooms.go4lunch.ui.chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.datas.repositories.UserRepository;
import com.openclassrooms.go4lunch.models.Message;

public class MentorChatAdapter extends FirestoreRecyclerAdapter<Message, MessageViewHolder> {

    public interface Listener {
        void onDataChanged();
    }

    // VIEW TYPES
    private static final int SENDER_TYPE = 1;
    private static final int RECEIVER_TYPE = 2;

    private final RequestManager glide;

    private Listener callback;

    public MentorChatAdapter(@NonNull FirestoreRecyclerOptions<Message> options, RequestManager glide, Listener callback) {
        super(options);
        this.glide = glide;
        this.callback = callback;
        Log.d("lol adapter", "model.getText()" );
    }

    @Override
    public int getItemViewType(int position) {
        // Determine the type of the message by if the user is the sender or not
        String currentUserId = UserRepository.getInstance().getCurrentUser().getUid();
        boolean isSender = getItem(position).getUser().getUserId().equals(currentUserId);

        return (isSender) ? SENDER_TYPE : RECEIVER_TYPE;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message model) {
        holder.itemView.invalidate();
        holder.updateWithMessage(model, this.glide);
        Log.d("lol adapter", model.getText() );
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("lol adapter", "onCreateViewHolder" );
        return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false), viewType == 1);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }
}

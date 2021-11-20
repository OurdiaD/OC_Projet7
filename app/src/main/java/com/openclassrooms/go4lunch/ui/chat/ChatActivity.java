package com.openclassrooms.go4lunch.ui.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.openclassrooms.go4lunch.databinding.ActivityChatBinding;
import com.openclassrooms.go4lunch.models.Message;

public class ChatActivity extends AppCompatActivity  implements ChatAdapter.Listener{
    private ChatAdapter chatAdapter;
    //private String currentChatName;
    private ActivityChatBinding binding;
    private final ChatViewModel chatViewModel = ChatViewModel.getInstance();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        configureRecyclerView();
        binding.sendButton.setOnClickListener(view -> sendMessage());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // Configure RecyclerView
    private void configureRecyclerView(){
        //Configure Adapter & RecyclerView
        this.chatAdapter = new ChatAdapter(
                generateOptionsForAdapter(chatViewModel.getAllMessageForChat()),
                Glide.with(this), this);

        chatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                binding.chatRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });

        binding.chatRecyclerView.setAdapter(this.chatAdapter);
        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Create options for RecyclerView from a Query
    private FirestoreRecyclerOptions<Message> generateOptionsForAdapter(Query query){
        return new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setLifecycleOwner(this)
                .build();
    }

    public void onDataChanged() {
        // Show TextView in case RecyclerView is empty
        binding.emptyRecyclerView.setVisibility(this.chatAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private void sendMessage(){
        // Check if user can send a message (Text not null + user logged)
        boolean canSendMessage = !TextUtils.isEmpty(binding.chatEditText.getText());

        if (canSendMessage){
            // Create a new message for the chat
            chatViewModel.createMessageForChat(binding.chatEditText.getText().toString());
            // Reset text field
            binding.chatEditText.setText("");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

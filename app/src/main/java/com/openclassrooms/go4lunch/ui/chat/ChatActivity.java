package com.openclassrooms.go4lunch.ui.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.openclassrooms.go4lunch.databinding.ActivityChatBinding;
import com.openclassrooms.go4lunch.datas.repositories.UserRepository;
import com.openclassrooms.go4lunch.models.Message;

public class ChatActivity extends AppCompatActivity  implements MentorChatAdapter.Listener{
    private MentorChatAdapter mentorChatAdapter;
    //private String currentChatName;
    private ActivityChatBinding binding;
    private UserRepository userRepository = UserRepository.getInstance();
    private ChatViewModel chatViewModel = ChatViewModel.getInstance();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        configureRecyclerView();
        //setupListeners();
        binding.sendButton.setOnClickListener(view -> { sendMessage(); });
    }

/*    private void setupListeners(){

        // Chat buttons
        binding.androidChatButton.setOnClickListener(view -> { this.configureRecyclerView(CHAT_NAME_ANDROID); });
        binding.firebaseChatButton.setOnClickListener(view -> { this.configureRecyclerView(CHAT_NAME_FIREBASE); });
        binding.bugChatButton.setOnClickListener(view -> { this.configureRecyclerView(CHAT_NAME_BUG); });
    }*/

    // Configure RecyclerView
    private void configureRecyclerView(){
        //Track current chat name
        //this.currentChatName = chatName;
        //Configure Adapter & RecyclerView
        this.mentorChatAdapter = new MentorChatAdapter(
                generateOptionsForAdapter(chatViewModel.getAllMessageForChat()),
                Glide.with(this), this);

        mentorChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                binding.chatRecyclerView.smoothScrollToPosition(mentorChatAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });

        binding.chatRecyclerView.setAdapter(this.mentorChatAdapter);
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
        binding.emptyRecyclerView.setVisibility(this.mentorChatAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
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
}

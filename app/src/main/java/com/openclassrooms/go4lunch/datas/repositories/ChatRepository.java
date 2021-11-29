package com.openclassrooms.go4lunch.datas.repositories;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.openclassrooms.go4lunch.models.Message;
import com.openclassrooms.go4lunch.models.User;

public class ChatRepository {
    private static final String CHAT_COLLECTION = "chats";
    private static volatile ChatRepository instance;
    private final UserRepository userRepository;

    private ChatRepository() { this.userRepository = UserRepository.getInstance(); }

    public static ChatRepository getInstance() {
        ChatRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(ChatRepository.class) {
            if (instance == null) {
                instance = new ChatRepository();
            }
            return instance;
        }
    }

    public CollectionReference getChatCollection(){
        return FirebaseFirestore.getInstance().collection(CHAT_COLLECTION);
    }

    public Query getAllMessageForChat(){
        return this.getChatCollection()
                .orderBy("dateCreated")
                .limit(50);
    }

    public void createMessageForChat(String textMessage){

        userRepository.getUserData().addOnSuccessListener(task -> {
            // Create the Message object
            User user = task.toObject(User.class);
            Message message = new Message(textMessage, user);

            // Store Message to Firestore
            this.getChatCollection()
                    .add(message);
        });

    }
}

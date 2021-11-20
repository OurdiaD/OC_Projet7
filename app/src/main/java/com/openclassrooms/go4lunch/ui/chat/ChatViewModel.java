package com.openclassrooms.go4lunch.ui.chat;

import com.google.firebase.firestore.Query;
import com.openclassrooms.go4lunch.datas.repositories.ChatRepository;

public class ChatViewModel {
    private static volatile ChatViewModel instance;
    private ChatRepository chatRepository;

    private ChatViewModel() {
        chatRepository = ChatRepository.getInstance();
    }

    public static ChatViewModel getInstance() {
        ChatViewModel result = instance;
        if (result != null) {
            return result;
        }
        synchronized(ChatViewModel.class) {
            if (instance == null) {
                instance = new ChatViewModel();
            }
            return instance;
        }
    }

    public Query getAllMessageForChat(){
        return chatRepository.getAllMessageForChat();
    }

    public void createMessageForChat(String message){
        chatRepository.createMessageForChat(message);
    }
}

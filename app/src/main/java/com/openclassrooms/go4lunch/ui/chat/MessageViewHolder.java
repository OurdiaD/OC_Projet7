package com.openclassrooms.go4lunch.ui.chat;

import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.databinding.ItemChatBinding;
import com.openclassrooms.go4lunch.models.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    private final ItemChatBinding binding;

    private final int colorCurrentUser;
    private final int colorRemoteUser;

    private final boolean isSender;

    public MessageViewHolder(@NonNull View itemView, boolean isSender) {
        super(itemView);
        this.isSender = isSender;
        binding = ItemChatBinding.bind(itemView);
        // Setup default colros
        colorCurrentUser = ContextCompat.getColor(itemView.getContext(), R.color.grey);
        colorRemoteUser = ContextCompat.getColor(itemView.getContext(), R.color.orange);
    }

    public void updateWithMessage(Message message, RequestManager glide){
        // Update message
        binding.messageTextView.setText(message.getText());
        binding.messageTextView.setTextAlignment(isSender ? View.TEXT_ALIGNMENT_TEXT_END : View.TEXT_ALIGNMENT_TEXT_START);

        // Update date
        if (message.getDateCreated() != null) binding.dateTextView.setText(convertDateToHour(message.getDateCreated()));

        // Update profile picture
        if (message.getUser().getPhotoUrl() != null)
            glide.load(message.getUser().getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.profileImage);

        updateLayoutFromSenderType();
    }

    private void updateLayoutFromSenderType(){
        ((GradientDrawable) binding.messageTextContainer.getBackground()).setColor(isSender ? colorCurrentUser : colorRemoteUser);
        binding.messageTextContainer.requestLayout();

        if(!isSender){
            updateProfileContainer();
            updateMessageContainer();
        }
    }

    private void updateProfileContainer(){
        // Update the constraint for the profile container (Push it to the left for receiver message)
        ConstraintLayout.LayoutParams profileContainerLayoutParams = (ConstraintLayout.LayoutParams) binding.profileContainer.getLayoutParams();
        profileContainerLayoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
        profileContainerLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        binding.profileContainer.requestLayout();
    }

    private void updateMessageContainer(){
        // Update the constraint for the message container (Push it to the right of the profile container for receiver message)
        ConstraintLayout.LayoutParams messageContainerLayoutParams = (ConstraintLayout.LayoutParams) binding.messageContainer.getLayoutParams();
        messageContainerLayoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
        messageContainerLayoutParams.endToStart = ConstraintLayout.LayoutParams.UNSET;
        messageContainerLayoutParams.startToEnd = binding.profileContainer.getId();
        messageContainerLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        messageContainerLayoutParams.horizontalBias = 0.0f;
        binding.messageContainer.requestLayout();

        // Update the constraint (gravity) for the text of the message (content + date) (Align it to the left for receiver message)
        LinearLayout.LayoutParams messageTextLayoutParams = (LinearLayout.LayoutParams) binding.messageTextContainer.getLayoutParams();
        messageTextLayoutParams.gravity = Gravity.START;
        binding.messageTextContainer.requestLayout();

        LinearLayout.LayoutParams dateLayoutParams = (LinearLayout.LayoutParams) binding.dateTextView.getLayoutParams();
        dateLayoutParams.gravity = Gravity.BOTTOM | Gravity.START;
        binding.dateTextView.requestLayout();

    }

    public static String convertDateToHour(Date date){
        DateFormat dfTime = new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault());
        return dfTime.format(date);
    }

}

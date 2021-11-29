package com.openclassrooms.go4lunch;

import static org.junit.Assert.assertEquals;

import com.openclassrooms.go4lunch.ui.chat.ChatAdapter;
import com.openclassrooms.go4lunch.ui.chat.MessageViewHolder;
import com.openclassrooms.go4lunch.ui.details.DetailsActivity;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UnitTest {

    @Test
    public void convertDate() {
        Date date = new Date();
        DateFormat dfTime = new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault());

        String value = MessageViewHolder.convertDateToHour(date);
        assertEquals(dfTime.format(date),value);
    }

    @Test
    public void isSenderTest() {
        int SENDER_TYPE = 1;
        int RECEIVER_TYPE = 2;
        String currentId = "b4t9mqJ9qLa16By0dbASi2SB1573";
        String otherId = "zMTwGeATNXbZvTznr6yMEBYsXq43";
        int value = ChatAdapter.isSender(currentId, otherId);
        assertEquals(RECEIVER_TYPE, value);
        value = ChatAdapter.isSender(currentId, currentId);
        assertEquals(SENDER_TYPE, value);
    }

    @Test
    public void getFavStarTest() {
        String currentId = "ChIJx4aD_xVu5kcR7o_FvMBQLFI";
        List<String> listId = new ArrayList<>();
        listId.add("ChIJ14WxUjlu5kcRuOfRbh-YtLI");
        listId.add("ChIJQfGO_T1u5kcR0eEskdBesJM");
        int value = DetailsActivity.getFavStar(listId, currentId);
        assertEquals(R.drawable.ic_star_outline, value);

        listId.add(currentId);
        value = DetailsActivity.getFavStar(listId, currentId);
        assertEquals(R.drawable.ic_star_rate, value);
    }

    @Test
    public void getCheckTest() {
        String currentId = "ChIJx4aD_xVu5kcR7o_FvMBQLFI";
        String otherId = "ChIJ14WxUjlu5kcRuOfRbh-YtLI";
        int value = DetailsActivity.getCheckDrawable(currentId,otherId);
        assertEquals(R.drawable.ic_baseline_check_circle_outline_24, value);
        value = DetailsActivity.getCheckDrawable(currentId,currentId);
        assertEquals(R.drawable.ic_check_circle, value);
    }


}

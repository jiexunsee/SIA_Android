package com.rep5.sialah.sia;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DropDownMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down_menu);
    }

    public void GoBack(View view) {
        this.onBackPressed();
    }

    public void ShowTicket(View view) {
        Intent intent = new Intent (this, Ticket.class);
        startActivity(intent);
    }

    public void ContactCustServ(View view) {
        StaticClass.SendMessageHistory();
        onBackPressed();

        View messageReplyView = getLayoutInflater().inflate(R.layout.message_receive_view, null, false);
        TextView messageTextView = (TextView) messageReplyView.findViewById(R.id.receive_text_view);
        messageTextView.setText("I will now contact a service operator to take over. Please hold for a moment while I patch you through.");

        ViewGroup chatbubbles = (ViewGroup) ChatBot.getChatBotInstance().findViewById(R.id.conversation);
        chatbubbles.addView(messageReplyView);

        final ScrollView scroll = (ScrollView) ChatBot.getChatBotInstance().findViewById(R.id.scrollView);
        scroll.post(new Runnable()
        {
            public void run()
            {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

}

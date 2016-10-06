package com.rep5.sialah.sia;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
        ImageView title = (ImageView) ChatBot.getChatBotInstance().findViewById(R.id.convoTitle);
        title.setImageResource(R.drawable.customerservice);
        LinearLayout topBar = (LinearLayout) ChatBot.getChatBotInstance().findViewById(R.id.topBar);
        topBar.setBackgroundColor(Color.parseColor("#fdb813"));
        EditText editText = (EditText) ChatBot.getChatBotInstance().findViewById(R.id.messageEditText);
        editText.setHint("Talk to Customer Service");
    }

    public void Shop(View view) {
        onBackPressed();
        ImageView title = (ImageView) ChatBot.getChatBotInstance().findViewById(R.id.convoTitle);
        title.setImageResource(R.drawable.title3);
        LinearLayout topBar = (LinearLayout) ChatBot.getChatBotInstance().findViewById(R.id.topBar);
        topBar.setBackgroundColor(Color.parseColor("#e6e0d0"));
        EditText editText = (EditText) ChatBot.getChatBotInstance().findViewById(R.id.messageEditText);
        editText.setHint("Type a message to Sia");
    }
}

package com.rep5.sialah.sia;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import javax.ws.rs.core.MediaType;

public class FriendConversation extends AppCompatActivity {

    private static FriendConversation friendInstance;
    private ImageButton sendButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_conversation);

        friendInstance = this;


        editText = (EditText) findViewById(R.id.editText);
        sendButton = (ImageButton) findViewById(R.id.send);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                    sendButton.setImageResource(R.drawable.plane);
                } else {
                    sendButton.setEnabled(false);
                    sendButton.setImageResource(R.drawable.buttondefault);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = editText.getText().toString();
                editText.setText("");

                TextView bubble = new TextView(getApplicationContext());
                bubble.setTextSize(18);
                bubble.setText(text);
                bubble.setTextColor(Color.parseColor("#000000"));
                bubble.setBackgroundResource(R.drawable.sendbubble);
                LinearLayout.LayoutParams bubblelayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                bubblelayout.setMargins(200, 6, 6, 6);
                bubblelayout.gravity = Gravity.RIGHT;
                bubble.setLayoutParams(bubblelayout);

                ViewGroup chatbubbles = (ViewGroup) findViewById(R.id.friendConversation);
                chatbubbles.addView(bubble);

                Log.d(text, text);


                // send message to heok hong
                SendText send = new SendText();
                send.setText("{\"token\":\"" + FirebaseInstanceId.getInstance().getToken() +
                        "\",\"message\":\"" + text + "\"}");

                Log.d("json", send.getText());
                send.setRequestType(MediaType.APPLICATION_JSON);
                send.setUrl("http://lhhong.asuscomm.com:8080/sia/messages/plane_chat");
                Thread thread = new Thread(send);
                thread.start();

                final ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
                scroll.post(new Runnable() {
                    public void run() {
                        scroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
    }


    public void addReceivedMessage(SiaMessage siaMessage) {
        String message = siaMessage.getMessage();

        TextView reply = new TextView(this);
        reply.setTextSize(18);
        reply.setText(message);
        reply.setTextColor(Color.parseColor("#000000"));
        reply.setBackgroundResource(R.drawable.receivebubble);
        LinearLayout.LayoutParams replylayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        replylayout.setMargins(6, 6, 200, 6);
        replylayout.gravity = Gravity.LEFT;
        reply.setLayoutParams(replylayout);
        ViewGroup chatbubbles = (ViewGroup) findViewById(R.id.friendConversation);
        chatbubbles.addView(reply);

        reply.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageOptions.class);
                startActivity(intent);
                return true;
            }
        });


        reply.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageOptions.class);
                startActivity(intent);
                return true;
            }
        });


        final ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.post(new Runnable()
        {
            public void run()
            {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });

    }

    public void MustBuyWifi(View view) {
        Toast.makeText(friendInstance, "To send attachments, please purchase the in-flight Wifi.", Toast.LENGTH_LONG).show();
    }

    public void GoBack(View view) {
        onBackPressed();
    }

    public static FriendConversation getInstance() {
        return friendInstance;
    }

}

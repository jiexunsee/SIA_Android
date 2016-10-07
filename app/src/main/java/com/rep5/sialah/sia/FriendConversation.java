package com.rep5.sialah.sia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import javax.ws.rs.core.MediaType;

public class FriendConversation extends AppCompatActivity {

    private static FriendConversation friendInstance;
    private ImageButton sendButton;
    private EditText editText;
    public static String lastMessage;
    public static String lastSender;

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
                    sendButton.setImageResource(R.drawable.sendbutton);
                } else {
                    sendButton.setEnabled(false);
                    sendButton.setImageResource(R.drawable.sendbutton_faded);
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

                View messageSendView = getLayoutInflater().inflate(R.layout.message_send_view, null, false);
                TextView textView = (TextView)messageSendView.findViewById(R.id.send_text_view);
                textView.setText(text);

                ViewGroup chatbubbles = (ViewGroup) findViewById(R.id.friendConversation);
                chatbubbles.addView(messageSendView);

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

                lastMessage = text;
                lastSender = "You";
            }
        });
    }


    public void addReceivedMessage(SiaMessage siaMessage) {
        String message = siaMessage.getMessage();

        View messageReplyView = getLayoutInflater().inflate(R.layout.message_receive_view, null, false);
        TextView messageTextView = (TextView) messageReplyView.findViewById(R.id.receive_text_view);
        ImageView pic = (ImageView) messageReplyView.findViewById(R.id.senderLogo);
        pic.setImageResource(R.drawable.mum_face);
        messageTextView.setText(message);

        ViewGroup chatbubbles = (ViewGroup) findViewById(R.id.friendConversation);
        chatbubbles.addView(messageReplyView);

        messageReplyView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageOptions.class);
                startActivity(intent);
                return true;
            }
        });


        messageReplyView.setOnLongClickListener(new View.OnLongClickListener() {
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

        lastMessage = message;
        lastSender = "Mom";

    }

    public void MustBuyWifi(View view) {
        if (!ChatBot.wifiState) Toast.makeText(friendInstance, "To send attachments, please purchase the in-flight Wifi.", Toast.LENGTH_LONG).show();
    }

    public void GoBack(View view) {
        onBackPressed();
    }

    public static FriendConversation getInstance() {
        return friendInstance;
    }

}

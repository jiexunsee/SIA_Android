/**
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rep5.sialah.sia;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.rep5.sialah.sia.ChatBot.MESSAGES_CHILD;
import static com.rep5.sialah.sia.ChatBot.mFirebaseDatabaseReference;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFMService";
    private FirebaseRecyclerAdapter<FriendlyMessage, ChatBot.MessageViewHolder> mFirebaseAdapterreceive;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //getting content of message from Heok Hong
        Context context = JSON.parseObject(remoteMessage.getData().get("context"), Context.class);
        SiaMessage msg = new SiaMessage();
        msg.setContext(context);
        msg.setMessage(remoteMessage.getData().get("message"));

        boolean booking;
        booking = context.getSiaData().getFakeBooking();


//        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
//
//
//        FriendlyMessage friendlyMessage = new
//                FriendlyMessage(msg.getMessage(),
//                "SIA",
//                "@drawable/sialogo");
//
//        mFirebaseDatabaseReference.child(MESSAGES_CHILD)
//                .push().setValue(friendlyMessage);


//        LinearLayout messagebubble = (LinearLayout) getLayoutInflater().inflate(R.layout.item_message, null);
//        ScrollView conversation = (ScrollView) findViewById(R.id.conversation);
//        conversation.addView(messagebubble);
//        TextView messagetext = (TextView) findViewById(R.id.messageTextView);
//        messagetext.setText(msg.getMessage());
//        TextView messagesender = (TextView) findViewById(R.id.messengerTextView);
//        messagesender.setText("SIA");

        GetReceivedMessage get = new GetReceivedMessage();
        get.setSiaMessage(msg);
        ChatBot.getChatBotInstance().runOnUiThread(get);

        if (booking) {

        }



        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "FCM Notification Message: " +
                remoteMessage.getNotification());
        Log.d(TAG, "FCM Data Message: " + remoteMessage.getData());
    }
}

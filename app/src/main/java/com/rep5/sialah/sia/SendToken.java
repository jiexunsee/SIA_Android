package com.rep5.sialah.sia;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by jiexun on 5/10/16.
 */

public class SendToken {
    public static void send() {
        SendText send = new SendText();
        //send.setText(FirebaseInstanceId.getInstance().getToken());
        send.setText("JX-" + FirebaseInstanceId.getInstance().getToken());
        Thread t = new Thread(send);
        t.start();
    }
}

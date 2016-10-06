package com.rep5.sialah.sia;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.LinkedList;

import javax.ws.rs.core.MediaType;

/**
 * Created by jiexun on 5/10/16.
 */

public class StaticClass {
    public static LinkedList<ChatBotMessage> messageHistory = new LinkedList<ChatBotMessage>();

    public static void SendMessageHistory() {
        String history = JSON.toJSONString(messageHistory);
        SendText send = new SendText();
        send.setText(history);
        send.setRequestType(MediaType.APPLICATION_JSON);
        send.setUrl("http://lhhong.asuscomm.com:8080/sia/signals/cus_service");
        Thread thread = new Thread(send);
        thread.start();
        Log.d("SENT", history);
    }




}

package com.rep5.sialah.sia;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jiexun on 3/10/16.
 */

public class SendText implements Runnable {

    private String text;
    private String requestType = MediaType.TEXT_PLAIN;
    private String url = "http://lhhong.asuscomm.com:8080/sia/messages/fcm_id";

    public void setRequestType(String type) {
        requestType = type;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void run() {

        WebTarget webTarget = RestClient.getTarget(url);
        Response postResponse =
                webTarget.request()
                        .post(Entity.entity(text, requestType));

        Log.d("status", Integer.toString(postResponse.getStatus()));

    }

    public void setUrl(String url) {
        this.url = url;
    }
}

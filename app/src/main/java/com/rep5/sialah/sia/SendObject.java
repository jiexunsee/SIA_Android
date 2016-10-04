package com.rep5.sialah.sia;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class SendObject implements Runnable {
    public SiaMessage siaMessage;

    public void setSiaMessage(SiaMessage siaMessage) {
        this.siaMessage = siaMessage;
    }

    @Override
    public void run() {

        WebTarget webTarget = RestClient.getTarget("http://lhhong.asuscomm.com:8080/sia/messages/");
        Response postResponse =
                webTarget.request()
                        .post(Entity.entity(JSON.toJSONString(siaMessage), MediaType.APPLICATION_JSON));
        //SiaMessage msg = JSON.parseObject("{mess", SiaMessage.class);

    }
}

package com.rep5.sialah.sia;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;


public class RestClient {

    public static Client client;

    public static WebTarget getTarget(String url) {
        client = ClientBuilder.newBuilder().build();

        return client.target(url);
    }

}

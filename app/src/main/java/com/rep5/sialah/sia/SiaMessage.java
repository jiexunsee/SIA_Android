package com.rep5.sialah.sia;

import java.io.Serializable;

public class SiaMessage implements Serializable {

    private long id;
    private String message;
    private Context context;

    public SiaMessage() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
package com.rep5.sialah.sia;

import java.util.Map;

/**
 * Created by low on 2/10/16 10:39 PM.
 * To be used by watson conversation
 */

public class Context {
    private String conversation_id;
    private Map<String, Object> system;
    private SiaData siaData;

    public Context() {
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public Map<String, Object> getSystem() {
        return system;
    }

    public void setSystem(Map<String, Object> system) {
        this.system = system;
    }

    public SiaData getSiaData() {
        return siaData;
    }

    public void setSiaData(SiaData siaData) {
        this.siaData = siaData;
    }
}
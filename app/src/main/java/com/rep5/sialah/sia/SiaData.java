package com.rep5.sialah.sia;

/**
 * Created by low on 1/10/16 5:45 AM.
 */

public class SiaData {

    //tell jx that watson recognised booking intent, start to fake the convo
    //to be set in watson convo
    private boolean fakeBooking;

    //tell jiarui seat number of the person who requested seat
//    private String seatNumber;

    //tell jiarui what the customer request type, ie, order snack or buy stuff
    //also to signal the next message will be pushed to jr
    //to be set in watson
    private String customerRequestType;
    private String customerRequestItem;

    //stores previous convo for customer service, jx to set this value
    //store from earliest to latest
    //clear after 1st contact to reduce payload
    //prepend each string with source of msg, use "usr-Whatever message" and "bot-Whatever message"
//    private String[] pastFiveConvo;

    //to be set by jx
//    private boolean talkingToCustomerService;
    private Boolean needsCustomerService;

    private Boolean planeChat;

    private Boolean confirmingCheckIn;
    //for watson use
    private boolean inFlight;

    public SiaData() {
    }

    public Boolean getNeedsCustomerService() {
        return needsCustomerService;
    }

    public void setNeedsCustomerService(Boolean needsCustomerService) {
        this.needsCustomerService = needsCustomerService;
    }

    public Boolean getConfirmingCheckIn() {
        return confirmingCheckIn;
    }

    public void setConfirmingCheckIn(Boolean confirmingCheckIn) {
        this.confirmingCheckIn = confirmingCheckIn;
    }

    public Boolean getPlaneChat() {
        return planeChat;
    }

    public void setPlaneChat(Boolean planeChat) {
        this.planeChat = planeChat;
    }

    public String getCustomerRequestType() {
        return customerRequestType;
    }

    public void setCustomerRequestType(String customerRequestType) {
        this.customerRequestType = customerRequestType;
    }

    public String getCustomerRequestItem() {
        return customerRequestItem;
    }

    public void setCustomerRequestItem(String customerRequestItem) {
        this.customerRequestItem = customerRequestItem;
    }

    public boolean getFakeBooking() {
        return fakeBooking;
    }

    public void setFakeBooking(boolean fakeBooking) {
        this.fakeBooking = fakeBooking;
    }
    /*
        public String getSeatNumber() {
            return seatNumber;
        }
        public void setSeatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
        }
        public String getCustomerRequestType() {
            return customerRequestType;
        }
        public void setCustomerRequestType(String customerRequestType) {
            this.customerRequestType = customerRequestType;
        }
        public String[] getPastFiveConvo() {
            return pastFiveConvo;
        }
        public void setPastFiveConvo(String[] pastFiveConvo) {
            this.pastFiveConvo = pastFiveConvo;
        }
        public boolean getTalkingToCustomerService() {
            return talkingToCustomerService;
        }
        public void setTalkingToCustomerService(boolean talkingToCustomerService) {
            this.talkingToCustomerService = talkingToCustomerService;
        }
    */
    public boolean getInFlight() {
        return inFlight;
    }

    public void setInFlight(boolean inFlight) {
        this.inFlight = inFlight;
    }
}
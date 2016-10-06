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

    //to be set by jx
//    private boolean talkingToCustomerService;
    private Boolean needsCustomerService;
    private Boolean isCusService;

    private Boolean planeChat;

    private Boolean confirmingCheckIn;
    //for watson use
    private boolean inFlight;

    public SiaData() {
    }

    public Boolean getIsCusService() {
        return isCusService;
    }

    public void setIsCusService(Boolean cusService) {
        isCusService = cusService;
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

    public boolean getInFlight() {
        return inFlight;
    }

    public void setInFlight(boolean inFlight) {
        this.inFlight = inFlight;
    }
}
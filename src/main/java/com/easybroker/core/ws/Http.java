package com.easybroker.core.ws;

public interface Http {

    public <T> T doGet(WSRequest wsRequest);
}

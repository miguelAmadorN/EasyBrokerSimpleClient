package com.easybroker.properties;

import com.easybroker.core.ws.operations.BrokerConfiguration;

public class BrokerConfigurationTest extends BrokerConfiguration {
    public static String AUTH_VALUE = "l7u502p8v46ba3ppgvj5y2aad50lb9";
    public static String URL = "https://api.stagingeb.com";
    public static int MAX_RESULT_PER_PAGE = 50;
    public static long DELAY_REQUEST_MILLIS = 10;

    public BrokerConfigurationTest() {
        super(URL, AUTH_VALUE);
        setMaxResultPerPage(MAX_RESULT_PER_PAGE);
        setDelayRequestMillis(DELAY_REQUEST_MILLIS);
    }
}

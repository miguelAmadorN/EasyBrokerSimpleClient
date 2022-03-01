package com.easybroker.core.ws.operations;

import com.easybroker.core.exceptions.BrokerConfigurationException;

public class BrokerConfiguration {
    private String url;
    private String authValue;
    private int maxResultPerPage = 50;
    private long delayRequestMillis = 10;

    public BrokerConfiguration(String url, String authValue){
        if(url == null) throw new BrokerConfigurationException("url can not be null");
        if(authValue == null) throw new BrokerConfigurationException("authValue can not be null");
        this.url = url;
        this.authValue = authValue;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthValue() {
        return authValue;
    }

    public int getMaxResultPerPage() {
        return maxResultPerPage;
    }

    public BrokerConfiguration setMaxResultPerPage(int maxResultPerPage) {
        if(maxResultPerPage < 1 || maxResultPerPage > 50){
            throw new BrokerConfigurationException("maxResultPerPage can not be less than 1 or greater than 50");
        }
        this.maxResultPerPage = maxResultPerPage;
        return this;
    }

    public BrokerConfiguration setDelayRequestMillis(long delayRequestMillis){
        if(delayRequestMillis < 1){
            throw new BrokerConfigurationException("delayRequestMillis can not be less than 1");
        }
        this.delayRequestMillis = delayRequestMillis;
        return this;
    }

    public long getDelayRequestMillis() {
        return delayRequestMillis;
    }
}

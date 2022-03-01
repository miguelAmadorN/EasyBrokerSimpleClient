package com.easybroker.core.ws;

import java.util.Map;

public class WSRequest {
    private String url;
    private Map<String, String> httpHeaders;
    private Map<String, Object> queryParams;
    private String path;

    public WSRequest(String url) {
        this.url = url;
    }

    public WSRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public WSRequest setHttpHeaders(Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public WSRequest setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public WSRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }
}

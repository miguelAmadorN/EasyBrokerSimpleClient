package com.easybroker.core.ws.operations;

import com.easybroker.core.ws.Http;
import com.easybroker.core.ws.WSRequest;
import com.easybroker.external.ws.SpringWebClient;
import com.jayway.jsonpath.JsonPath;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class EasyBrokerBaseOperation {

    protected static Http http = new SpringWebClient();
    protected BrokerConfiguration configuration;

    public int getTotalProperties(String path){
        WSRequest wsRequest =  new WSRequest(configuration.getUrl()).setPath(path).setHttpHeaders(getAuthHeaders());
        Mono<String> requestForGetAmounts = http.doGet(wsRequest);
        String response = requestForGetAmounts.block();
        Integer total = JsonPath.read(response, "$.pagination.total");
        return total == null ? 0 : total;
    }

    protected Map<String, String> getAuthHeaders(){
        Map<String, String> auth = new HashMap<>();
        auth.put("X-Authorization", configuration.getAuthValue());
        return auth;
    }

    protected Map<String, Object> getBaseQueryParams(Integer page, Integer limit){
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", page);
        queryParams.put("limit", limit);
        return queryParams;
    }
}

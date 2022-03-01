package com.easybroker.core.ws.operations;

import com.easybroker.core.exceptions.EasyBrokerIntegrityDataException;
import com.easybroker.core.ws.WSRequest;
import com.jayway.jsonpath.JsonPath;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;

public class EasyBrokerPropertiesRequest extends EasyBrokerBaseOperation{

    public static final String pathProperties = "v1/properties";

    public EasyBrokerPropertiesRequest(BrokerConfiguration configuration){
        this.configuration = configuration;
    }

    public List<String> getAllTitleProperties() throws EasyBrokerIntegrityDataException {
        int totalProperties = getTotalProperties(pathProperties);

        int maxRequest = totalProperties / configuration.getMaxResultPerPage();
        if(totalProperties % configuration.getMaxResultPerPage() > 0) maxRequest++;

        List<String> titles = getTitleProperties(maxRequest);
        if(totalProperties != titles.size()){
            throw new EasyBrokerIntegrityDataException("Title properties has integrity problems");
        }

        return titles;
    }

    private List<String> getTitleProperties(int totalPages) {
        List<String> titles = new ArrayList<>();
        if(totalPages < 1) return titles;

        SortedMap<Integer, List<String>> orderedPropertyTitles = new TreeMap<>();
        Flux.range(1, totalPages)
                .delayElements(Duration.ofMillis(configuration.getDelayRequestMillis()))
                .map(this::getPathPropertiesRequest)
                .flatMap(http::doGet)
                .map(loadPropertyTitles(orderedPropertyTitles))
                .blockLast()
                ;

        orderedPropertyTitles.values().stream().forEach(titles::addAll);
        return titles;
    }

    private Function<Object, Integer> loadPropertyTitles(Map<Integer, List<String>> orderedPropertyTitles) {
        final int SUCCESS = 1;
        return response -> {
            Integer page = JsonPath.read((String) response, "$.pagination.page");
            List<String> titleProperties = JsonPath.read((String) response, "$.content[*].title");
            orderedPropertyTitles.put(page, titleProperties);
            return SUCCESS;
        };
    }

    private WSRequest getPathPropertiesRequest(Integer page){
        return new WSRequest(configuration.getUrl())
                    .setPath(pathProperties)
                    .setHttpHeaders(getAuthHeaders())
                    .setQueryParams(getBaseQueryParams(page, configuration.getMaxResultPerPage()));
    }

}

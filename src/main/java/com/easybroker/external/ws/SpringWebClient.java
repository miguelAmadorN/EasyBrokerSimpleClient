package com.easybroker.external.ws;


import com.easybroker.core.ws.Http;
import com.easybroker.core.ws.WSRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.function.Consumer;

public class SpringWebClient implements Http {

    private ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                                                                    .codecs(getClientCodecConfigurerConsumer())
                                                                    .build();

    private Consumer<ClientCodecConfigurer> getClientCodecConfigurerConsumer() {
        final int NO_LIMIT = -1;
        return configurer -> configurer.defaultCodecs()
                                        .maxInMemorySize(NO_LIMIT);
    }

    @Override
    public  Mono<String> doGet(WSRequest wsRequest) {
        WebClient webClient = getConfiguredWebClient(wsRequest);
        return executeWebClientRequest(webClient, wsRequest);
    }

    private WebClient getConfiguredWebClient(WSRequest wsRequest){
        return WebClient.builder()
                        .exchangeStrategies(exchangeStrategies)
                        .baseUrl(wsRequest.getUrl())
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build();
    }

    private Mono<String> executeWebClientRequest(WebClient webClient, WSRequest wsRequest){
        return webClient.get()
                .uri(uriBuilder -> buildUri(uriBuilder, wsRequest))
                .headers(httpHeaders -> loadHeaders(wsRequest.getHttpHeaders(), httpHeaders))
                .retrieve()
                .bodyToMono(String.class);
    }

    private URI buildUri(UriBuilder uriBuilder, WSRequest wsRequest){
        uriBuilder.path(wsRequest.getPath());
        loadQueryParams(wsRequest.getQueryParams(), uriBuilder);
        return uriBuilder.build();
    }

    private void loadHeaders(Map<String, String> headers, HttpHeaders httpHeaders){
        if(headers == null) return;
        for(Map.Entry<String, String> entry: headers.entrySet()){
            httpHeaders.add(entry.getKey(), entry.getValue());
        }
    }

    private void loadQueryParams(Map<String, Object> queryParams, UriBuilder uriBuilder){
        if(queryParams == null) return;
        for(Map.Entry<String, Object> entry : queryParams.entrySet()){
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }
    }
}

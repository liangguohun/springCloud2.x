package com.example.demo;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestFetcher {
    /**
     * Fetches the product data that is being received via REST from another service.
     *
     * @param providerUri the URI of the provider, on which the data is returned.
     * @return a Product object with the fetched data.
     */
    public <T> T fetchInfo(URI providerUri, Class T) {
        RestTemplate restTemplate = new RestTemplate();
        return (T) restTemplate.getForObject(providerUri, T);
    }
}

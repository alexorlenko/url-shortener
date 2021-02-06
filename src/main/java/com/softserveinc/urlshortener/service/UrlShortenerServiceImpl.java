package com.softserveinc.urlshortener.service;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    final static Map<String, String> database = new HashMap<>();
    final static String BASE_URL = "http://localhost:8080";

    @Override
    public URI getUrl(String key) throws URISyntaxException {
        return new URI(database.get(key));
    }

    @Override
    public URI saveUrl(String url) throws URISyntaxException {
        String key = UUID.randomUUID().toString().substring(0, 4);
        database.put(key, url);
        return new URI(String.format("%s/%s", BASE_URL, key));
    }
}

package com.softserveinc.urlshortener.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public interface UrlShortenerService {

    URI find(String key) throws MalformedURLException, URISyntaxException;

    URI create(String url) throws MalformedURLException, URISyntaxException;

    void delete(String key);
}

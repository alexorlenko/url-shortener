package com.softserveinc.urlshortener.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public interface UrlShortenerService {

    public URI getUrl(String key) throws MalformedURLException, URISyntaxException;
    public URI saveUrl(String url) throws MalformedURLException, URISyntaxException;
}

package com.softserveinc.urlshortener.controller;

import com.softserveinc.urlshortener.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@RestController
public class UrlShortenerController {

    UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> getUrl(@PathVariable String key) throws URISyntaxException, MalformedURLException {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(urlShortenerService.getUrl(key)).build();
    }

    @PostMapping
    public ResponseEntity<String> saveUrl(@RequestParam("url") String url) throws MalformedURLException, URISyntaxException {
        return ResponseEntity.ok(urlShortenerService.saveUrl(url).toString());
    }
}

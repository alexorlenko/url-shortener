package com.softserveinc.urlshortener.controller;

import com.softserveinc.urlshortener.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * UrlShortenerController class is the controller of our application.
 *
 * @author alexorlenko
 */
@RestController
public class UrlShortenerController {

    UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * find method, this is HTTP method GET in which,
     * using the key, go to the original link.
     *
     * @param key the key by which are looking in the database(map)
     * @return the response redirects to the original Url or NOT_FOUND, BED_REQUEST response
     */
    @GetMapping("/{key}")
    public ResponseEntity<String> find(@PathVariable String key) {
        try {
            URI uri = urlShortenerService.find(key);
            if (uri == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found in Database.");
            } else {
                return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(urlShortenerService.find(key)).build();
            }
        } catch (MalformedURLException | URISyntaxException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    /**
     * create method, this is HTTP method POST in which,
     * create short url.
     *
     * @param url url what we got
     * @return response with short url or BAD_REQUEST
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestParam("url") String url) {
        try {
            return ResponseEntity.ok(urlShortenerService.create(url).toString());
        } catch (MalformedURLException | URISyntaxException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    /**
     * delete method, this is HTTP method DELETE in which,
     * delete the original url from database(map) using a key.
     *
     * @param key the key by which are looking in the database(map)
     */
    @DeleteMapping("/{key}")
    public ResponseEntity<String> delete(@PathVariable String key) {
        urlShortenerService.delete(key);
        return ResponseEntity.ok().build();
    }
}

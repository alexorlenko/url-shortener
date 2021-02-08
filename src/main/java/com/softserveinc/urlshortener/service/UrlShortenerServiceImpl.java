package com.softserveinc.urlshortener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * UrlShortenerServiceImpl is the service layer of our application.
 *
 * @author alexorlenko
 */
@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {
    //A Map is used instead of a database, since this will not affect the logic of the application in any way,
    //but will make it easier to use the application
    public final static Map<String, String> database = new HashMap<>();
    //A localhost is used now, but in the future, if necessary, it will be possible to change,
    //for example, to the one that comes in the request
    private final static String BASE_URL = "http://localhost:8080";
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlShortenerServiceImpl.class);

    /**
     * find method, this is the method where use the key to get the
     * original link from database(map).
     *
     * @param key the key by which we are looking in the database(map)
     * @return original URI or null
     * @throws URISyntaxException
     */
    @Override
    public URI find(String key) throws URISyntaxException {
        String uri = database.get(key);
        if (uri == null) return null;
        LOGGER.info("Entry found: {}", uri);
        return new URI(uri);
    }

    /**
     * create method is a method in which search the database(map) for the received key, if we already
     * have this key, then we take it. Otherwise, we generate a new key and put this key-value pair into
     * the database(map).
     *
     * @param url url what we got
     * @return short uri
     * @throws URISyntaxException
     */
    @Override
    public URI create(String url) throws URISyntaxException {
        String key = getKeyByValue(database, url);
        if (key == null) {
            //UUID is used as it a universal identifier with the ability to randomly generate characters
            key = UUID.randomUUID().toString().substring(0, 4);
            //Try to create URI from Url to check if it's eligible or throw Exception
            URI uri = new URI(url);
            database.put(key, String.valueOf(uri));
            LOGGER.info("New key created: {}", key);
        } else {
            LOGGER.info("Key found in database: {}", key);
        }
        return new URI(String.format("%s/%s", BASE_URL, key));
    }

    /**
     * delete method is the method by which remove
     * the key-value pair from database(map).
     *
     * @param key the key by which we are looking in the database(map)
     */
    @Override
    public void delete(String key) {
        database.remove(key);
    }

    /**
     * getKeyByValue method is the method when get a key
     * by value from database(map).
     *
     * @param map   our database
     * @param value url value what we got
     * @param <T>   key type of map
     * @param <E>   value type of map
     * @return key from map
     */
    private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}

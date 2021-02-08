package com.softserveinc.urlshortener;

import com.softserveinc.urlshortener.service.UrlShortenerService;
import com.softserveinc.urlshortener.service.UrlShortenerServiceImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlShortenerServiceImplTests {

    @Autowired
    UrlShortenerService urlShortenerService;

    @BeforeClass
    public static void setUp() {
        UrlShortenerServiceImpl.database.put("1111", "https://javarush.ru/groups/posts/605-junit");
        UrlShortenerServiceImpl.database.put("2222", "any data");
    }

    @AfterClass
    public static void tearDown() {
        UrlShortenerServiceImpl.database.remove("1111");
        UrlShortenerServiceImpl.database.remove("2222");
    }

    @Test
    public void testFind_shouldReturnUriWhenFoundInDb() throws MalformedURLException, URISyntaxException {
        Assert.assertEquals(urlShortenerService.find("1111"), URI.create("https://javarush.ru/groups/posts/605-junit"));
    }

    @Test
    public void testFind_shouldReturnNullWhenNotFoundInDb() throws MalformedURLException, URISyntaxException {
        Assert.assertEquals(urlShortenerService.find("9999"), null);
    }

    @Test(expected = URISyntaxException.class)
    public void testFind_shouldThrowExceptionWhenWrongUri() throws MalformedURLException, URISyntaxException {
        urlShortenerService.find("2222");
    }

    @Test
    public void testCreate_shouldReturnUriWhenFoundInDb() throws MalformedURLException, URISyntaxException {
        URI uri = urlShortenerService.create("https://javarush.ru/groups/posts/605-junit");
        Assert.assertTrue(uri.toString().endsWith("1111"));
    }

    @Test
    public void testCreate_shouldReturnUriWhenNotFoundInDb() throws MalformedURLException, URISyntaxException {
        URI uri = urlShortenerService.create("https://github.com/ita-social-projects/Home");
        Assert.assertEquals(urlShortenerService.find(uri.getPath().substring(1)).toString(), "https://github.com/ita-social-projects/Home");
    }

    @Test(expected = URISyntaxException.class)
    public void testCreate_shouldThrowExceptionWhenWrongUri() throws MalformedURLException, URISyntaxException {
        urlShortenerService.create("wrong uri");
    }

}

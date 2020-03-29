package com.games.rssreader.service;

import com.games.rssreader.exceptions.XmlParsingException;
import com.games.rssreader.model.RssMessages;
import com.games.rssreader.service.RssService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.NotNull;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RssServiceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RssService rssService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testStatus200() throws Exception {
        // when
        String url = "http://localhost:" + port + "/api";
        ResponseEntity<List> entity = testRestTemplate.getForEntity(url, List.class);
        // then
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void testReturnStoredItems() throws Exception {
        // given
        RssMessages rssMessages = new RssMessages(1L, "1", "1", "1", "1", ZonedDateTime.now());
        RssMessages rssMessages2 = new RssMessages(2L, "2", "2", "2", "2", ZonedDateTime.now());
        rssService.save(rssMessages);
        rssService.save(rssMessages2);

        // when
        @NotNull List<RssMessages> messages = rssService.getAll();

        // then
        assertEquals(messages.get(0).getId(), rssMessages.getId());
        assertEquals(messages.get(1).getId(), rssMessages2.getId());
    }
}

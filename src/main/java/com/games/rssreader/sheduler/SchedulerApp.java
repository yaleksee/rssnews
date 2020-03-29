package com.games.rssreader.sheduler;

import com.games.rssreader.model.RssMessages;
import com.games.rssreader.parser.RSSFeedParser;
import com.games.rssreader.service.RssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;

import static javax.xml.stream.XMLInputFactory.newInstance;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerApp {

    private final RssService rssService;
    private final RSSFeedParser rssFeedParser;

    @Scheduled(fixedDelay = 10000)
    public void execute() {
        final List<RssMessages> rssItems;
        try {
            log.info("recording posts from rss feed on time: " + " " + new GregorianCalendar().getTime().toString());
            rssItems = rssFeedParser.readFeed(read());
            rssService.saveAll(rssItems);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Value("${url}")
    private String feedUrl;

    public XMLEventReader read() {
        try {
            final URL url = new URL(feedUrl);
            final XMLInputFactory inputFactory = newInstance();
            final InputStream in = url.openStream();
            return inputFactory.createXMLEventReader(in);
        } catch (XMLStreamException | IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

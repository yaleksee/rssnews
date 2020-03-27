package com.games.rssnews.parser;

import com.games.rssnews.model.RssItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class RSSFeedParser {
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String LINK = "link";
    static final String AUTHOR = "author";
    static final String ITEM = "item";
    static final String GUID = "guid";

    final URL url;
    @Value("${rss.url}")
    private String feedUrl;

    public RSSFeedParser() {
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<RssItem> readFeed() {
        final List<RssItem> rssItems = new ArrayList<>();
        try {
            boolean isFeedHeader = true;
            String description = "";
            String title = "";
            String link = "";
            String author = "";
            String guid = "";

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName().getLocalPart();
                    switch (localPart) {
                        case ITEM:
                            if (isFeedHeader) {
                                isFeedHeader = false;
                            }
                            eventReader.nextEvent();
                            break;
                        case TITLE:
                            title = getCharacterData(event, eventReader);
                            break;
                        case DESCRIPTION:
                            description = getCharacterData(event, eventReader);
                            break;
                        case LINK:
                            link = getCharacterData(event, eventReader);
                            break;
                        case GUID:
                            guid = getCharacterData(event, eventReader);
                            break;
                        case AUTHOR:
                            author = getCharacterData(event, eventReader);
                            break;
                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart().equals(ITEM)) {
                        final RssItem rssItem = new RssItem();
                        rssItem.setAuthor(author);
                        rssItem.setDescription(description);
                        rssItem.setGuid(guid);
                        rssItem.setLink(link);
                        rssItem.setTitle(title);
                        rssItem.setPubDate(generatePubDate());
                        rssItems.add(rssItem);
                        eventReader.nextEvent();
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return rssItems;
    }

    private ZonedDateTime generatePubDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(2019, 2020);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return gc.toZonedDateTime();
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

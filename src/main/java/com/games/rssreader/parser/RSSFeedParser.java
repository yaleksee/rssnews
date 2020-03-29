package com.games.rssreader.parser;

import com.games.rssreader.exceptions.XmlParsingException;
import com.games.rssreader.model.RssMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.time.ZonedDateTime;
import java.util.*;

@Component
@Slf4j
public class RSSFeedParser {
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String LINK = "link";
    static final String ITEM = "item";
    static final String GUID = "guid";
    private final List<RssMessages> rssItems = new ArrayList<>();


    public List<RssMessages> readFeed(XMLEventReader eventReader) {
        try {
            boolean isFeedHeader = true;
            String description = "";
            String title = "";
            String link = "";
            String guid = "";
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
                            title = getCharacterData(eventReader);
                            break;
                        case DESCRIPTION:
                            description = getCharacterData(eventReader);
                            break;
                        case LINK:
                            link = getCharacterData(eventReader);
                            break;
                        case GUID:
                            guid = getCharacterData(eventReader);
                            break;
                        default:
                            break;
                    }
                } else {
                    if (!event.isEndElement()) {
                        continue;
                    }
                    if (event.asEndElement().getName().getLocalPart().equals(ITEM)) {
                        final RssMessages rssItem = new RssMessages();
                        Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
                        rssItem.setId(id);
                        rssItem.setDescription(description);
                        rssItem.setGuid(guid);
                        rssItem.setLink(link);
                        rssItem.setTitle(title);
                        rssItem.setPubDate(generatePubDate());
                        rssItems.add(rssItem);
                        log.info("new RssMessage was parsed " + rssItem.toString());
                        eventReader.nextEvent();
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new XmlParsingException("Error parsing XML ", e);
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

    private String getCharacterData(XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        final XMLEvent event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }
}

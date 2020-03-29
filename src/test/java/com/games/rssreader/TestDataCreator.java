package com.games.rssreader;

import com.games.rssreader.model.RssMessages;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.games.rssreader.util.Util.generatePubDate;

public class TestDataCreator {
    public static RssMessages createRssMessages(int countDay) {
        Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        String description = "description " + UUID.randomUUID().toString();
        String title = "title " + UUID.randomUUID().toString();
        String link = "link " + UUID.randomUUID().toString();
        String guid = "guid " + UUID.randomUUID().toString();
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.DAY_OF_YEAR, countDay);
        ZonedDateTime pubDate = gc.toInstant().atZone(ZoneId.of("UTC"));
        return new RssMessages(id, title, description, link, guid, pubDate);
    }
}

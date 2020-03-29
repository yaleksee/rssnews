package com.games.rssreader;

import com.games.rssreader.model.RssMessages;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataCreator {
    public static RssMessages createRssInstance() {
        Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        String description = "description " + UUID.randomUUID().toString();
        String title = "title " + UUID.randomUUID().toString();
        String link = "link " + UUID.randomUUID().toString();
        String guid = "guid " + UUID.randomUUID().toString();
        ZonedDateTime pubDate = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        return new RssMessages(id, title, description, link, guid, pubDate);
    }

    public static RssMessages createRssInstancePlusDay() {
        Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        ZonedDateTime pubDate = ZonedDateTime.now().plusDays(
                ThreadLocalRandom.current().nextInt(0, 500 + 1)).truncatedTo(ChronoUnit.SECONDS);
        String description = "description " + UUID.randomUUID().toString();
        String title = "title " + UUID.randomUUID().toString();
        String link = "link " + UUID.randomUUID().toString();
        String guid = "guid " + UUID.randomUUID().toString();
        return new RssMessages(id, title, description, link, guid, pubDate);
    }
}

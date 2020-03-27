package com.games.rssnews.sheduler;

import com.games.rssnews.model.RssItem;
import com.games.rssnews.parser.RSSFeedParser;
import com.games.rssnews.service.RssItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLEventReader;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerApp {

    private final RssItemsService rssItemsService;
    private final RSSFeedParser rssFeedParser;
    private final XMLEventReader xmlEventReader;

    @Scheduled(fixedDelay = 5000)
    public void execute() {
        final List<RssItem> rssItems;
        try {
            log.info("recording posts from rss feed");
            rssItems = rssFeedParser.readFeed(xmlEventReader);
            rssItemsService.saveAll(rssItems);
        } catch (Exception e) {
        }
    }
}

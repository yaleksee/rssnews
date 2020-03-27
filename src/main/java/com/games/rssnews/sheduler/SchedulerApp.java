package com.games.rssnews.sheduler;

import com.games.rssnews.model.RssItem;
import com.games.rssnews.parser.RSSFeedParser;
import com.games.rssnews.service.RssItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SchedulerApp {

    private final RssItemsService rssItemsService;
    private final RSSFeedParser rssFeedParser;

    @Scheduled(fixedRate = 5000)
    public void execute() {
        List<RssItem> rssItems = null;
        try {
            rssItems = rssFeedParser.readFeed();
            rssItemsService.saveAll(rssItems);
        } catch (Exception e) {
        }
    }
}

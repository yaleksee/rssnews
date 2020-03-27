package com.games.rssnews.controller;

import com.games.rssnews.model.RssItem;
import com.games.rssnews.service.RssItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController("/getItems")
@RequiredArgsConstructor
public class Get10LatestRssItems {

    private final RssItemsService rssItemsService;

    @GetMapping("/{count}")
    public List<RssItem> get10LatestItems(@PathVariable(value = "count") @NotNull int count) {
        return rssItemsService.getItems(count);
    }

}

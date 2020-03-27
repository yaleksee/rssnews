package com.games.rssnews.controller;

import com.games.rssnews.model.RssItem;
import com.games.rssnews.service.RssItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetRssItems {

    private final RssItemsService rssItemsService;

    @GetMapping("/{count}")
    @ResponseBody
    public List<RssItem> getItems(@PathVariable(value = "count") @NotNull Long count) {
        return rssItemsService.getItems(count);
    }

    @GetMapping()
    @ResponseBody
    public List<RssItem> getAll() {
        return rssItemsService.getAll();
    }

}

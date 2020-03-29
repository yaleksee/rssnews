package com.games.rssreader.controller;

import com.games.rssreader.exceptions.ResourceNotFoundException;
import com.games.rssreader.model.RssMessages;
import com.games.rssreader.service.RssService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetRssItems {

    private final RssService rssService;

    @GetMapping("/get")
    @ApiOperation("get messages by count")
    public List<RssMessages> getItems(@RequestParam(value = "count") @NotNull Long count) throws ResourceNotFoundException {
        return rssService.getItems(count);
    }

    @GetMapping()
    @ApiOperation("get all messages")
    public List<RssMessages> getAll() {
        return rssService.getAll();
    }

}

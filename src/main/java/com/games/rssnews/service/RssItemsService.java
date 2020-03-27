package com.games.rssnews.service;

import com.games.rssnews.model.RssItem;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RssItemsService {

    void save(@NotNull RssItem rssItem);

    void deleteAll();

    @NotNull
    List<RssItem> getAll();

    void saveAll(@NotNull List<RssItem> storedItems);

    @NotNull
    List<RssItem> getItems(@NotNull Long count);
}

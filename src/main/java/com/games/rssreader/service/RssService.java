package com.games.rssreader.service;


import com.games.rssreader.model.RssMessages;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RssService {

    void save(@NotNull RssMessages rssItem);

    void deleteAll();

    @NotNull
    List<RssMessages> getAll();

    void saveAll(@NotNull List<RssMessages> storedItems);

    @NotNull
    List<RssMessages> getItems(@NotNull Long count) throws Exception;
}

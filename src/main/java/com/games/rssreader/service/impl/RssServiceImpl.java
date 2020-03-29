package com.games.rssreader.service.impl;

import com.games.rssreader.exceptions.ResourceNotFoundException;
import com.games.rssreader.model.RssMessages;
import com.games.rssreader.service.RssService;
import com.games.rssreader.service.repo.RSSRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RssServiceImpl implements RssService {
    private final RSSRepository rssRepository;

    @Override
    public void save(@NotNull RssMessages rssItem) {
        rssRepository.saveAndFlush(rssItem);
    }

    @Override
    public void deleteAll() {
        rssRepository.deleteAll();
    }

    @Override
    public void saveAll(@NotNull List<RssMessages> storedItems) {
        rssRepository.saveAll(storedItems);
    }

    @Override
    public @NotNull List<RssMessages> getAll() {
        return rssRepository.getAll();
    }

    @Override
    public @NotNull List<RssMessages> getItems(@NotNull Long count) {
        Optional<List<RssMessages>> rssItems = Optional.ofNullable(rssRepository.getItems(count));
        if (rssItems.isEmpty()) {
            log.error("Entry not found for this count : " + count);
            throw new ResourceNotFoundException("Entry not found for this count : " + count);
        }
        return rssRepository.getItems(count);
    }
}

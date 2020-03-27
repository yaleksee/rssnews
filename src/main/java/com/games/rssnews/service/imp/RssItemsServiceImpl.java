package com.games.rssnews.service.imp;

import com.games.rssnews.exceptions.ResourceNotFoundException;
import com.games.rssnews.model.RssItem;
import com.games.rssnews.service.RssItemsService;
import com.games.rssnews.service.repository.RssItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RssItemsServiceImpl implements RssItemsService {
    private final RssItemRepository rssItemRepository;

    @Override
    public void save(@NotNull RssItem rssItem) {
        rssItemRepository.save(rssItem);
    }

    @Override
    public void deleteAll() {
        rssItemRepository.deleteAll();
    }

    @Override
    public void saveAll(@NotNull List<RssItem> storedItems) {
        rssItemRepository.saveAll(storedItems);
    }

    @Override
    public List<RssItem> getAll() {
        return rssItemRepository.getAll();
    }


    @Override
    public @NotNull List<RssItem> getItems(@NotNull Long count) {
        Optional<List<RssItem>> rssItems = Optional.ofNullable(rssItemRepository.getItems(count));
        if (rssItems.isPresent()) {
            throw new ResourceNotFoundException("Entry not found for this id : " + count);
        }
        return rssItemRepository.getItems(count);
    }
}

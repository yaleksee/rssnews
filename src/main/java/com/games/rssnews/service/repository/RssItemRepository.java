package com.games.rssnews.service.repository;

import com.games.rssnews.model.RssItem;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class RssItemRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(RssItem rssItem) {
        try {
            jdbcTemplate.update(
                    "insert into rss_item (id, title, description, link, autor, guid, pub_date) values(?,?,?,?,?,?,?)",
                    new Object[]{
                            rssItem.getId(),
                            rssItem.getTitle(),
                            rssItem.getDescription(),
                            rssItem.getLink(),
                            rssItem.getAuthor(),
                            rssItem.getGuid(),
                            rssItem.getPubDate().toInstant()});
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM RSS_ITEM");
    }


    public List<RssItem> getAll() {
        return jdbcTemplate.query("select * from rss_item", new RssItemRowMapper());
    }

    public void saveAll(List<RssItem> storedItems) {
        storedItems.forEach(this::save);
    }

    public List<RssItem> getItems(@NotNull Long count) {
        return jdbcTemplate.query("select * from rss_item order by pub_date desc FETCH FIRST " + count + " ROWS ONLY", new RssItemRowMapper());
    }

    static class RssItemRowMapper implements RowMapper<RssItem> {
        @Override
        public RssItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            String title = rs.getString("title");
            String description = rs.getString("description");
            String link = rs.getString("link");
            String author = rs.getString("author");
            String guid = rs.getString("guid");
            Timestamp pubDate = rs.getTimestamp("pub_date");
            ZonedDateTime dateTime = null;
            if (!isNull(pubDate)) {
                Instant instant = pubDate.toInstant();
                dateTime = instant.atZone(ZoneId.of("UTC"));
            }
            return new RssItem(title, description, link, author, guid, dateTime);
        }
    }
}

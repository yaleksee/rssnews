package com.games.rssreader.service.repo;

import com.games.rssreader.exceptions.ResourceNotFoundException;
import com.games.rssreader.exceptions.SQLCustomException;
import com.games.rssreader.model.RssMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RSSRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveAndFlush(RssMessages rssMessages) {
        final String update = "insert into RSS_MESSAGE (id, title, description, link, guid, pub_date) values(?,?,?,?,?,?)";
        try {
            jdbcTemplate.update(
                    update,
                    rssMessages.getId(),
                    rssMessages.getTitle(),
                    rssMessages.getDescription(),
                    rssMessages.getLink(),
                    rssMessages.getGuid(),
                    rssMessages.getPubDate().toInstant());
        } catch (SQLCustomException e) {
            log.error(e.getMessage());
        }
    }

    public void saveAll(List<RssMessages> storedItems) {
        storedItems.stream().forEach(item -> saveAndFlush(item));
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM RSS_MESSAGE");
    }

    public List<RssMessages> getAll() {
        return jdbcTemplate.query("select * from RSS_MESSAGE", new RssEntityRowMapper());
    }

    public List<RssMessages> getItems(@NotNull Long count) {
        final String sql = String.format("select * from RSS_MESSAGE order by pub_date desc FETCH FIRST %d ROWS ONLY", count);
        return checkCount(count) ? jdbcTemplate.query(sql, new RssEntityRowMapper()) : new ArrayList<>();
    }

    public boolean checkCount(Long count) {
        final String sql = "SELECT count(*) FROM RSS_MESSAGE";
        final Number number = jdbcTemplate.queryForObject(sql, Integer.class);
        final int numberRowsInTable = isNull(number) ? 0 : number.intValue();
        if (count > numberRowsInTable)
            throw new ResourceNotFoundException("Enter count less than " + numberRowsInTable);
        return (count < numberRowsInTable);
    }

    static class RssEntityRowMapper implements RowMapper<RssMessages> {
        @Override
        public RssMessages mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Long id = rs.getLong("id");
            final String title = rs.getString("title");
            final String description = rs.getString("description");
            final String link = rs.getString("link");
            final String guid = rs.getString("guid");
            final Timestamp pubDate = rs.getTimestamp("pub_date");
            final ZonedDateTime dateTime = !isNull(pubDate) ? pubDate.toInstant().atZone(ZoneId.of("UTC")) : null;
            return new RssMessages(id, title, description, link, guid, dateTime);
        }
    }
}

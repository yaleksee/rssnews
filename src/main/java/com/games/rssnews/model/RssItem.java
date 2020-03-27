package com.games.rssnews.model;

import lombok.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.util.Objects.isNull;

@Getter
@Setter
@NoArgsConstructor
public class RssItem {

    private String id;
    private String title;
    private String description;
    private String link;
    private String author;
    private String guid;
    private ZonedDateTime pubDate;

    public RssItem(String title, String description, String link, String author, String guid, ZonedDateTime pubDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.author = author;
        this.guid = guid;
        this.pubDate = pubDate;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(description.substring(0, Math.min(title.length(), 10))).append(
                (isNull(pubDate) ? "" : DateTimeFormatter.RFC_1123_DATE_TIME.format(pubDate)));
        id = stringBuffer.toString();
    }

    @Override
    public String toString() {
        return "RssItem{" +
                "author='" + getAuthor() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", pubDate=" + DateTimeFormatter.RFC_1123_DATE_TIME.format(getPubDate()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RssItem)) return false;
        RssItem rssItem = (RssItem) o;
        return getTitle().equals(rssItem.getTitle()) &&
                getDescription().equals(rssItem.getDescription()) &&
                getLink().equals(rssItem.getLink()) &&
                getAuthor().equals(rssItem.getAuthor()) &&
                getGuid().equals(rssItem.getGuid());
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + pubDate.hashCode();
        result = 31 * result + author.hashCode();
        return result;
    }
}

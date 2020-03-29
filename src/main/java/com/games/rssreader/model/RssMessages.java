package com.games.rssreader.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class RssMessages {
    private Long id;
    private String title;
    private String description;
    private String link;
    private String guid;
    private ZonedDateTime pubDate;

    public RssMessages(Long id, String title, String description, String link, String guid, ZonedDateTime pubDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.guid = guid;
        this.pubDate = pubDate;
    }

    public RssMessages() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public ZonedDateTime getPubDate() {
        return pubDate;
    }

    public void setPubDate(ZonedDateTime pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RssMessages)) return false;
        RssMessages testq = (RssMessages) o;
        return Objects.equals(getTitle(), testq.getTitle()) &&
                Objects.equals(getDescription(), testq.getDescription()) &&
                Objects.equals(getLink(), testq.getLink()) &&
                Objects.equals(getGuid(), testq.getGuid()) &&
                Objects.equals(getPubDate(), testq.getPubDate());
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + pubDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RssEntity{" +
                "id=" + id +
                '}';
    }
}

package com.games.rssnews.model;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class RssItem {

    private String id;
    private final String title;
    private final String description;
    private final String link;
    private final String author;
    private final String guid;
    private final ZonedDateTime pubDate;
}

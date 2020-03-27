package com.games.rssnews.model;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RssItem {

    private String title;
    private String description;
    private String link;
    private String author;
    private String guid;
    private ZonedDateTime pubDate;

}

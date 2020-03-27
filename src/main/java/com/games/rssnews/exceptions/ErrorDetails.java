package com.games.rssnews.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private final Date timestamp;
    private final String message;
    private final String details;
}

package com.games.rssreader.exceptions;

import org.springframework.dao.DataAccessException;

public class SQLCustomException extends DataAccessException {

	private static final long serialVersionUID = 1L;

	public SQLCustomException(String message){
    	super(message);
    }

	public SQLCustomException(String message, Throwable cause) {
		super(message, cause);
	}
}
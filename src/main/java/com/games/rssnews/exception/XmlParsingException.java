package com.games.rssnews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class XmlParsingException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public XmlParsingException(String message){
    	super(message);
    }

	public XmlParsingException(String message, Throwable cause) {
		super(message, cause);
	}
}
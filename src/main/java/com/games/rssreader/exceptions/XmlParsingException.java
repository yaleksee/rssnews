package com.games.rssreader.exceptions;

public class XmlParsingException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public XmlParsingException(String message){
    	super(message);
    }

	public XmlParsingException(String message, Throwable cause) {
		super(message, cause);
	}
}
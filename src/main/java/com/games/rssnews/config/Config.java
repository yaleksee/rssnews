package com.games.rssnews.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Configuration
public class Config extends WebMvcConfigurationSupport {
    @Value("${rss.url}")
    private String feedUrl;

    @Bean
    @Scope("prototype")
    public XMLEventReader read() {
        try {
            URL url = new URL(feedUrl);
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = url.openStream();
            return inputFactory.createXMLEventReader(in);
        } catch (XMLStreamException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}

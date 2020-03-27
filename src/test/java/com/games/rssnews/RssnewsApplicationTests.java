package com.games.rssnews;

import com.games.rssnews.model.RssItem;
import com.games.rssnews.parser.RSSFeedParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RssnewsApplicationTests {

    @Test
    void contextLoads() {
    }


}

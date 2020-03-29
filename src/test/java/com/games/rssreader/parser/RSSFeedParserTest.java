package com.games.rssreader.parser;

import com.games.rssreader.exceptions.XmlParsingException;
import com.games.rssreader.model.RssMessages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static javax.xml.stream.XMLInputFactory.newInstance;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RSSFeedParserTest {

    @Mock
    private RSSFeedParser rssFeedParser;

    private XMLEventReader eventReader;
    private XMLEventReader eventReaderError;

    final RssMessages rssMessagesTest = new RssMessages();

    @Before
    public void setUp() throws XMLStreamException {
        read();
        rssMessagesTest.setTitle("Android user interface testing with Espresso  Tutorial");
        rssMessagesTest.setDescription("This tutorial describes how to test Android     applications with     the     Android Espresso testing framework.");
        rssMessagesTest.setLink("http://www.vogella.com/tutorials/AndroidTestingEspresso/article.html");
        rssMessagesTest.setGuid("http://www.vogella.com/tutorials/AndroidTestingEspresso/article.html");
    }

    @Test
    public void testReturnListParsedMessaged() {
        // given
        List<RssMessages> parsedItems = Arrays.asList(new RssMessages(), new RssMessages());
        when(rssFeedParser.readFeed(eventReader)).thenReturn(parsedItems);
        // then
        Assert.assertThat(rssFeedParser.readFeed(eventReader), contains(parsedItems.toArray()));
    }

    @Test
    public void testSuccessParseRss() {
        final RSSFeedParser rssFeedParser = new RSSFeedParser();
        final RssMessages rssMessageLocal = rssFeedParser.readFeed(eventReader).get(0);
        Assert.assertEquals(rssMessageLocal.getTitle(), rssMessagesTest.getTitle());
        Assert.assertEquals(rssMessageLocal.getDescription(), rssMessagesTest.getDescription());
        Assert.assertEquals(rssMessageLocal.getGuid(), rssMessagesTest.getGuid());
        Assert.assertEquals(rssMessageLocal.getLink(), rssMessagesTest.getLink());
        Assert.assertNotNull(rssMessageLocal.getId());
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThrowExceptionIfParsingFailed() {
        when(rssFeedParser.readFeed(eventReaderError)).thenThrow(XmlParsingException.class);
        expectedException.expect(XmlParsingException.class);
        rssFeedParser.readFeed(eventReaderError);
    }

    public void read() {
        try {
            final XMLInputFactory inputFactory = newInstance();
            FileInputStream in = new FileInputStream("src/test/java/com/games/rssreader/parser/test.xml");
            FileInputStream inError = new FileInputStream("src/test/java/com/games/rssreader/parser/testError.xml");
            try {
                this.eventReader = inputFactory.createXMLEventReader(in);
                this.eventReaderError = inputFactory.createXMLEventReader(inError);
            } catch (XMLStreamException e) {
                System.out.println("There was an XML Stream Exception, whatever that means");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file. Try again.");
        }
    }
}

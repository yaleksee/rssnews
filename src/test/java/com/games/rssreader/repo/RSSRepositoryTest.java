package com.games.rssreader.repo;

import com.games.rssreader.TestDataCreator;
import com.games.rssreader.exceptions.ResourceNotFoundException;
import com.games.rssreader.exceptions.XmlParsingException;
import com.games.rssreader.model.RssMessages;
import com.games.rssreader.service.repo.RSSRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RSSRepositoryTest {

    @Autowired
    private RSSRepository rssRepository;

    @Before
    public void setUp() {
        rssRepository.deleteAll();
    }

    @Test
    public void testSaved() {
        // given
        final RssMessages rssMessages = TestDataCreator.createRssMessages(1);

        // when
        rssRepository.saveAndFlush(rssMessages);
        final List<RssMessages> messagesList = rssRepository.getAll();

        // then
        assertThat(messagesList, hasItem(rssMessages));
    }

    @Test
    public void testReturnMessagesGroupByDate() throws Exception {
        // given
        final List<RssMessages> messagesList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messagesList.add(TestDataCreator.createRssMessages(i));
        }
        // when
        rssRepository.saveAll(messagesList);
        final List<RssMessages> getCountList = rssRepository.getItems(5L);
        System.out.println(messagesList);
        // then
        assertThat(getCountList, containsInAnyOrder(messagesList.subList(5, 10).toArray()));
    }

    @Test
    public void testNotSaveDuplicates() {
        // given
        final List<RssMessages> messagesList = new ArrayList<>();
        final RssMessages rssMessages = TestDataCreator.createRssMessages(0);
        messagesList.add(rssMessages);
        final RssMessages rssMessages2 = new RssMessages(
                rssMessages.getId(),
                rssMessages.getTitle(),
                rssMessages.getDescription(),
                rssMessages.getLink(),
                rssMessages.getGuid(),
                rssMessages.getPubDate()
        );
        messagesList.add(rssMessages2);

        // when
        rssRepository.saveAll(messagesList);

        // then
        List<RssMessages> storedMessages = rssRepository.getAll();
        assertEquals(1, storedMessages.size());
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testCheckCount() {
        final List<RssMessages> messagesList = new ArrayList<>();
        final RssMessages rssMessages = TestDataCreator.createRssMessages(0);
        messagesList.add(rssMessages);
        rssRepository.saveAll(messagesList);
        expectedException.expectMessage("Enter count less than 1");
        rssRepository.checkCount(10L);
    }

}

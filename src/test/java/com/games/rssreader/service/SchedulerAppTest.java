package com.games.rssreader.service;

import com.games.rssreader.sheduler.SchedulerApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static java.util.OptionalDouble.empty;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SchedulerAppTest {

    @Autowired
    private SchedulerApp schedulerApp;
    @Autowired
    private RssService rssService;

    @Test
    public void testWorkScheduler() {
        // when
        schedulerApp.execute();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // then
        assertThat(rssService.getAll(), not(empty()));

    }
}

package com.games.rssreader.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Util {
    public static ZonedDateTime generatePubDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(2019, 2020);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return gc.toInstant().atZone(ZoneId.of("UTC"));
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private Util() {

    }
}

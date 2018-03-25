package com.github.songbird0;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeltaTimeComputerTest {
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void addDayTest() {
        final Calendar now = new GregorianCalendar();
        final int currentDayMonth = now.get(Calendar.DAY_OF_MONTH);
        final Calendar tomorrow = new DeltaTimeComputer(now).plus(1).day().computeIt();
        assertThat(tomorrow.get(Calendar.DAY_OF_MONTH), is(currentDayMonth + 1));
    }

    @Test
    public void addTwoDaysTest() {
        final Calendar now = new GregorianCalendar();
        final int currentDayMonth = now.get(Calendar.DAY_OF_MONTH);
        final Calendar tomorrow =  new DeltaTimeComputer(now).plus(2).day().computeIt();
        assertThat(tomorrow.get(Calendar.DAY_OF_MONTH), is(currentDayMonth + 2));
    }

    @Test
    public void addTwoYears() {
        final Calendar now = new GregorianCalendar();
        final int currentYear = now.get(Calendar.YEAR);
        final Calendar tomorrow =  new DeltaTimeComputer(now).plus(2).year().computeIt();
        assertThat(tomorrow.get(Calendar.YEAR), is(currentYear + 2));
    }

    @Test
    public void alternativeToAddTwoYears() {
        final Calendar now = new GregorianCalendar();
        final int currentYear = now.get(Calendar.YEAR);
        final Calendar tomorrow = new DeltaTimeComputer(now)
                .plus(1)
                .year()
                .plus(1)
                .year()
                .computeIt();
        assertThat(tomorrow.get(Calendar.YEAR), is(currentYear + 2));
    }

    @Test
    public void addOneDayAndOneYear() {
        final Calendar now = new GregorianCalendar();
        final int currentYear = now.get(Calendar.YEAR);
        final int currentDay = now.get(Calendar.DAY_OF_MONTH);
        final Calendar tomorrow = new DeltaTimeComputer(now)
                .plus(1)
                .day()
                .plus(1)
                .year()
                .computeIt();
        assertThat(tomorrow.get(Calendar.YEAR), is(currentYear + 1));
        assertThat(tomorrow.get(Calendar.DAY_OF_MONTH), is(currentDay + 1));
    }
}

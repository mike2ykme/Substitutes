package com.icrn.substitutes.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class HolidayAvailabilityTest {

    HolidayAvailability holidayAvailability;
    @Before
    public void setup(){
        this.holidayAvailability = new HolidayAvailability();
    }

    @Test
    public void testHolidayScheduled(){
        this.holidayAvailability.addHoliday(LocalDate.now());
        assertThat(this.holidayAvailability.isHolidayScheduled(LocalDate.now()),is(true));

        this.holidayAvailability.removeHoliday(LocalDate.now());
        assertThat(this.holidayAvailability.isHolidayScheduled(LocalDate.now()),is(false));
    }

    @Test
    public void verifyAltConstructor(){
        this.holidayAvailability = new HolidayAvailability(new HashSet<String>());
        assertThat(this.holidayAvailability.getAvailability(),is(not(nullValue())));
    }

}
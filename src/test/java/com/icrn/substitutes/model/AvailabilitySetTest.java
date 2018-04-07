package com.icrn.substitutes.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class AvailabilitySetTest {

    AvailabilitySet availabilitySet;
    @Before
    public void setup(){
        this.availabilitySet = new AvailabilitySet();
    }

    @Test
    public void testHolidayScheduled(){
        this.availabilitySet.addDay(LocalDate.now());
        assertThat(this.availabilitySet.isScheduled(LocalDate.now()),is(true));

        this.availabilitySet.removeDay(LocalDate.now());
        assertThat(this.availabilitySet.isScheduled(LocalDate.now()),is(false));
    }

    @Test
    public void verifyAltConstructor(){
        this.availabilitySet = new AvailabilitySet(new HashSet<String>());
        assertThat(this.availabilitySet.getAvailability(),is(not(nullValue())));
    }

}
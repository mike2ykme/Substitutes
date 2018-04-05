package com.icrn.substitutes.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SubstituteTest {

    Substitute sub;
    Availability availability;
    HolidayAvailability holidayAvailability;

    @Before
    public void setup(){
        this.availability = new Availability();
        this.availability.addAvailabilityTime(
                0,new StartEnd(LocalTime.of(6,0),LocalTime.of(21,0)));
        this.availability.addAvailabilityTime(
                1,new StartEnd(LocalTime.of(6,0),LocalTime.of(21,0)));
        this.availability.addAvailabilityTime(
                2,new StartEnd(LocalTime.of(6,0),LocalTime.of(21,0)));
        this.availability.addAvailabilityTime(
                3,new StartEnd(LocalTime.of(6,0),LocalTime.of(21,0)));
        this.availability.addAvailabilityTime(
                4,new StartEnd(LocalTime.of(6,0),LocalTime.of(21,0)));

        this.holidayAvailability = new HolidayAvailability();
        this.holidayAvailability.addHoliday(LocalDate.of(2011,11,11));

        sub = new Substitute(1234567890l,"Name","123456789","123 Fake Street",
                holidayAvailability,this.availability);
    }

    @Test
    public void testIsAvailableFor(){
        LocalDateTime ldt1 = LocalDateTime.of(2018,4,5,9,0);
        LocalDateTime ldt2 = LocalDateTime.of(2018,4,5,17,0);
        assertThat(this.sub.isAvailableOn(ldt1,ldt2),is(true));
    }

    @Test(expected = RuntimeException.class)
    public void expectExceptionWhenUsingDifferentDays(){
        this.sub.isAvailableOn(LocalDateTime.of(2018,4,5,6,7),
                LocalDateTime.of(2018,4,6,7,8));

    }

    @Test
    public void testNotAvailable(){
        LocalDateTime ldt = LocalDateTime.of(2011,11,11,0,0);
        assertThat(this.sub.isAvailableOn(ldt,ldt),is(false));
    }


}
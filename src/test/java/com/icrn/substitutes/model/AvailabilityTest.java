package com.icrn.substitutes.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class AvailabilityTest {
    Availability availability;
    @Before
    public void setup(){
        this.availability = new Availability();
        this.availability.addAvailabilityTime(
                0,new StartEnd(LocalTime.of(5,0),LocalTime.of(21,0)));
        this.availability.addAvailabilityTime(
                1,new StartEnd(LocalTime.of(5,0),LocalTime.of(21,0)));
        this.availability.addAvailabilityTime(
                2,new StartEnd(LocalTime.of(5,0),LocalTime.of(21,0)));
        this.availability.addAvailabilityTime(
                3,new StartEnd(LocalTime.of(5,0),LocalTime.of(21,0)));
        this.availability.addAvailabilityTime(
                4,new StartEnd(LocalTime.of(5,0),LocalTime.of(21,0)));
    }

    @Test
    public void testIsAvailable(){
        assertThat(this.availability.isAvailable(LocalDateTime.now(),LocalDateTime.now()),is(true));
    }
    @Test(expected = RuntimeException.class)
    public void testExceptionForDifferentDates(){
        this.availability.isAvailable(LocalDateTime.of(2018,04,05,5,0)
                ,LocalDateTime.of(2018,04,06,5,0));
    }

//    @Test
//    public void test(){
//        LocalDate ld = LocalDate.of(2018,4,1);
//        System.out.println(ld.getDayOfWeek().getValue());
//        System.out.println(ld.getDayOfWeek().name());
//    }

}
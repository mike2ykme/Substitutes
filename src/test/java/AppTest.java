import com.icrn.substitutes.Controller;
import com.icrn.substitutes.Exceptions.SchedulingException;
import com.icrn.substitutes.dao.RequestRepositoryInMemory;
import com.icrn.substitutes.dao.SubstituteRepositoryInMemory;
import com.icrn.substitutes.dao.UserRepositoryInMemory;
import com.icrn.substitutes.model.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.icrn.substitutes.model.enumerations.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AppTest {

    Controller controller;
    Request testRequest;
    LocalDateTime testHoliday;
    LocalDateTime testNotHolidayStart;
    LocalDateTime testNotHolidayEnd;
    AvailabilitySet testAvailabilitySet;
    Availability testAvailability;
    Substitute testSubstitute;
    UserInterface testUser;

    @Before
    public void before(){

        this.controller = new Controller(
                new RequestRepositoryInMemory(),
                new SubstituteRepositoryInMemory(),
                new UserRepositoryInMemory());



        testHoliday = LocalDateTime.of(2011,11,11,11,11);
        testNotHolidayStart = LocalDateTime.of(2018,4,5,9,0);
        testNotHolidayEnd = LocalDateTime.of(2018,4,5,17,0);

        testAvailabilitySet = new AvailabilitySet();
        testAvailability = new Availability();
        testAvailabilitySet.addDay(LocalDate.of(2011,11,11));
            for(int i=0; i <=5;i++){
                testAvailability.addAvailabilityTime(i,new StartEnd(LocalTime.of(5,0),
                    LocalTime.of(17,0)));
            }


        testSubstitute = new Substitute();
        testSubstitute.setId(1234567l);
        testSubstitute.setName("tester");
        testSubstitute.setAddress("123 Fake Street");
        testSubstitute.setContactNumber("1234567890");
        testSubstitute.setHolidayAvailability(testAvailabilitySet);
        testSubstitute.setRegularAvailability(testAvailability);
        testSubstitute.setScheduledTimes(new AvailabilitySet());

        testUser = new User();
        testUser.setId(1111111111L);

        testRequest = new Request();
        testRequest.setRequestId(123456789L);
        testRequest.setStatus(Status.OPEN);
        testRequest.setStartTime(testNotHolidayStart);
        testRequest.setEndTime(testNotHolidayEnd);
        testRequest.setRequestorId(testUser.getId());

    }
    @Test
    public void testJUnit(){
        assertEquals(true,true);
    }
    @Test
    public void testGetAllRequestsNotNull(){
        assertThat(this.controller.getAllRequests(),is(not(nullValue())));
    }
    @Test
    public void testAddRequest(){
        Request request = new Request();
        request.setRequestId(1234567890l);
        request.setRequestorId(987654321l);
        assertEquals(request,this.controller.saveRequest(request));
        assertThat(request,equalTo(this.controller.saveRequest(request)));
        assertThat(request,is(equalTo(this.controller.saveRequest((request)))));
    }
    @Test
    public void verifyRetrieveByRequestorId(){
        Request request = new Request();
        request.setRequestId(1234567890l);
        request.setRequestorId(987654321l);

        Request request2 = new Request();
        request.setRequestId(55555555555l);
        request.setRequestorId(5555555555555l);

        this.controller.saveRequest(request);
        assertThat(controller.getRequestByRequestorId(request.getRequestorId()),is(not(nullValue())));
        assertThat(controller.getRequestByRequestorId(request.getRequestorId()),not(hasItem(request2)));
    }
    @Test
    public void verifyUpdate(){
        Request request = new Request();
        request.setRequestId(1234567890l);
        request.setRequestorId(987654321l);

        assertThat(this.controller.saveRequest(request),equalTo(request));
        Request request2 = new Request();
        request2.setRequestId(1234567890l);
        request.setRequestorId(5555555555555l);
        request2.setRequestorId(5555555555555l);

        assertThat(this.controller.saveRequest(request),equalTo(request2));

    }
    @Test
    public void getByRequestId(){
        Request request = new Request();
        request.setRequestId(1234567890l);
        request.setRequestorId(987654321l);
        this.controller.saveRequest(request);

        assertThat(this.controller.getRequestByRequestId(1234567890l).get(),is(equalTo(request)));

    }
    @Test
    public void getAllUsersTest(){
        assertThat(this.controller.getAllUsers(),is(not(nullValue())));
        this.controller.saveUser(this.testUser);
        assertThat(this.controller.getUserById(testUser.getId()).get(),is(this.testUser));
        assertThat(this.controller.getAllUsers(),hasItem(testUser));
    }
    @Test
    public void getAvailableOnDateEmpty(){
        LocalDateTime ldtNow = LocalDateTime.now();
        assertThat(controller.getSubstitutesAvailableOnDateTime(ldtNow,ldtNow), is(not(nullValue())));

    }
    @Test
    public void getAllSubstitutesNotNull(){
        assertThat(this.controller.getAllSubstitutes(),is(not(nullValue())));
    }
    @Test
    public void verifySubValidCreationPath(){
        Substitute sub = new Substitute();
        this.controller.saveSubstitute(sub);
        assertThat(this.controller.getAllSubstitutes().get(0).getId(),is(not(nullValue())));
//        System.out.println(sub.getId());
    }
    @Test
    public void verifyHolidayAvailabilityOfNewSubstitute(){
        AvailabilitySet availabilitySet = new AvailabilitySet();
        Availability availability = new Availability();
        LocalDateTime aHoliday = LocalDateTime.of(2011,11,
                11,11,11);
        LocalDateTime notHolidayStart = LocalDateTime.of(2018,4,5,9,0);
        LocalDateTime notHolidayEnd = LocalDateTime.of(2018,4,5,17,0);
        availabilitySet.addDay(LocalDate.of(2011,11,11));
        for(int i=0; i <=5;i++){
            availability.addAvailabilityTime(i,new StartEnd(LocalTime.of(5,0),
                    LocalTime.of(17,0)));
        }

        Substitute sub = new Substitute();
        sub.setId(1234567l);
        sub.setName("tester");
        sub.setAddress("123 Fake Street");
        sub.setContactNumber("1234567890");

        sub.setHolidayAvailability(availabilitySet);
        sub.setRegularAvailability(availability);
        sub.setScheduledTimes(new AvailabilitySet());

        this.controller.saveSubstitute(sub);
        assertThat(this.controller.getAllSubstitutes().get(0).getId(),is(not(nullValue())));
        assertThat(this.controller.getSubstitutesAvailableOnDateTime(aHoliday,aHoliday).isEmpty()
                ,is(true));
        assertThat(this.controller.getSubstitutesAvailableOnDateTime(notHolidayStart,notHolidayEnd).isEmpty(),
                is(false));
        assertThat(this.controller.getSubstitutesAvailableOnDateTime(notHolidayStart,notHolidayEnd),
                hasItem(sub));
    }
    @Test
    public void testRequestProcessOnlyAllowsBookingOnce(){
        AvailabilitySet availabilitySet = new AvailabilitySet();
        Availability availability = new Availability();
        LocalDateTime aHoliday = LocalDateTime.of(2011,11,
                11,11,11);
        LocalDateTime notHolidayStart = LocalDateTime.of(2018,4,5,9,0);
        LocalDateTime notHolidayEnd = LocalDateTime.of(2018,4,5,17,0);
        availabilitySet.addDay(LocalDate.of(2011,11,11));
        for(int i=0; i <=5;i++){
            availability.addAvailabilityTime(i,new StartEnd(LocalTime.of(5,0),
                    LocalTime.of(17,0)));
        }

        Substitute sub = new Substitute();
        sub.setId(1234567l);
        sub.setName("tester");
        sub.setAddress("123 Fake Street");
        sub.setContactNumber("1234567890");

        sub.setHolidayAvailability(availabilitySet);
        sub.setRegularAvailability(availability);
        sub.setScheduledTimes(new AvailabilitySet());

        UserInterface user = new User();
        user.setId(1111111111L);
        //Add a substitute
        assertThat(this.controller.saveSubstitute(sub),is(not(nullValue())));
        assertThat(this.controller.getSubstitutesAvailableOnDateTime(notHolidayStart,notHolidayEnd).isEmpty(),
                is(not(nullValue())));
        //make sure we can find a substitute user for a specific time
        assertThat(this.controller.getSubstitutesAvailableOnDateTime(notHolidayStart,notHolidayEnd),
                hasItem(sub));
        List<Substitute> substituteList = this.controller.getSubstitutesAvailableOnDateTime(notHolidayStart,notHolidayEnd);

        //make request and ensure it is scheduled. We then need to ensure we don't double book someone
        Request request = null;
        try{
            request = this.controller.scheduleSubstitute(user,substituteList.get(0),notHolidayStart,notHolidayEnd);
            assertThat(this.controller.getSubstitutesAvailableOnDateTime(notHolidayStart,notHolidayEnd).isEmpty(),is(true));
            assertThat(request,is(not(nullValue())));

        }catch (SchedulingException e){
            e.printStackTrace();
            fail("Should not have thrown an exception");
        }
        /*
        * Using method for trying exception from the below resource
        * https://memorynotfound.com/junit-exception-testing/
         */
        try {
            Request request1 = this.controller.scheduleSubstitute(user,substituteList.get(0),notHolidayStart,notHolidayEnd);
            fail("Should have thrown an exception as this Substitute isn't available because they would be double booked");
        }catch (SchedulingException e){
            assertThat(e.getMessage(),containsString("Unable to schedule Substitute"));
            assertThat(e,instanceOf(SchedulingException.class));
        }
        assertThat(this.controller.getAllRequests(),hasItem(request));


    }
    @Test
    public void verifyScheduleByOpenRequest(){

        this.testRequest.setSubstituteId(this.testSubstitute.getId());

        /*
         * Using method for trying exception from the below resource
         * https://memorynotfound.com/junit-exception-testing/
         */
        this.controller.saveRequest(this.testRequest);
        this.controller.saveSubstitute(this.testSubstitute);
        this.controller.saveUser(this.testUser);

        assertThat(this.controller.getRequestsByStatus(Status.OPEN),hasItem(testRequest));
        assertThat(this.controller.getRequestsByStatus(Status.CANCELLED).isEmpty(),is(true));
        assertThat(this.controller.getSubstitutesAvailableOnDateTime(testNotHolidayStart,testNotHolidayEnd),
                hasItem(this.testSubstitute));

        Request request = this.controller.getRequestsByStatus(Status.OPEN).get(0);
        Substitute substitute = this.controller.getSubstitutesAvailableOnDateTime(
                testNotHolidayStart,testNotHolidayEnd).get(0);
        try {
            Request returnedRequest = this.controller.scheduleSubstitute(request);
            assertThat(returnedRequest.getStatus(),is(Status.SCHEDULED));
            assertThat(returnedRequest.getRequestId(),is(request.getRequestId()));
            assertThat(this.controller.getSubstituteById(returnedRequest.getSubstituteId()).get().getAllRequestIds(),
                    hasItem(returnedRequest.getRequestId()));
        }catch (SchedulingException e){
            fail(e.getMessage());
        }
        assertThat(this.controller.getRequestsByStatus(Status.SCHEDULED).isEmpty(),is(false));
        assertThat(this.controller.getRequestsByStatus(Status.SCHEDULED).get(0).getSubstituteId(),
                is(testSubstitute.getId()));
    }
    @Test
    public void getSubstituteByIdTest(){
        this.controller.saveSubstitute(this.testSubstitute);
        assertThat(this.controller.getSubstituteById(this.testSubstitute.getId()).get().getId(),is(this.testSubstitute.getId()));
    }
    @Test
    public void updateExistingScheduledRequest(){
        this.testRequest.setSubstituteId(this.testSubstitute.getId());
        Request returnedRequest = this.controller.saveRequest(this.testRequest);
        this.controller.saveSubstitute(this.testSubstitute);
        this.controller.saveUser(this.testUser);


        try {
            returnedRequest = this.controller.scheduleSubstitute(returnedRequest);
        }catch (SchedulingException e){
            fail(e.getMessage());
        }
        assertThat(this.testRequest.getRequestId(), is(returnedRequest.getRequestId()));

        try {
            returnedRequest = this.controller.cancelRequest(returnedRequest.getRequestId());
            assertThat(this.controller.getRequestsByStatus(Status.CANCELLED).isEmpty(),is(false));
            assertThat(this.controller.getRequestsByStatus(Status.SCHEDULED).isEmpty(),is(true));


        }catch (SchedulingException e){
            fail(e.getMessage());
        }

        assertThat(this.controller.getSubstitutesAvailableOnDateTime(
                returnedRequest.getStartTime(),returnedRequest.getEndTime()).isEmpty(),
                is(false));

        try {
            returnedRequest = this.controller.scheduleSubstitute(returnedRequest);
            assertThat(this.controller.getSubstitutesAvailableOnDateTime(
                    returnedRequest.getStartTime(),returnedRequest.getEndTime()).isEmpty(),
                    is(true));
            assertThat(this.controller.getRequestsByStatus(Status.CANCELLED).isEmpty(),is(true));
            assertThat(this.controller.getRequestsByStatus(Status.SCHEDULED).isEmpty(),is(false));
            returnedRequest = this.controller.completeRequest(returnedRequest.getRequestId());
            assertThat(this.controller.getRequestsByStatus(Status.OPEN).isEmpty(), is(true));
            assertThat(this.controller.getRequestsByStatus(Status.COMPLETED),hasItem(returnedRequest));
        }catch (SchedulingException e){
            fail(e.getMessage());
        }

    }
}
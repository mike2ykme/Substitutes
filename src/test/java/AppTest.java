import com.icrn.substitutes.Controller;
import com.icrn.substitutes.dao.RequestRepositoryInMemory;
import com.icrn.substitutes.dao.SubstituteRepositoryInMemory;
import com.icrn.substitutes.model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
public class AppTest {

    Controller controller;

    @Before
    public void before(){

        this.controller = new Controller(new RequestRepositoryInMemory(), new SubstituteRepositoryInMemory());
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
        assertEquals(request,this.controller.addRequest(request));
        assertThat(request,equalTo(this.controller.addRequest(request)));
        assertThat(request,is(equalTo(this.controller.addRequest((request)))));
    }

    @Test
    public void verifyRetrieveByRequestorId(){
        Request request = new Request();
        request.setRequestId(1234567890l);
        request.setRequestorId(987654321l);

        Request request2 = new Request();
        request.setRequestId(55555555555l);
        request.setRequestorId(5555555555555l);

        this.controller.addRequest(request);
        assertThat(controller.getRequestByRequestorId(request.getRequestorId()),is(not(nullValue())));
        assertThat(controller.getRequestByRequestorId(request.getRequestorId()),not(hasItem(request2)));

    }
    @Test
    public void verifyUpdate(){
        Request request = new Request();
        request.setRequestId(1234567890l);
        request.setRequestorId(987654321l);

        assertThat(this.controller.addRequest(request),equalTo(request));
        Request request2 = new Request();
        request2.setRequestId(1234567890l);
        request.setRequestorId(5555555555555l);
        request2.setRequestorId(5555555555555l);

        assertThat(this.controller.addRequest(request),equalTo(request2));

    }

    @Test
    public void getByRequestId(){
        Request request = new Request();
        request.setRequestId(1234567890l);
        request.setRequestorId(987654321l);
        this.controller.addRequest(request);

        assertThat(this.controller.getRequestByRequestId(1234567890l).get(),is(equalTo(request)));

    }

    @Test
    public void getAvailableOnDateEmpty(){
        LocalDateTime ldtNow = LocalDateTime.now();
        assertThat(controller.getSubstitutesAvailableOnDateTime(ldtNow,ldtNow), is(not(nullValue())));

    }

    @Test
    public void getAllSunbstitutesNotNull(){
        assertThat(this.controller.getAllSubstitutes(),is(not(nullValue())));
    }

    @Test
    public void verifySubValidCreationPath(){
        Substitute sub = new Substitute();
        this.controller.addSubstitute(sub);
        assertThat(this.controller.getAllSubstitutes().get(0).getId(),is(not(nullValue())));
//        System.out.println(sub.getId());
    }

    @Test
    public void verifyHolidayAvailabilityOfNewSubstitute(){
        HolidayAvailability holidayAvailability = new HolidayAvailability();
        Availability availability = new Availability();
        LocalDateTime aHoliday = LocalDateTime.of(2011,11,
                11,11,11);
        LocalDateTime notHolidayStart = LocalDateTime.of(2018,4,5,9,0);
        LocalDateTime notHolidayEnd = LocalDateTime.of(2018,4,5,17,0);
        holidayAvailability.addHoliday(LocalDate.of(2011,11,11));
        for(int i=0; i <=5;i++){
            availability.addAvailabilityTime(i,new StartEnd(LocalTime.of(5,0),
                    LocalTime.of(17,0)));
        }

        Substitute sub = new Substitute();
        sub.setId(1234567l);
        sub.setName("tester");
        sub.setAddress("123 Fake Street");
        sub.setContactNumber("1234567890");

        sub.setHolidayAvailability(holidayAvailability);
        sub.setRegularAvailability(availability);

        this.controller.addSubstitute(sub);
        assertThat(this.controller.getAllSubstitutes().get(0).getId(),is(not(nullValue())));
        assertThat(this.controller.getSubstitutesAvailableOnDateTime(aHoliday,aHoliday).isEmpty()
                ,is(true));
        assertThat(this.controller.getSubstitutesAvailableOnDateTime(notHolidayStart,notHolidayEnd).isEmpty(),
                is(false));
        assertThat(this.controller.getSubstitutesAvailableOnDateTime(notHolidayStart,notHolidayEnd),
                hasItem(sub));
    }
}

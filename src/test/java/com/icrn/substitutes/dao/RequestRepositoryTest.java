package com.icrn.substitutes.dao;

import com.icrn.substitutes.model.Request;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class RequestRepositoryTest {
    RequestRepository requestRepository;
    RequestRepository requestRepository2;
    @Before
    public void setup(){
        requestRepository   = new RequestRepositoryInMemory();
        requestRepository2 = new RequestRepositoryInMemory(new ConcurrentHashMap<>());

    }
    @Test
    public void getRequestById() {
    }

    @Test
    public void getAllRequests() {
    }

    @Test
    public void getAllRequestsByRequestorId() {
    }

    @Test
    public void saveRequest() {
    }

    @Test
    public void getAllRequestsBySubstituteId(){
        List<Request> requests = this.requestRepository.getRequestsBySubstituteId(0000000000L);
        assertThat(requests, is(not(nullValue())));
        assertThat(requests.isEmpty(),is(true));

        Request request = new Request();
        request.setSubstituteId(123456789L);
        request.setRequestorId(987654321L);
        request.setEndTime(LocalDateTime.now());
        request.setStartTime(LocalDateTime.now());

        Request request2 = this.requestRepository.saveRequest(request);
        assertThat(request2,is(notNullValue()));
        assertThat(this.requestRepository.getAllRequests().isEmpty(),is(false));
        assertThat(this.requestRepository.getAllRequests().get(0).getRequestId(),is(not(0)));
        assertThat(this.requestRepository.getAllRequests().get(0).getRequestId(),is(not(nullValue())));


    }
}
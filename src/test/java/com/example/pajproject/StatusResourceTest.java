package com.example.pajproject;

import com.example.pajproject.EJB.StatusService;
import com.example.pajproject.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatusResourceTest {
    private StatusService statusService;

    @BeforeEach
    void setUp() {
        statusService = mock(StatusService.class);
    }

    @Test
    void getAllStatuses() {
        Status status1 = new Status();
        status1.setId(1L);
        Status status2 = new Status();
        status2.setId(2L);

        List<Status> statuses = List.of(status1, status2);

        when(statusService.getAllStatuses()).thenReturn(statuses);
    }
}
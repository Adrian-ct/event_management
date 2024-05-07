package com.example.pajproject.DTO;

import com.example.pajproject.model.Event;

public class EventAttendance {
    private Event event;
    private boolean attending;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isAttending() {
        return attending;
    }

    public void setAttending(boolean attending) {
        this.attending = attending;
    }
}

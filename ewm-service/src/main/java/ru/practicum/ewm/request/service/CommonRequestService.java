package ru.practicum.ewm.request.service;

import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.request.model.Request;

import java.util.List;

public interface CommonRequestService {
    List<Request> getConfirmedRequests(List<Event> events);
}

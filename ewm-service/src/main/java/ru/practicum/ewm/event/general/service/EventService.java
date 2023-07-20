package ru.practicum.ewm.event.general.service;

import ru.practicum.ewm.event.general.dto.update.UpdateEventRequest;
import ru.practicum.ewm.event.general.model.Event;

import java.util.List;
import java.util.Map;

public interface EventService {

    Event update(Event eventToUpdate, UpdateEventRequest updateEventRequest);

    Map<Long, Long> getStats(List<Event> events, Boolean unique);

}

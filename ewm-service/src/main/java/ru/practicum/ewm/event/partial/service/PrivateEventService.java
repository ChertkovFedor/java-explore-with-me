package ru.practicum.ewm.event.partial.service;

import ru.practicum.ewm.event.general.dto.CreateEventDto;
import ru.practicum.ewm.event.general.dto.EventDto;
import ru.practicum.ewm.event.general.dto.update.UpdateEventRequest;

import java.util.List;

public interface PrivateEventService {
    EventDto create(Long userId, CreateEventDto createEventDto);

    EventDto update(Long userId, Long eventId, UpdateEventRequest updateEventUserRequest);

    EventDto get(Long userId, Long eventId);

    List<EventDto> getAll(Long userId, long from, int size);
}

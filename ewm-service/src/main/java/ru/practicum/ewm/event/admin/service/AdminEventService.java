package ru.practicum.ewm.event.admin.service;

import ru.practicum.ewm.event.general.dto.EventDto;
import ru.practicum.ewm.event.general.dto.update.UpdateEventRequest;
import ru.practicum.ewm.util.enums.State;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {

    List<EventDto> adminEventSearch(List<Long> users,
                                    List<State> states,
                                    List<Long> categories,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    Long from,
                                    Integer size);

    EventDto update(Long eventId, UpdateEventRequest updateEventAdminRequest);

}

package ru.practicum.ewm.event.common.service;

import ru.practicum.ewm.event.general.dto.EventDto;
import ru.practicum.ewm.util.enums.EventSortTypes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {
    EventDto get(Long id, HttpServletRequest request);

    List<EventDto> search(String text,
                          List<Long> categories,
                          Boolean paid,
                          LocalDateTime rangeStart,
                          LocalDateTime rangeEnd,
                          Boolean onlyAvailable,
                          EventSortTypes sort,
                          long from,
                          int size,
                          String ip);
}

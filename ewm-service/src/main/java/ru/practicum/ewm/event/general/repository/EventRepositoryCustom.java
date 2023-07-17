package ru.practicum.ewm.event.general.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.util.enums.State;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepositoryCustom {

    List<Event> adminEventSearch(List<Long> users,
                                 List<State> states,
                                 List<Long> categories,
                                 LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd,
                                 Long from,
                                 Integer size);

    List<Event> publicEventSearch(String text,
                                  List<Long> categories,
                                  Boolean paid,
                                  LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd,
                                  Long from,
                                  Integer size);
}

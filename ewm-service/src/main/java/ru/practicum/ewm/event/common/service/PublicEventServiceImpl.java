package ru.practicum.ewm.event.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.general.dto.EventDto;
import ru.practicum.ewm.event.general.mapper.EventMapper;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.event.general.repository.EventRepository;
import ru.practicum.ewm.event.general.service.EventService;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.service.CommonRequestService;
import ru.practicum.ewm.util.enums.EventSortTypes;
import ru.practicum.ewm.util.enums.State;
import ru.practicum.ewm.util.exception.EventNotFoundException;
import ru.practicum.ewm.util.exception.NotFoundException;
import ru.practicum.ewm.util.validator.Validator;
import ru.practicum.client.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.ewm.event.general.mapper.EventMapper.toDto;
import static ru.practicum.ewm.util.enums.Constants.APP_NAME;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepository eRep;
    private final StatsClient statsClient;
    private final Validator valid;
    private final EventService eServ;
    private final CommonRequestService crServ;

    @Override
    public EventDto get(Long id, HttpServletRequest request) {

        Event eventToReturn = valid.getEventIfExist(id);
        if (!State.PUBLISHED.equals(eventToReturn.getState()))
            throw new NotFoundException(id, Event.class.getSimpleName());

        EventDto eventDto = toDto(eventToReturn);

        Map<Long, Long> views = eServ.getStats(List.of(eventToReturn), true);
        eventDto.setViews(views.get(eventToReturn.getId()));

        List<Request> confirmedRequests = crServ.getConfirmedRequests(List.of(eventToReturn));
        eventDto.setConfirmedRequests(confirmedRequests.size());

        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        statsClient.create(APP_NAME, uri, ip, LocalDateTime.now());
        return eventDto;

    }

    @Override
    public List<EventDto> search(String text,
                                 List<Long> categories,
                                 Boolean paid,
                                 LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd,
                                 Boolean onlyAvailable,
                                 EventSortTypes sort,
                                 long from,
                                 int size,
                                 String ip) {

        List<Event> foundEvents = eRep.publicEventSearch(text, categories, paid, rangeStart, rangeEnd, from, size);
        if (foundEvents.isEmpty())
            throw new EventNotFoundException("List Events cannot be empty");

        List<EventDto> eventsDto = foundEvents.stream()
                .map(EventMapper::toDto)
                .collect(Collectors.toList());

        Map<Long, Long> views = eServ.getStats(foundEvents, false);
        eventsDto.forEach(e -> e.setViews(views.get(e.getId())));

        List<Request> confirmedRequests = crServ.getConfirmedRequests(foundEvents);
        for (EventDto eventDto : eventsDto) {
            eventDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(eventDto.getId()))
                    .count());
        }

        LocalDateTime timestamp = LocalDateTime.now();
        statsClient.create(APP_NAME, "/events", ip, timestamp);
        foundEvents.forEach(event -> statsClient.create(APP_NAME, "/events/" + event.getId(), ip, timestamp));

        if (EventSortTypes.VIEWS.equals(sort))
            return eventsDto.stream()
                    .sorted(Comparator.comparing(EventDto::getViews))
                    .collect(Collectors.toList());
        return eventsDto.stream()
                .sorted(Comparator.comparing(EventDto::getEventDate))
                .collect(Collectors.toList());
    }
}

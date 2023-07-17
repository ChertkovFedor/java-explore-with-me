package ru.practicum.ewm.event.general.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.ewm.event.general.dto.update.UpdateEventRequest;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.util.enums.State;
import ru.practicum.ewm.util.exception.AccessException;
import ru.practicum.ewm.util.exception.InvalidEventDateException;
import ru.practicum.ewm.util.exception.InvalidOperationException;
import ru.practicum.ewm.util.validator.Validator;
import ru.practicum.client.StatsClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.ewm.util.enums.Constants.DATE_TIME_FORMATTER;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final Validator valid;
    private final StatsClient statsClient;

    @Override
    public Event update(Event eventToUpdate, UpdateEventRequest updateEventRequest) {
        if (State.PUBLISHED.equals(eventToUpdate.getState()))
            throw new InvalidOperationException("Invalid state");
        if (updateEventRequest.getAnnotation() != null)
            eventToUpdate.setAnnotation(updateEventRequest.getAnnotation());
        if (updateEventRequest.getCategory() != null)
            eventToUpdate.setCategory(valid.getCategoryIfExist(updateEventRequest.getCategory()));
        if (updateEventRequest.getDescription() != null)
            eventToUpdate.setDescription(updateEventRequest.getDescription());
        if (updateEventRequest.getEventDate() != null) {
            if (updateEventRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(2)))
                throw new InvalidEventDateException("Invalid date");
            eventToUpdate.setEventDate(updateEventRequest.getEventDate());
        }
        if (updateEventRequest.getLocation() != null)
            eventToUpdate.setLocation(updateEventRequest.getLocation());
        if (updateEventRequest.getPaid() != null)
            eventToUpdate.setPaid(updateEventRequest.getPaid());
        if (updateEventRequest.getParticipantLimit() != null)
            eventToUpdate.setParticipantLimit(updateEventRequest.getParticipantLimit());
        if (updateEventRequest.getRequestModeration() != null)
            eventToUpdate.setRequestModeration(updateEventRequest.getRequestModeration());
        if (updateEventRequest.getTitle() != null)
            eventToUpdate.setTitle(updateEventRequest.getTitle());
        return eventToUpdate;
    }

    @Override
    public Map<Long, Long> getStats(List<Event> events, Boolean unique) {

        Optional<LocalDateTime> start = events.stream()
                .map(Event::getPublishedOn)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo);

        if (start.isEmpty())
            return new HashMap<>();

        LocalDateTime timestamp = LocalDateTime.now();
        List<Long> ids = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        String startTime = start.get().format(DATE_TIME_FORMATTER);
        String endTime = timestamp.format(DATE_TIME_FORMATTER);
        List<String> uris = ids.stream()
                .map(id -> "/events/" + id)
                .collect(Collectors.toList());

        ResponseEntity<Object> response = statsClient.get(startTime, endTime, uris, unique);
        List<ViewStatsDto> stats;
        ObjectMapper mapper = new ObjectMapper();
        try {
            stats = Arrays.asList(mapper.readValue(mapper.writeValueAsString(response.getBody()), ViewStatsDto[].class));
        } catch (IOException e) {
            throw new AccessException("Access error");
        }

        Map<Long, Long> views = new HashMap<>();
        for (Long id : ids) {
            Optional<Long> viewsOptional = stats.stream()
                    .filter(s -> s.getUri().equals("/events/" + id))
                    .map(ViewStatsDto::getHits)
                    .findFirst();
            Long eventViews = viewsOptional.orElse(0L);
            views.put(id, eventViews);
        }

        return views;
    }

}

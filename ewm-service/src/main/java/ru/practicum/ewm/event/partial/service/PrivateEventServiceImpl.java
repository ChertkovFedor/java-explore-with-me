package ru.practicum.ewm.event.partial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.general.model.Category;
import ru.practicum.ewm.event.general.dto.CreateEventDto;
import ru.practicum.ewm.event.general.dto.EventDto;
import ru.practicum.ewm.event.general.dto.update.UpdateEventRequest;
import ru.practicum.ewm.event.general.mapper.EventMapper;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.event.general.repository.EventRepository;
import ru.practicum.ewm.event.general.service.EventService;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.service.CommonRequestService;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.util.enums.State;
import ru.practicum.ewm.util.enums.StateAction;
import ru.practicum.ewm.util.exception.InvalidEventDateException;
import ru.practicum.ewm.util.exception.InvalidOperationException;
import ru.practicum.ewm.util.exception.NotFoundException;
import ru.practicum.ewm.util.validator.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.ewm.event.general.mapper.EventMapper.toModel;
import static ru.practicum.ewm.event.general.mapper.EventMapper.toDto;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository eRep;
    private final Validator valid;
    private final EventService eServ;
    private final CommonRequestService crServ;

    @Override
    public EventDto create(Long userId, CreateEventDto eventDto) {
        if (eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2)))
            throw new InvalidEventDateException("Invalid event date");
        Event event = toModel(eventDto);
        Category category = valid.getCategoryIfExist(eventDto.getCategory());
        event.setCategory(category);
        User initiator = valid.getUserIfExist(userId);
        event.setInitiator(initiator);
        event.setState(State.PENDING);
        event.setCreatedOn(LocalDateTime.now());
        return toDto(eRep.save(event));
    }

    @Override
    public EventDto update(Long userId, Long eventId, UpdateEventRequest updateEventUserRequest) {
        Event eventToUpdate = valid.getEventIfExist(eventId);
        if (!Objects.equals(eventToUpdate.getInitiator().getId(), userId))
            throw new InvalidOperationException("Event could be updated only by initiator");
        Event event = eServ.update(eventToUpdate, updateEventUserRequest);
        StateAction updatedEventStateAction = updateEventUserRequest.getStateAction();
        if (updatedEventStateAction == null)
            return toDto(eRep.save(event));
        switch (updateEventUserRequest.getStateAction()) {
            case SEND_TO_REVIEW:
                event.setState(State.PENDING);
                break;
            case CANCEL_REVIEW:
                event.setState(State.CANCELED);
                break;
            default:
                throw new InvalidOperationException("Invalid state");
        }
        return toDto(eRep.save(event));
    }

    @Override
    public EventDto get(Long userId, Long eventId) {
        Event event = valid.getEventIfExist(eventId);
        if (!Objects.equals(event.getInitiator().getId(), userId))
            throw new NotFoundException(eventId, Event.class.getSimpleName());
        EventDto eventDto = toDto(event);
        if (State.PUBLISHED.equals(eventDto.getState())) {
            Map<Long, Long> views = eServ.getStats(List.of(event), false);
            eventDto.setViews(views.get(event.getId()));

            List<Request> confirmedRequests = crServ.getConfirmedRequests(List.of(event));
            eventDto.setConfirmedRequests(confirmedRequests.size());
        }
        return eventDto;
    }

    @Override
    public List<EventDto> getAll(Long userId, long from, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        List<Event> foundEvents = eRep.findAllByIdIsGreaterThanEqualAndInitiatorIdIs(from, userId, pageRequest);

        List<EventDto> eventsDto = foundEvents.stream()
                .map(EventMapper::toDto)
                .collect(Collectors.toList());

        Map<Long, Long> views = eServ.getStats(foundEvents, false);
        if (!views.isEmpty())
            eventsDto.forEach(e -> e.setViews(views.get(e.getId())));

        List<Request> confirmedRequests = crServ.getConfirmedRequests(foundEvents);
        for (EventDto fullDto : eventsDto) {
            fullDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(fullDto.getId()))
                    .count());
        }

        return eventsDto;
    }

}

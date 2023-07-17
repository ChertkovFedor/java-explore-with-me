package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.dto.RequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.RequestStatusUpdateResult;
import ru.practicum.ewm.request.mapper.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.util.enums.RequestState;
import ru.practicum.ewm.util.enums.State;
import ru.practicum.ewm.util.exception.InvalidOperationException;
import ru.practicum.ewm.util.validator.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.ewm.request.mapper.RequestMapper.toDto;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository rRep;
    private final Validator valid;
    private final CommonRequestService crServ;

    @Override
    public RequestDto create(Long userId, Long eventId) {
        User requester = valid.getUserIfExist(userId);
        Event event = valid.getEventIfExist(eventId);

        Optional<Request> optionalRequest = rRep.findFirstByEventIdIsAndRequesterIdIs(event.getId(), requester.getId());

        optionalRequest.ifPresent(r -> {throw new InvalidOperationException("Request already exist");});

        if (event.getInitiator().equals(requester))
            throw new InvalidOperationException("Request could not be created by event initiator");

        if (!State.PUBLISHED.equals(event.getState()))
            throw new InvalidOperationException("Event is not published");

        List<Request> confirmedRequests = crServ.getConfirmedRequests(List.of(event));
        if (event.getParticipantLimit() != 0 && confirmedRequests.size() == event.getParticipantLimit())
            throw new InvalidOperationException("Limit over");

        Request request = Request.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(requester)
                .build();

        if (!event.isRequestModeration() || event.getParticipantLimit() == 0)
            request.setStatus(RequestState.CONFIRMED);
        else
            request.setStatus(RequestState.PENDING);

        return toDto(rRep.save(request));
    }

    @Override
    public RequestDto cancel(Long userId, Long requestId) {
        User requester = valid.getUserIfExist(userId);
        Request request = valid.getRequestIfExist(requestId);
        if (!request.getRequester().equals(requester))
            throw new InvalidOperationException("You can not cancel this request");
        request.setStatus(RequestState.CANCELED);
        return toDto(rRep.save(request));
    }

    @Override
    public List<RequestDto> getAllUsersRequests(Long userId) {
        List<Request> foundRequests = rRep.findAllByRequesterIdIs(userId);
        return foundRequests.stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> getEventRequests(Long userId, Long eventId) {
        User initiator = valid.getUserIfExist(userId);
        Event event = valid.getEventIfExist(eventId);
        if (!event.getInitiator().equals(initiator))
            throw new InvalidOperationException("User is not event initiator");
        List<Request> foundRequests = rRep.findAllByEventIdIs(eventId);
        return foundRequests.stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestStatusUpdateResult update(Long userId, Long eventId, RequestStatusUpdateRequest requestStatusUpdateRequest) {
        User initiator = valid.getUserIfExist(userId);
        Event event = valid.getEventIfExist(eventId);
        if (!event.getInitiator().equals(initiator))
            throw new InvalidOperationException("User is not event initiator");

        if (!event.isRequestModeration() || event.getParticipantLimit() == 0)
            throw new InvalidOperationException("Request does not need confirmation");

        int confirmedRequests = crServ.getConfirmedRequests(List.of(event)).size();

        List<Request> requestsToUpdate = rRep.findAllByIdIn(requestStatusUpdateRequest.getRequestIds());
        RequestStatusUpdateResult requestStatusUpdateResult = new RequestStatusUpdateResult();
        List<RequestDto> requestsDto;
        switch (requestStatusUpdateRequest.getStatus()) {
            case REJECTED:
                for (Request request : requestsToUpdate) {
                    if (!RequestState.PENDING.equals(request.getStatus()))
                        throw new InvalidOperationException("Request must be in PENDING");
                    request.setStatus(RequestState.REJECTED);
                }
                requestsDto = rRep.saveAll(requestsToUpdate).stream()
                        .map(RequestMapper::toDto)
                        .collect(Collectors.toList());
                requestStatusUpdateResult.setRejectedRequests(requestsDto);
                break;
            case CONFIRMED:
                for (Request request : requestsToUpdate) {
                    if (!RequestState.PENDING.equals(request.getStatus()))
                        throw new InvalidOperationException("Request must be in PENDING");
                    if (confirmedRequests == event.getParticipantLimit())
                        throw new InvalidOperationException("Limit is over");
                    request.setStatus(RequestState.CONFIRMED);
                    confirmedRequests++;
                }
                requestsDto = rRep.saveAll(requestsToUpdate).stream()
                        .map(RequestMapper::toDto)
                        .collect(Collectors.toList());
                requestStatusUpdateResult.setConfirmedRequests(requestsDto);
                break;
            default:
                throw new InvalidOperationException("Invalid status");
        }
        return requestStatusUpdateResult;
    }
}

package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.util.enums.RequestState;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonRequestServiceImpl implements CommonRequestService {

    private final RequestRepository rRep;

    @Override
    public List<Request> getConfirmedRequests(List<Event> events) {
        return rRep.findAllByEventInAndStatusIs(events, RequestState.CONFIRMED);
    }
}

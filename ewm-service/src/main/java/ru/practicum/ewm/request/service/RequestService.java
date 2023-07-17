package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.dto.RequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.RequestStatusUpdateResult;

import java.util.List;

public interface RequestService {
    RequestDto create(Long userId, Long eventId);
    RequestDto cancel(Long userId, Long requestId);
    List<RequestDto> getAllUsersRequests(Long userId);
    List<RequestDto> getEventRequests(Long userId, Long eventId);
    RequestStatusUpdateResult update(Long userId, Long eventId, RequestStatusUpdateRequest request);
}

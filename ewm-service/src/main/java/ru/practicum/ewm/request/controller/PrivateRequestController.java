package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.RequestStatusUpdateRequest;
import ru.practicum.ewm.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users/{userId}")
public class PrivateRequestController {

    private final RequestService rServ;

    @PostMapping("/requests")
    public ResponseEntity<Object> create(@PathVariable Long userId,
                                         @RequestParam(name = "eventId") Long eventId) {
        log.info("-- PrivateRequestController -- Create request user {} to event {}", userId, eventId);
        return new ResponseEntity<>(rServ.create(userId, eventId), HttpStatus.CREATED);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancel(@PathVariable Long userId,
                                         @PathVariable Long requestId) {
        log.info("-- PrivateRequestController -- Cancel request {} by user {}", requestId, userId);
        return new ResponseEntity<>(rServ.cancel(userId, requestId), HttpStatus.OK);
    }

    @GetMapping("/requests")
    public ResponseEntity<Object> getAllUsersRequests(@PathVariable Long userId) {
        log.info("-- PrivateRequestController -- Get all requests by user {}", userId);
        return new ResponseEntity<>(rServ.getAllUsersRequests(userId), HttpStatus.OK);
    }

    @GetMapping("/events/{eventId}/requests")
    public ResponseEntity<Object> getEventRequests(@PathVariable Long userId,
                                                   @PathVariable Long eventId) {
        log.info("-- PrivateRequestController -- Get all requests from event {} by initiator {}", eventId, userId);
        return new ResponseEntity<>(rServ.getEventRequests(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/events/{eventId}/requests")
    public ResponseEntity<Object> update(@PathVariable Long userId,
                                         @PathVariable Long eventId,
                                         @RequestBody @NotNull @Valid RequestStatusUpdateRequest request) {
        log.info("-- PrivateRequestController -- Update requests {} to status {}", request.getRequestIds(), request.getStatus());
        return new ResponseEntity<>(rServ.update(userId, eventId, request), HttpStatus.OK);
    }

}

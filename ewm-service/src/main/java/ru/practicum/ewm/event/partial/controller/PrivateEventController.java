package ru.practicum.ewm.event.partial.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.general.dto.CreateEventDto;
import ru.practicum.ewm.event.general.dto.update.UpdateEventUserRequest;
import ru.practicum.ewm.event.partial.service.PrivateEventService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@Slf4j
@Validated
@RequiredArgsConstructor
public class PrivateEventController {

    private final PrivateEventService peServ;

    @PostMapping
    public ResponseEntity<Object> create(@PathVariable Long userId,
                                         @RequestBody @NotNull @Valid CreateEventDto eventDto) {
        log.info("-- PrivateEventController -- Create event {} by user {}", eventDto, userId);
        return new ResponseEntity<>(peServ.create(userId, eventDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@PathVariable Long userId,
                                         @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero long from,
                                         @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        log.info("-- PrivateEventController -- Get events by user {} from {}, page size {}", userId, from, size);
        return new ResponseEntity<>(peServ.getAll(userId, from, size), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> get(@PathVariable Long userId,
                                      @PathVariable Long eventId) {
        log.info("-- PrivateEventController -- Get event {} by user {}", eventId, userId);
        return new ResponseEntity<>(peServ.get(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> update(@PathVariable Long userId,
                                         @PathVariable Long eventId,
                                         @RequestBody @NotNull @Valid UpdateEventUserRequest updateEventUserRequest) {
        log.info("-- PrivateEventController -- Update event {} by user {}", eventId, userId);
        return new ResponseEntity<>(peServ.update(userId, eventId, updateEventUserRequest), HttpStatus.OK);
    }
}

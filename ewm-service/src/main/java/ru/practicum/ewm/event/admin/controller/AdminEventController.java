package ru.practicum.ewm.event.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.admin.service.AdminEventService;
import ru.practicum.ewm.event.general.dto.update.UpdateEventAdminRequest;
import ru.practicum.ewm.util.enums.State;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.util.enums.Constants.DATE_TIME_PATTERN;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/events")
public class AdminEventController {

    private final AdminEventService aeServ;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(value = "users", required = false) List<Long> users,
                                      @RequestParam(value = "states", required = false) List<State> states,
                                      @RequestParam(value = "categories", required = false) List<Long> categories,
                                      @RequestParam(value = "rangeStart", required = false)
                                        @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
                                      @RequestParam(value = "rangeEnd", required = false)
                                        @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
                                      @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Long from,
                                      @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {
        log.info("-- AdminEventController -- Get events by users {}, states {}, categories {}, rangeStart {}, rangeEnd {}",
                users, states, categories, rangeStart, rangeEnd);
        return new ResponseEntity<>(aeServ.adminEventSearch(users, states, categories, rangeStart, rangeEnd, from, size), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> update(@PathVariable Long eventId,
                                         @RequestBody @NotNull @Valid UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("-- AdminEventController -- Update event {}", eventId);
        return new ResponseEntity<>(aeServ.update(eventId, updateEventAdminRequest), HttpStatus.OK);
    }
}

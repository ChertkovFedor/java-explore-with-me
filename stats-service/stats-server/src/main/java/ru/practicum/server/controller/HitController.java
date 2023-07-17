package ru.practicum.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.server.service.HitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class HitController {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final HitService hServ;

    @PostMapping("/hit")
    public ResponseEntity<Object> post(@RequestBody @Valid EndpointHitDto hitDto) {
        log.info("-- HitController -- POST for new EndpointHitDto {}", hitDto);
        return new ResponseEntity<>(hServ.post(hitDto), HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> get(@RequestParam(name = "start") @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
                                      @RequestParam(name = "end") @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
                                      @RequestParam(name = "uris", defaultValue = "") List<String> uris,
                                      @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("-- HitController -- GET stats, start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return new ResponseEntity<>(hServ.get(start, end, uris, unique), HttpStatus.OK);
    }
}

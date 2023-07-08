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
import java.util.Collection;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class HitController {
    private final HitService hServ;

    @PostMapping("/hit")
    public ResponseEntity<Object> post(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        log.info("POST for new EndpointHitDto {}", endpointHitDto);
        return new ResponseEntity<>(hServ.post(endpointHitDto), HttpStatus.CREATED);
    }

    @GetMapping("stats")
    public ResponseEntity<Object> get(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                      @RequestParam(required = false) Collection<String> uris,
                                      @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.info("GET stats, start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return new ResponseEntity<>(hServ.get(start, end, uris, unique), HttpStatus.OK);
    }
}

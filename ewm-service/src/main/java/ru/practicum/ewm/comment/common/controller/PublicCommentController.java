package ru.practicum.ewm.comment.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.common.service.PublicCommentService;
import ru.practicum.ewm.util.enums.SortTypes;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Slf4j
@Validated
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class PublicCommentController {

    private final PublicCommentService pcServ;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(required = false) String text,
                                      @RequestParam(required = false) LocalDateTime start,
                                      @RequestParam(required = false) LocalDateTime end,
                                      @RequestParam(required = false) SortTypes sort) {
        log.info("-- PublicCommentController -- GET comments by text {}, startDate {}, endDate {}, sort {}", text, start, end, sort);
        return new ResponseEntity<>(pcServ.get(text, start, end, sort), HttpStatus.OK);
    }

    @GetMapping("{eventId}")
    public ResponseEntity<Object> getAllByEventId(@PathVariable @Min(0) Long eventId,
                                            @RequestParam(defaultValue = "DESC") SortTypes sort,
                                            @RequestParam(defaultValue = "0", required = false) @Min(0) int from,
                                            @RequestParam(defaultValue = "10", required = false) @Min(1) int size) {
        log.info("-- PublicCommentController -- GET comments eventId {}", eventId);
        return new ResponseEntity<>(pcServ.getAllByEventId(eventId, sort, from, size), HttpStatus.OK);
    }

}
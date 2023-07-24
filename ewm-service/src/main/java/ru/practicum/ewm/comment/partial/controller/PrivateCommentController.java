package ru.practicum.ewm.comment.partial.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.general.dto.CreateCommentDto;
import ru.practicum.ewm.comment.partial.service.PrivateCommentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Slf4j
@Validated
@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
public class PrivateCommentController {

    private final PrivateCommentService pcServ;

    @PostMapping("{eventId}")
    public ResponseEntity<Object> create(@PathVariable @Min(0) Long userId,
                                         @PathVariable @Min(0) Long eventId,
                                         @RequestBody @Valid CreateCommentDto commentDto) {
        log.info("-- PrivateCommentController -- CREATE new comment for event {}", eventId);
        return new ResponseEntity<>(pcServ.create(userId, eventId, commentDto), HttpStatus.CREATED);
    }

    @PatchMapping("{commentId}")
    public ResponseEntity<Object> update(@PathVariable @Min(0) Long userId,
                                         @PathVariable @Min(0) Long commentId,
                                         @RequestBody @Valid CreateCommentDto commentDto) {
        log.info("-- PrivateCommentController -- UPDATE comment {}", commentId);
        return new ResponseEntity<>(pcServ.update(userId, commentId, commentDto), HttpStatus.OK);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<Object> delete(@PathVariable @Min(0) Long userId,
                                         @PathVariable @Min(0) Long commentId) {
        log.info("-- PrivateCommentController -- DELETE comment {}", commentId);
        pcServ.delete(userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
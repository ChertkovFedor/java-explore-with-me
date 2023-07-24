package ru.practicum.ewm.comment.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.admin.service.AdminCommentService;
import ru.practicum.ewm.comment.general.service.CommentService;
import ru.practicum.ewm.util.enums.SortTypes;

import javax.validation.constraints.Min;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {

    private final AdminCommentService acServ;
    private final CommentService cServ;

    @GetMapping("{commentId}")
    public ResponseEntity<Object> get(@PathVariable @Min(0) Long commentId) {
        log.info("-- AdminCommentController -- GET comment {}", commentId);
        return new ResponseEntity<>(cServ.get(commentId), HttpStatus.OK);
    }

    @GetMapping("{userId}/users")
    public ResponseEntity<Object> getAllByUserId(@PathVariable @Min(0) Long userId,
                                                 @RequestParam(defaultValue = "DESC") SortTypes sort,
                                                 @RequestParam(defaultValue = "0", required = false) @Min(0) int from,
                                                 @RequestParam(defaultValue = "10", required = false) @Min(1) int size) {
        log.info("-- AdminCommentController -- GET comments userId={} from={} size={}", userId, from, size);
        return new ResponseEntity<>(acServ.getByUserId(userId, sort, from, size), HttpStatus.OK);
    }

    @GetMapping("{userId}/users/{eventId}/events")
    public ResponseEntity<Object> getByUserIdEventId(@PathVariable @Min(0) Long userId,
                                                     @PathVariable @Min(0) Long eventId,
                                                     @RequestParam(defaultValue = "DESC") SortTypes sort,
                                                     @RequestParam(defaultValue = "0", required = false) @Min(0) int from,
                                                     @RequestParam(defaultValue = "10", required = false) @Min(1) int size) {
        log.info("-- AdminCommentController -- GET comments userId {} eventId {} from {} size {}", userId, eventId, from, size);
        return new ResponseEntity<>(acServ.getByUserIdEventId(userId, eventId, sort, from, size), HttpStatus.OK);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<Object> delete(@PathVariable @Min(0) Long commentId) {
        log.info("-- AdminCommentController -- DELETE comment {}", commentId);
        acServ.delete(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
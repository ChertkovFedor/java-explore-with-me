package ru.practicum.ewm.comment.common.service;

import ru.practicum.ewm.comment.general.dto.CommentDto;
import ru.practicum.ewm.util.enums.SortTypes;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicCommentService {

    List<CommentDto> get(String text, LocalDateTime start, LocalDateTime end, SortTypes sort);

    List<CommentDto> getAllByEventId(Long eventId, SortTypes sort, int from, int size);

}
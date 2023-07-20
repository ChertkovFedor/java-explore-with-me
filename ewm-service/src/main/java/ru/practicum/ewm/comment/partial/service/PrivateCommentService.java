package ru.practicum.ewm.comment.partial.service;

import ru.practicum.ewm.comment.general.dto.CommentDto;
import ru.practicum.ewm.comment.general.dto.CreateCommentDto;

public interface PrivateCommentService {

    CommentDto create(Long userId, Long eventId, CreateCommentDto commentDto);

    CommentDto update(Long userId, Long commentId, CreateCommentDto commentDto);

    void delete(Long userId, Long commentId);

}
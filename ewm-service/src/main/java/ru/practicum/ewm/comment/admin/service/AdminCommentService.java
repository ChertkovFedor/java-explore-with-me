package ru.practicum.ewm.comment.admin.service;

import ru.practicum.ewm.comment.general.dto.CommentDto;
import ru.practicum.ewm.util.enums.SortTypes;

import java.util.List;

public interface AdminCommentService {

    List<CommentDto> getByUserId(Long userId, SortTypes sort, int from, int size);

    List<CommentDto> getByUserIdEventId(Long userId, Long eventId, SortTypes sort, int from, int size);

    void delete(Long commentId);

}
package ru.practicum.ewm.comment.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.general.dto.CommentDto;
import ru.practicum.ewm.comment.general.repository.CommentRepository;
import ru.practicum.ewm.comment.general.service.CommentService;
import ru.practicum.ewm.util.enums.SortTypes;
import ru.practicum.ewm.util.validator.Validator;

import java.util.List;

import static ru.practicum.ewm.comment.general.mapper.CommentMapper.toDtoList;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentRepository cRep;
    private final CommentService cServ;
    private final Validator valid;

    @Override
    public List<CommentDto> getByUserId(Long userId, SortTypes sort, int from, int size) {
        valid.getUserIfExist(userId);

        if (SortTypes.DESC.equals(sort)) {
            return toDtoList(cRep.findAllByAuthorIdOrderByCreatedOnDesc(userId, PageRequest.of(from, size)));
        }
        return toDtoList(cRep.findAllByAuthorIdOrderByCreatedOnAsc(userId, PageRequest.of(from, size)));
    }

    @Override
    public List<CommentDto> getByUserIdEventId(Long userId, Long eventId, SortTypes sort, int from, int size) {
        valid.getUserIfExist(userId);
        valid.getEventIfExist(eventId);

        if (SortTypes.DESC.equals(sort)) {
            return toDtoList(cRep.findAllByAuthorIdAndEventIdOrderByCreatedOnDesc(userId, eventId, PageRequest.of(from, size)));
        }
        return toDtoList(cRep.findAllByAuthorIdAndEventIdOrderByCreatedOnAsc(userId, eventId, PageRequest.of(from, size)));
    }

    @Override
    public void delete(Long commentId) {
        cRep.delete(cServ.get(commentId));
    }

}
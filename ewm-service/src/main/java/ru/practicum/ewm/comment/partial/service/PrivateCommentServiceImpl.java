package ru.practicum.ewm.comment.partial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.general.dto.CreateCommentDto;
import ru.practicum.ewm.comment.general.model.Comment;
import ru.practicum.ewm.comment.general.repository.CommentRepository;
import ru.practicum.ewm.comment.general.service.CommentService;
import ru.practicum.ewm.event.general.repository.EventRepository;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;

import static ru.practicum.ewm.comment.general.mapper.CommentMapper.toDto;
import static ru.practicum.ewm.comment.general.mapper.CommentMapper.toModel;


@Service
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentRepository cRep;
    private final UserRepository uRep;
    private final EventRepository eRep;
    private final CommentService cServ;

    @Override
    public ru.practicum.ewm.comment.general.dto.CommentDto create(Long userId, Long eventId, CreateCommentDto commentDto) {
        Comment comment = toModel(commentDto);
        comment.setAuthor(uRep.getReferenceById(userId));
        comment.setEvent(eRep.getReferenceById(eventId));
        comment.setCreatedOn(LocalDateTime.now());

        return toDto(cRep.save(comment));
    }

    @Override
    public ru.practicum.ewm.comment.general.dto.CommentDto update(Long userId, Long commentId, CreateCommentDto commentDto) {
        uRep.getReferenceById(userId);
        Comment comment = cServ.get(commentId);
        if (commentDto.getText() != null)
            comment.setText(commentDto.getText());
        comment.setUpdatedOn(LocalDateTime.now());

        return toDto(cRep.save(comment));
    }

    @Override
    public void delete(Long userId, Long commentId) {
        uRep.getReferenceById(userId);
        cRep.delete(cServ.get(commentId));
    }

}
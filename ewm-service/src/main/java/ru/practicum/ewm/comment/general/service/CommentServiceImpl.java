package ru.practicum.ewm.comment.general.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.general.model.Comment;
import ru.practicum.ewm.comment.general.repository.CommentRepository;
import ru.practicum.ewm.util.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository cRep;

    @Override
    public Comment get(Long commentId) {
        return cRep.findById(commentId).orElseThrow(() -> new NotFoundException(commentId, "Comment not found"));
    }

}
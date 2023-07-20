package ru.practicum.ewm.comment.general.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.comment.general.dto.CommentDto;
import ru.practicum.ewm.comment.general.dto.CreateCommentDto;
import ru.practicum.ewm.comment.general.model.Comment;
import ru.practicum.ewm.event.general.mapper.EventMapper;
import ru.practicum.ewm.user.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentMapper {
    public static Comment toModel(CreateCommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .build();
    }

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .author(UserMapper.toShortDto(comment.getAuthor()))
                .event(EventMapper.toShortDto(comment.getEvent()))
                .createdOn(comment.getCreatedOn())
                .updatedOn(comment.getUpdatedOn())
                .text(comment.getText())
                .build();
    }

    public static List<CommentDto> toDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }
}
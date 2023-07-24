package ru.practicum.ewm.comment.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.general.dto.CommentDto;
import ru.practicum.ewm.comment.general.repository.CommentRepository;
import ru.practicum.ewm.event.general.repository.EventRepository;
import ru.practicum.ewm.util.enums.SortTypes;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.comment.general.mapper.CommentMapper.toDtoList;

@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {

    private final CommentRepository cRep;
    private final EventRepository eRep;

    @Override
    public List<CommentDto> get(String text, LocalDateTime start, LocalDateTime end, SortTypes sort) {
        if (start == null)
            start = LocalDateTime.now().minusYears(3);
        if (end == null)
            end = LocalDateTime.now();
        if (text != null)
            text = text.toLowerCase();

        if (SortTypes.DESC.equals(sort))
            return toDtoList(cRep.findByParamsDesc(text, start, end));
        return toDtoList(cRep.findByParamsAsc(text, start, end));

    }

    @Override
    public List<CommentDto> getAllByEventId(Long eventId, SortTypes sort, int from, int size) {
        eRep.getReferenceById(eventId);

        if (SortTypes.DESC.equals(sort))
            return toDtoList(cRep.findAllByEventIdOrderByCreatedOnDesc(eventId, PageRequest.of(from, size)));
        return toDtoList(cRep.findAllByEventIdOrderByCreatedOnAsc(eventId, PageRequest.of(from, size)));
    }

}
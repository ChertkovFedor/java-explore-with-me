package ru.practicum.ewm.request.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestMapper {

    public static Request toModel(RequestDto requestDto, User requester, Event event) {
        return Request.builder()
                .id(requestDto.getId())
                .created(requestDto.getCreated())
                .requester(requester)
                .event(event)
                .status(requestDto.getStatus())
                .build();
    }

    public static RequestDto toDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .requester(request.getRequester().getId())
                .event(request.getEvent().getId())
                .status(request.getStatus())
                .build();
    }

}

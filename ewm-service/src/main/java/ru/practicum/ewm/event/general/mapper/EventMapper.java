package ru.practicum.ewm.event.general.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.category.general.mapper.CategoryMapper;
import ru.practicum.ewm.event.general.dto.CreateEventDto;
import ru.practicum.ewm.event.general.dto.EventDto;
import ru.practicum.ewm.event.general.dto.ShortEventDto;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.user.mapper.UserMapper;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventMapper {

    public static Event toModel(CreateEventDto eventDto) {
        return Event.builder()
                .annotation(eventDto.getAnnotation())
                .description(eventDto.getDescription())
                .eventDate(eventDto.getEventDate())
                .location(eventDto.getLocation())
                .paid(eventDto.isPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .requestModeration(eventDto.isRequestModeration())
                .title(eventDto.getTitle())
                .build();
    }

    public static EventDto toDto(Event event) {
        return EventDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(0L)
                .build();
    }

    public static ShortEventDto toShortDto(Event event) {
        return ShortEventDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }
}

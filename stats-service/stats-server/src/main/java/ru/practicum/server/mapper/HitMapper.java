package ru.practicum.server.mapper;

import lombok.NoArgsConstructor;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.server.model.Hit;

@NoArgsConstructor
public class HitMapper {

    public static Hit toEndpointHit(EndpointHitDto endpointHitDto) {
        return new Hit(
                endpointHitDto.getId(),
                endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                endpointHitDto.getTimestamp()
        );
    }

    public static EndpointHitDto toEndpointHitDto(Hit hit) {
        return new EndpointHitDto(
                hit.getId(),
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                hit.getTimestamp()
        );
    }
}

package ru.practicum.server.mapper;

import lombok.NoArgsConstructor;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.server.model.Hit;

@NoArgsConstructor
public final class HitMapper {

    public static Hit toModel(EndpointHitDto endpointHitDto) {
        return Hit.builder()
                .id(endpointHitDto.getId())
                .app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri())
                .ip(endpointHitDto.getIp())
                .timestamp(endpointHitDto.getTimestamp())
                .build();
    }

    public static EndpointHitDto toDto(Hit hit) {
        return EndpointHitDto.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp())
                .build();
    }
}

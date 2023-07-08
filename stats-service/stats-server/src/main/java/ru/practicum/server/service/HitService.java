package ru.practicum.server.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface HitService {
    EndpointHitDto post(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> get(LocalDateTime start,
                           LocalDateTime end,
                           List<String> uris,
                           Boolean unique);
}

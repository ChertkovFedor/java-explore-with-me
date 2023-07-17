package ru.practicum.server.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    EndpointHitDto post(EndpointHitDto hitDto);

    List<ViewStatsDto> get(LocalDateTime start,
                           LocalDateTime end,
                           List<String> uris,
                           Boolean unique);
}

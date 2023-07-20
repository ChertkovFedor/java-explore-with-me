package ru.practicum.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.server.repository.HitRepository;
import ru.practicum.server.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.server.mapper.HitMapper.toModel;
import static ru.practicum.server.mapper.HitMapper.toDto;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepository hRep;

    @Transactional
    @Override
    public EndpointHitDto post(EndpointHitDto hitDto) {
        return toDto(hRep.save(toModel(hitDto)));
    }

    @Override
    public List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        if (start.isAfter(LocalDateTime.now()))
            throw new ValidationException("The start date cannot be in the future");
        if (unique)
            return hRep.getStatsUniqueIps(start, end, uris)
                    .stream()
                    .sorted(Comparator.comparing(ViewStatsDto::getHits).reversed())
                    .collect(Collectors.toList());
        return hRep.getStats(start, end, uris)
                .stream()
                .sorted(Comparator.comparing(ViewStatsDto::getHits).reversed())
                .collect(Collectors.toList());
    }

}

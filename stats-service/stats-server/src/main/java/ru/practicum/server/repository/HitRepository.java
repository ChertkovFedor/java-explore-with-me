package ru.practicum.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.server.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query(value = "SELECT new ru.practicum.dto.ViewStatsDto(e.app, e.uri, COUNT(e.ip)) " +
            " FROM Hit AS e" +
            " WHERE e.timestamp >= :start" +
            " AND e.timestamp <= :end" +
            " AND e.uri IN :uris" +
            " GROUP BY e.app, e.uri")
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.dto.ViewStatsDto(e.app, e.uri, COUNT(distinct e.ip)) " +
            " FROM Hit AS e" +
            " WHERE e.timestamp >= :start" +
            " AND e.timestamp <= :end" +
            " AND e.uri IN :uris" +
            " GROUP BY e.app, e.uri")
    List<ViewStatsDto> getStatsUniqueIps(LocalDateTime start, LocalDateTime end, List<String> uris);
}

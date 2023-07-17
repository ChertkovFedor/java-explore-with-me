package ru.practicum.ewm.event.general.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.general.model.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {
    List<Event> findAllByIdIsGreaterThanEqualAndInitiatorIdIs(Long id, Long initiatorId, Pageable pageable);

    @Query("SELECT count(pr.id) FROM Request as pr " +
            "WHERE pr.event.id = ?1 " +
            "AND pr.status = 'CONFIRMED'" +
            "GROUP BY pr.id")
    Integer findConfirmedRequests(Long id);
}

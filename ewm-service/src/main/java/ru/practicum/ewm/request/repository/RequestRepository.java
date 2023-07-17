package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.util.enums.RequestState;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findFirstByEventIdIsAndRequesterIdIs(Long eventId, Long requesterId);
    List<Request> findAllByRequesterIdIs(Long requesterId);
    List<Request> findAllByEventIdIs(Long eventId);
    List<Request> findAllByEventInAndStatusIs(List<Event> event, RequestState status);
    List<Request> findAllByIdIn(List<Long> ids);
}

package ru.practicum.ewm.event.general.repository;

import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.util.enums.State;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class EventRepositoryCustomImpl implements EventRepositoryCustom {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Event> adminEventSearch(List<Long> users,
                                        List<State> states,
                                        List<Long> categories,
                                        LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd,
                                        Long from,
                                        Integer size) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);
        Predicate criteria = cb.conjunction();

        if (users != null)
            criteria = cb.and(criteria, event.get("initiator").in(users));

        if (states != null)
            criteria = cb.and(criteria, event.get("state").in(states));

        if (categories != null)
            criteria = cb.and(criteria, event.get("category").in(categories));

        if (rangeStart != null)
            criteria = cb.and(criteria, cb.greaterThan(event.get("eventDate"), rangeStart));

        if (rangeEnd != null)
            criteria = cb.and(criteria, cb.lessThan(event.get("eventDate"), rangeEnd));

        criteria = cb.and(criteria, cb.greaterThanOrEqualTo(event.get("id"), from));

        query.select(event).where(criteria);
        return manager.createQuery(query).setMaxResults(size).getResultList();
    }

    @Override
    public List<Event> publicEventSearch(String text,
                                         List<Long> categories,
                                         Boolean paid,
                                         LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd,
                                         Long from,
                                         Integer size) {

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);
        Predicate criteria = cb.conjunction();

        if (text != null && !text.isEmpty()) {
            Predicate hasTextInAnnotation = cb.like(cb.lower(event.get("annotation")), "%" + text.toLowerCase() + "%");
            Predicate hasTextInDescription = cb.like(cb.lower(event.get("description")), "%" + text.toLowerCase() + "%");
            Predicate hasText = cb.or(hasTextInDescription, hasTextInAnnotation);
            criteria = cb.and(criteria, hasText);
        }

        if (paid != null)
            criteria = cb.and(criteria, cb.equal(event.get("paid"), paid));

        if (categories != null)
            criteria = cb.and(criteria, event.get("category").in(categories));

        if (rangeStart != null)
            criteria = cb.and(criteria, cb.greaterThan(event.get("eventDate"), rangeStart));
        else
            criteria = cb.and(criteria, cb.greaterThan(event.get("eventDate"), LocalDateTime.now()));

        if (rangeEnd != null)
            criteria = cb.and(criteria, cb.lessThan(event.get("eventDate"), rangeEnd));

        criteria = cb.and(criteria, cb.greaterThanOrEqualTo(event.get("id"), from));

        criteria = cb.and(criteria, cb.equal(event.get("state"), State.PUBLISHED));

        query.select(event).where(criteria);
        return manager.createQuery(query).setMaxResults(size).getResultList();
    }
}

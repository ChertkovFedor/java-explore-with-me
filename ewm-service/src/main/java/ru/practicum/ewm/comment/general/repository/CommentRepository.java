package ru.practicum.ewm.comment.general.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.comment.general.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByEventIdOrderByCreatedOnAsc(long eventId, PageRequest pageRequest);

    List<Comment> findAllByEventIdOrderByCreatedOnDesc(long eventId, PageRequest pageRequest);

    List<Comment> findAllByAuthorIdOrderByCreatedOnAsc(long userId, PageRequest pageRequest);

    List<Comment> findAllByAuthorIdOrderByCreatedOnDesc(long userId, PageRequest pageRequest);

    List<Comment> findAllByAuthorIdAndEventIdOrderByCreatedOnAsc(long userId, long eventId, PageRequest pageRequest);

    List<Comment> findAllByAuthorIdAndEventIdOrderByCreatedOnDesc(long userId, long eventId, PageRequest pageRequest);

    @Query("FROM Comment as c " +
            "WHERE (c.createdOn between :startDate and :endDate) " +
            "AND lower(c.text) like %:text% " +
            "ORDER BY c.createdOn ASC")
    List<Comment> findByParamsAsc(String text, LocalDateTime startDate, LocalDateTime endDate);

    @Query("FROM Comment as c " +
            "WHERE (c.createdOn between :startDate and :endDate) " +
            "AND lower(c.text) like %:text% " +
            "ORDER BY c.createdOn DESC")
    List<Comment> findByParamsDesc(String text, LocalDateTime startDate, LocalDateTime endDate);

}
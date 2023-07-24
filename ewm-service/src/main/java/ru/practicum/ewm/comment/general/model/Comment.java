package ru.practicum.ewm.comment.general.model;

import lombok.*;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "COMMENTS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "EVENT_ID", nullable = false)
    private Event event;

    @JoinColumn(name = "CREATED_ON", nullable = false)
    private LocalDateTime createdOn;

    @JoinColumn(name = "UPDATED_ON")
    private LocalDateTime updatedOn;

    @JoinColumn(name = "TEXT", nullable = false)
    private String text;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author=" + author +
                ", event=" + event +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
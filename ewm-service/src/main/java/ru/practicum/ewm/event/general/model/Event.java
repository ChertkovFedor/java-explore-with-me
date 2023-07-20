package ru.practicum.ewm.event.general.model;

import lombok.*;
import ru.practicum.ewm.category.general.model.Category;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.util.enums.State;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Builder
@Table(name = "EVENTS")
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String annotation;
    @ManyToOne(optional = false)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "INITIATOR_ID")
    private User initiator;
    @Embedded
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state;
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id.equals(event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}

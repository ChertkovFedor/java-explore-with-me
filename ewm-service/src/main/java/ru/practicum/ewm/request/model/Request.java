package ru.practicum.ewm.request.model;

import lombok.*;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.util.enums.RequestState;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Builder
@Table(name = "REQUESTS")
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "CREATED")
    private LocalDateTime created;
    @ManyToOne(optional = false)
    @JoinColumn(name = "REQUESTER_ID")
    private User requester;
    @ManyToOne(optional = false)
    @JoinColumn(name = "EVENT_ID")
    private Event event;
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "STATUS")
    private RequestState status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", created=" + created +
                ", requester=" + requester +
                ", event=" + event +
                ", status=" + status +
                '}';
    }
}

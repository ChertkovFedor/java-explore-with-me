package ru.practicum.ewm.compilation.general.model;

import lombok.*;
import ru.practicum.ewm.event.general.model.Event;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Builder
@Table(name = "COMPILATIONS")
@AllArgsConstructor
@NoArgsConstructor
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean pinned;
    private String title;
    @ManyToMany
    @JoinTable(name = "COMPILATION_EVENTS",
            joinColumns = @JoinColumn(name = "COMP_ID"),
            inverseJoinColumns = @JoinColumn(name = "EVENT_ID"))
    private List<Event> events;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compilation that = (Compilation) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Compilation{" +
                "id=" + id +
                ", pinned=" + pinned +
                ", title='" + title + '\'' +
                ", events=" + events +
                '}';
    }
}

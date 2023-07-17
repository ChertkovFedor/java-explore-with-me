package ru.practicum.ewm.event.general.dto.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.event.general.model.Location;
import ru.practicum.ewm.util.enums.StateAction;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.ewm.util.enums.Constants.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UpdateEventRequest {
    @Size(min = 20, max = 2000, message = "Invalid annotation")
    private String annotation;
    @Positive(message = "Invalid category")
    private Long category;
    @Size(min = 20, max = 7000, message = "Invalid description")
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3, max = 120, message = "Invalid title")
    private String title;

    @Override
    public String toString() {
        return "CreateEventDto{" +
                "annotation='" + annotation + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

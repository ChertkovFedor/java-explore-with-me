package ru.practicum.ewm.event.general.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.event.general.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.ewm.util.enums.Constants.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventDto {
    @NotBlank(message = "Blank title")
    @Size(min = 3, max = 120, message = "Invalid title")
    private String title;

    @NotBlank(message = "Blank description")
    @Size(min = 20, max = 7000, message = "Invalid description")
    private String description;

    @Size(min = 20, max = 2000, message = "Invalid annotation")
    @NotBlank(message = "Blank annotation")
    private String annotation;

    @NotNull(message = "Blank category")
    @Positive(message = "Invalid category")
    private Long category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    @NotNull(message = "Blank location")
    private Location location;

    private boolean paid;

    private int participantLimit;

    private boolean requestModeration = true;


    @Override
    public String toString() {
        return "CreateEventDto{" +
                "annotation='" + annotation + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

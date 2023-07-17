package ru.practicum.ewm.event.general.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.category.general.dto.CategoryDto;
import ru.practicum.ewm.user.dto.ShortUserDto;

import java.time.LocalDateTime;

import static ru.practicum.ewm.util.enums.Constants.DATE_TIME_PATTERN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortEventDto {
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    private Long id;
    private ShortUserDto initiator;
    private Boolean paid;
    private String title;
    private long views;

    @Override
    public String toString() {
        return "ShortEventDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}

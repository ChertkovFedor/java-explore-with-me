package ru.practicum.ewm.comment.general.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.general.dto.ShortEventDto;
import ru.practicum.ewm.user.dto.ShortUserDto;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.ewm.util.enums.Constants.DATE_TIME_PATTERN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;
    private ShortUserDto author;
    private ShortEventDto event;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime updatedOn;
    @Size(max = 1000)
    private String text;
}
package ru.practicum.ewm.util.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

import static ru.practicum.ewm.util.enums.Constants.DATE_TIME_PATTERN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorApi {
    private String status;
    private String reason;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime timestamp;
}

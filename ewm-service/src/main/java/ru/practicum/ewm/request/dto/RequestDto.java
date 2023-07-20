package ru.practicum.ewm.request.dto;

import lombok.*;
import ru.practicum.ewm.util.enums.RequestState;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto {
    private Long id;
    private LocalDateTime created;
    private Long requester;
    private Long event;
    private RequestState status;
}

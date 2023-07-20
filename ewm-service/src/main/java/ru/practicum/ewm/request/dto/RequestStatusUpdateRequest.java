package ru.practicum.ewm.request.dto;

import lombok.*;
import ru.practicum.ewm.util.enums.RequestStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatusUpdateRequest {
    @NotNull
    private List<Long> requestIds;
    @NotNull
    private RequestStatus status;
}

package ru.practicum.ewm.request.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatusUpdateResult {
    private List<RequestDto> confirmedRequests;
    private List<RequestDto> rejectedRequests;
}

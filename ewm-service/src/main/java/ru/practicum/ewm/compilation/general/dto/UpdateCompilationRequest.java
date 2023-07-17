package ru.practicum.ewm.compilation.general.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationRequest {
    private List<Long> events;
    private Boolean pinned;
    private String title;
}

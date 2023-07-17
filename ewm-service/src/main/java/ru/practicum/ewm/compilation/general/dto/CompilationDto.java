package ru.practicum.ewm.compilation.general.dto;

import lombok.*;
import ru.practicum.ewm.event.general.dto.ShortEventDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private List<ShortEventDto> events;
    private Long id;
    private boolean pinned;
    private String title;
}

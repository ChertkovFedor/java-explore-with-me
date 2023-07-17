package ru.practicum.ewm.compilation.general.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompilationDto {
    private List<Long> events;
    private boolean pinned;
    @NotBlank(message = "must not be blank")
    private String title;
}

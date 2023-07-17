package ru.practicum.ewm.category.general.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryDto {
    @NotBlank(message = "Blank name")
    @Size(max = 50, message = "Name is too long")
    private String name;
}

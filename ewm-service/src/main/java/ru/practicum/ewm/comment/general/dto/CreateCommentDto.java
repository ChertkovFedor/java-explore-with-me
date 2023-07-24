package ru.practicum.ewm.comment.general.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentDto {
    @NotBlank
    @Size(min = 6, max = 1000)
    private String text;
}
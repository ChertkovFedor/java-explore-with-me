package ru.practicum.ewm.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private Long id;
    @NotBlank(message = "Empty name")
    @Size(min = 2, max = 250, message = "Name is too short or long")
    private String name;
    @NotBlank(message = "Empty email")
    @Email(message = "Invalid email")
    @Size(min = 6, max = 254, message = "Email is too short or long")
    private String email;
}

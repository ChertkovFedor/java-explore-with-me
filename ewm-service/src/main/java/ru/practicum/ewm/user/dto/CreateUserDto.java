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
    @Size(max = 250, message = "Name is too long")
    @Size(min = 2, message = "Name is too short")
    private String name;
    @NotBlank(message = "Empty email")
    @Email(message = "Invalid email")
    @Size(max = 254, message = "Email is too long")
    @Size(min = 6, message = "Email is too short")
    private String email;
}

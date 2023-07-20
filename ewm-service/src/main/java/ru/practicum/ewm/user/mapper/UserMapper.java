package ru.practicum.ewm.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.user.dto.CreateUserDto;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.ShortUserDto;
import ru.practicum.ewm.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {

    public static User toModel(CreateUserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static ShortUserDto toShortDto(User user) {
        return ShortUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

}

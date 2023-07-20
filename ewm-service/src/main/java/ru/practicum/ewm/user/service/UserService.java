package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.CreateUserDto;
import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(CreateUserDto userDto);

    void delete(Long userId);

    List<UserDto> get(List<Long> ids);

    List<UserDto> getAll(Long from, Integer size);
}

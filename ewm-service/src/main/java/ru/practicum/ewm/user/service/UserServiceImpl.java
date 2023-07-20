package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.user.dto.CreateUserDto;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;
import ru.practicum.ewm.util.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.user.mapper.UserMapper.toModel;
import static ru.practicum.ewm.user.mapper.UserMapper.toDto;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository uRep;

    @Override
    public UserDto create(CreateUserDto userDto) {
        return toDto(uRep.save(toModel(userDto)));
    }

    @Override
    public void delete(Long userId) {
        try {
            uRep.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(userId, User.class.getSimpleName());
        }
    }

    @Override
    public List<UserDto> get(List<Long> ids) {
        return uRep.findAllById(ids).stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAll(Long from, Integer size) {
        List<User> foundUsers = uRep.findAllByIdIsGreaterThanEqual(from, PageRequest.of(0, size));
        return foundUsers.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}

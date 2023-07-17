package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.CreateUserDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/users")
public class AdminUserController {
    private final UserService uServ;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @NotNull @Valid CreateUserDto userDto) {
        log.info("-- AdminUserController -- Create user id {}", userDto);
        return new ResponseEntity<>(uServ.create(userDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable Long userId) {
        log.info("-- AdminUserController -- Delete user id {}", userId);
        uServ.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(name = "ids", defaultValue = "") List<Long> ids,
                                      @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Long from,
                                      @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        if (ids.isEmpty()) {
            log.info("-- AdminUserController -- Get users from id {}, page size {}", from, size);
            return new ResponseEntity<>(uServ.getAll(from, size), HttpStatus.OK);
        }
        log.info("-- AdminUserController -- Get users ids from {}", ids);
        return new ResponseEntity<>(uServ.get(ids), HttpStatus.OK);
    }
}

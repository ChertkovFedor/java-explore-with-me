package ru.practicum.ewm.category.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.common.service.PublicCategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/categories")
public class PublicCategoryController {
    private final PublicCategoryService pcServ;

    @GetMapping("/{catId}")
    public ResponseEntity<Object> get(@PathVariable Long catId) {
        log.info("-- PublicCategoryController -- Get category by id {}", catId);
        return new ResponseEntity<>(pcServ.get(catId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Long from,
                                         @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("-- PublicCategoryController -- Get categories from id {}, page size = {}", from, size);
        return new ResponseEntity<>(pcServ.getAll(from, size), HttpStatus.OK);
    }

}

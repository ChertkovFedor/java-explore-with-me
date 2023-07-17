package ru.practicum.ewm.category.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.general.dto.CreateCategoryDto;
import ru.practicum.ewm.category.admin.service.AdminCategoryService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@Validated
@RequiredArgsConstructor
public class AdminCategoryController {
    private final AdminCategoryService acServ;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @NotNull @Valid CreateCategoryDto categoryDto) {
        log.info("-- AdminCategoryController -- Create category {}", categoryDto);
        return new ResponseEntity<>(acServ.create(categoryDto), HttpStatus.CREATED);
    }

    @DeleteMapping("{catId}")
    public ResponseEntity<Object> delete(@PathVariable Long catId) {
        log.info("-- AdminCategoryController -- Delete category {}", catId);
        acServ.delete(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{catId}")
    public ResponseEntity<Object> update(@PathVariable Long catId,
                                         @RequestBody @NotNull @Valid CreateCategoryDto categoryDto) {
        log.info("-- AdminCategoryController -- Update category {}", catId);
        return new ResponseEntity<>(acServ.update(catId, categoryDto), HttpStatus.OK);
    }
}

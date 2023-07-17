package ru.practicum.ewm.category.common.service;

import ru.practicum.ewm.category.general.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryDto> getAll(Long from, Integer size);
    CategoryDto get(Long catId);
}

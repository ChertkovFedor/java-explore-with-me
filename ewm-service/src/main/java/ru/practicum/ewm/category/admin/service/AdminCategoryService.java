package ru.practicum.ewm.category.admin.service;

import ru.practicum.ewm.category.general.dto.CategoryDto;
import ru.practicum.ewm.category.general.dto.CreateCategoryDto;

public interface AdminCategoryService {
    CategoryDto create(CreateCategoryDto categoryDto);
    void delete(Long catId);
    CategoryDto update(Long catId, CreateCategoryDto categoryDto);
}

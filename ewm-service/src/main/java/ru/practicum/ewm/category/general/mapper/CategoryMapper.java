package ru.practicum.ewm.category.general.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.category.general.dto.CategoryDto;
import ru.practicum.ewm.category.general.dto.CreateCategoryDto;
import ru.practicum.ewm.category.general.model.Category;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CategoryMapper {

    public static Category toModel(CreateCategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
    }

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}

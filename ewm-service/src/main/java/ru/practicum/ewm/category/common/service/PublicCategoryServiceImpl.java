package ru.practicum.ewm.category.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.general.dto.CategoryDto;
import ru.practicum.ewm.category.general.mapper.CategoryMapper;
import ru.practicum.ewm.category.general.model.Category;
import ru.practicum.ewm.category.general.repository.CategoryRepository;
import ru.practicum.ewm.util.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.ewm.category.general.mapper.CategoryMapper.toDto;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository cRep;

    @Override
    public CategoryDto get(Long catId) {
        Optional<Category> foundCategory = cRep.findById(catId);
        return toDto(cRep.save(foundCategory.orElseThrow(() -> new NotFoundException(catId, Category.class.getSimpleName()))));
    }

    @Override
    public List<CategoryDto> getAll(Long from, Integer size) {
        List<Category> foundCategories = cRep.findAllByIdIsGreaterThanEqualOrderByIdAsc(from, PageRequest.of(0, size));
        return foundCategories.stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }
}

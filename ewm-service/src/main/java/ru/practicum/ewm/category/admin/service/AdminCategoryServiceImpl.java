package ru.practicum.ewm.category.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.general.dto.CategoryDto;
import ru.practicum.ewm.category.general.dto.CreateCategoryDto;
import ru.practicum.ewm.category.general.model.Category;
import ru.practicum.ewm.category.general.repository.CategoryRepository;
import ru.practicum.ewm.util.exception.NotFoundException;

import java.util.Optional;

import static ru.practicum.ewm.category.general.mapper.CategoryMapper.toModel;
import static ru.practicum.ewm.category.general.mapper.CategoryMapper.toDto;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository cRep;

    @Override
    public CategoryDto create(CreateCategoryDto categoryDto) {
        return toDto(cRep.save(toModel(categoryDto)));
    }

    @Override
    public void delete(Long catId) {
        try {
            cRep.deleteById(catId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(catId, Category.class.getSimpleName());
        }
    }

    @Override
    public CategoryDto update(Long catId, CreateCategoryDto categoryDto) {
        Optional<Category> foundCategory = cRep.findById(catId);
        Category categoryToUpdate = foundCategory.orElseThrow(() -> new NotFoundException(catId, Category.class.getSimpleName()));
        categoryToUpdate.setName(categoryDto.getName());
        return toDto(cRep.save(categoryToUpdate));
    }
}

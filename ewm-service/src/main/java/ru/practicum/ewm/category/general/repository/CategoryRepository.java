package ru.practicum.ewm.category.general.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.category.general.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIdIsGreaterThanEqualOrderByIdAsc(Long from, Pageable pageable);
}

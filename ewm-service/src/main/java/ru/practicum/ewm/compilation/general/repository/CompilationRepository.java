package ru.practicum.ewm.compilation.general.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.compilation.general.model.Compilation;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findAllByIdIsGreaterThanEqualAndPinnedIs(Long from, boolean pinned, Pageable pageable);
}

package ru.practicum.ewm.compilation.common.service;

import ru.practicum.ewm.compilation.general.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {
    CompilationDto get(Long compId);

    List<CompilationDto> getAll(boolean pinned, long from, int size);
}

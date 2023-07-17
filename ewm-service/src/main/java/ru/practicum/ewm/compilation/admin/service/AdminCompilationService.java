package ru.practicum.ewm.compilation.admin.service;

import ru.practicum.ewm.compilation.general.dto.CompilationDto;
import ru.practicum.ewm.compilation.general.dto.CreateCompilationDto;
import ru.practicum.ewm.compilation.general.dto.UpdateCompilationRequest;

public interface AdminCompilationService {
    CompilationDto create(CreateCompilationDto createCompilationDto);

    void delete(Long compId);

    CompilationDto update(Long compId, UpdateCompilationRequest updateRequest);
}

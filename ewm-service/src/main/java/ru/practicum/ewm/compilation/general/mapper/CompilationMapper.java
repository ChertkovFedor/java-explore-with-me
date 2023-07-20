package ru.practicum.ewm.compilation.general.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.compilation.general.dto.CompilationDto;
import ru.practicum.ewm.compilation.general.dto.CreateCompilationDto;
import ru.practicum.ewm.compilation.general.model.Compilation;
import ru.practicum.ewm.event.general.mapper.EventMapper;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompilationMapper {

    public static Compilation toModel(CreateCompilationDto compilationDto) {
        return Compilation.builder()
                .title(compilationDto.getTitle())
                .pinned(compilationDto.isPinned())
                .build();
    }

    public static CompilationDto toDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.isPinned())
                .events(compilation.getEvents().stream()
                        .map(EventMapper::toShortDto)
                        .collect(Collectors.toList()))
                .build();
    }
}

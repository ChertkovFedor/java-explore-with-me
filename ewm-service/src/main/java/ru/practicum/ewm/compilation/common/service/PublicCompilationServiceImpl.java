package ru.practicum.ewm.compilation.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.common.service.PublicCompilationService;
import ru.practicum.ewm.compilation.general.dto.CompilationDto;
import ru.practicum.ewm.compilation.general.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.general.model.Compilation;
import ru.practicum.ewm.compilation.general.repository.CompilationRepository;
import ru.practicum.ewm.event.general.dto.ShortEventDto;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.event.general.service.EventService;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.service.CommonRequestService;
import ru.practicum.ewm.util.validator.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.ewm.compilation.general.mapper.CompilationMapper.toDto;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final CompilationRepository cRep;
    private final Validator valid;
    private final EventService eServ;
    private final CommonRequestService crServ;

    @Override
    public CompilationDto get(Long compId) {
        Compilation compilation = valid.getCompilationIfExist(compId);
        CompilationDto compilationDto = toDto(compilation);

        Map<Long, Long> views = eServ.getStats(compilation.getEvents(), false);
        if (!views.isEmpty())
            setViewsToDto(views, compilationDto);

        List<Request> confirmedRequests = crServ.getConfirmedRequests(compilation.getEvents());
        for (ShortEventDto shortDto : compilationDto.getEvents()) {
            shortDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(shortDto.getId()))
                    .count());
        }

        return compilationDto;
    }

    @Override
    public List<CompilationDto> getAll(boolean pinned, long from, int size) {

        List<Compilation> foundCompilations = cRep.findAllByIdIsGreaterThanEqualAndPinnedIs(from, pinned, PageRequest.of(0, size));

        List<CompilationDto> compilationsDto = foundCompilations.stream()
                .map(CompilationMapper::toDto)
                .collect(Collectors.toList());

        Set<Event> events = foundCompilations.stream()
                .map(Compilation::getEvents)
                .flatMap(List<Event>::stream)
                .collect(Collectors.toSet());

        Map<Long, Long> views = eServ.getStats(List.copyOf(events), false);
        if (!views.isEmpty())
            compilationsDto.forEach(compilationDto -> setViewsToDto(views, compilationDto));

        List<Request> confirmedRequests = crServ.getConfirmedRequests(List.copyOf(events));

        compilationsDto.forEach(compilationDto -> setConfirmedRequestsToDto(compilationDto.getEvents(), confirmedRequests));

        return compilationsDto;
    }

    private void setViewsToDto(Map<Long, Long> views, CompilationDto compilationDto) {
        compilationDto.getEvents().forEach(e -> e.setViews(views.get(e.getId())));
    }

    private void setConfirmedRequestsToDto(List<ShortEventDto> events, List<Request> confirmedRequests) {
        for (ShortEventDto shortDto : events) {
            shortDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(shortDto.getId()))
                    .count());
        }
    }
}

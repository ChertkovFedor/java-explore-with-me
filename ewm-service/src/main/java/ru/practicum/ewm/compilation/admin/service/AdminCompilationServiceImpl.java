package ru.practicum.ewm.compilation.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.general.dto.CompilationDto;
import ru.practicum.ewm.compilation.general.dto.CreateCompilationDto;
import ru.practicum.ewm.compilation.general.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.general.model.Compilation;
import ru.practicum.ewm.compilation.general.repository.CompilationRepository;
import ru.practicum.ewm.event.general.dto.ShortEventDto;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.event.general.repository.EventRepository;
import ru.practicum.ewm.event.general.service.EventService;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.service.CommonRequestService;
import ru.practicum.ewm.util.validator.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.practicum.ewm.compilation.general.mapper.CompilationMapper.toModel;
import static ru.practicum.ewm.compilation.general.mapper.CompilationMapper.toDto;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationRepository cRep;
    private final EventRepository eRep;
    private final EventService ceServ;
    private final CommonRequestService crServ;
    private final Validator valid;

    @Override
    public CompilationDto create(CreateCompilationDto createCompilationDto) {
        if (createCompilationDto.getTitle().length() > 50)
            throw new IllegalArgumentException("Title name is too long");
        Compilation compilationToSave = toModel(createCompilationDto);
        List<Event> events;
        List<Long> eventIds = createCompilationDto.getEvents();

        if (eventIds != null && !eventIds.isEmpty())
            events = eRep.findAllById(createCompilationDto.getEvents());
        else
            events = new ArrayList<>();

        compilationToSave.setEvents(events);
        CompilationDto compilationDto = toDto(cRep.save(compilationToSave));

        Map<Long, Long> views = ceServ.getStats(events, false);
        if (!views.isEmpty())
            compilationDto.getEvents().forEach(e -> e.setViews(views.get(e.getId())));

        List<Request> confirmedRequests = crServ.getConfirmedRequests(events);
        for (ShortEventDto shortDto : compilationDto.getEvents()) {
            shortDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(shortDto.getId()))
                    .count());
        }

        return compilationDto;
    }

    @Override
    public void delete(Long compId) {
        valid.getCompilationIfExist(compId);
        cRep.deleteById(compId);
    }

    @Override
    public CompilationDto update(Long compId, UpdateCompilationRequest updateRequest) {
        if (updateRequest.getTitle() != null && updateRequest.getTitle().length() > 50)
            throw new IllegalArgumentException("Title name is too long");
        Compilation compilationToUpdate = valid.getCompilationIfExist(compId);

        if (updateRequest.getPinned() != null)
            compilationToUpdate.setPinned(updateRequest.getPinned());

        if (updateRequest.getTitle() != null)
            compilationToUpdate.setTitle(updateRequest.getTitle());

        if (updateRequest.getEvents() != null)
            compilationToUpdate.setEvents(eRep.findAllById(updateRequest.getEvents()));

        CompilationDto compilationDto = toDto(cRep.save(compilationToUpdate));

        Map<Long, Long> views = ceServ.getStats(compilationToUpdate.getEvents(), false);
        if (!views.isEmpty())
            compilationDto.getEvents().forEach(e -> e.setViews(views.get(e.getId())));

        List<Request> confirmedRequests = crServ.getConfirmedRequests(compilationToUpdate.getEvents());
        for (ShortEventDto shortDto : compilationDto.getEvents()) {
            shortDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(shortDto.getId()))
                    .count());
        }

        return compilationDto;
    }
}

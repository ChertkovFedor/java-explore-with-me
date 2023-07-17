package ru.practicum.ewm.util.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.general.model.Category;
import ru.practicum.ewm.category.general.repository.CategoryRepository;
import ru.practicum.ewm.compilation.general.model.Compilation;
import ru.practicum.ewm.compilation.general.repository.CompilationRepository;
import ru.practicum.ewm.event.general.model.Event;
import ru.practicum.ewm.event.general.repository.EventRepository;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;
import ru.practicum.ewm.util.exception.NotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Validator {
    private final UserRepository uRep;
    private final CategoryRepository catRep;
    private final EventRepository eRep;
    private final RequestRepository rRep;
    private final CompilationRepository comRep;

    public User getUserIfExist(Long userId) {
        Optional<User> user = uRep.findById(userId);
        return user.orElseThrow(() -> new NotFoundException(userId, User.class.getSimpleName()));
    }

    public Event getEventIfExist(Long eventId) {
        Optional<Event> event = eRep.findById(eventId);
        return event.orElseThrow(() -> new NotFoundException(eventId, Event.class.getSimpleName()));
    }

    public Category getCategoryIfExist(Long catId) {
        Optional<Category> category = catRep.findById(catId);
        return category.orElseThrow(() -> new NotFoundException(catId, Category.class.getSimpleName()));
    }

    public Request getRequestIfExist(Long requestId) {
        Optional<Request> request = rRep.findById(requestId);
        return request.orElseThrow(() -> new NotFoundException(requestId, Request.class.getSimpleName()));
    }

    public Compilation getCompilationIfExist(Long compId) {
        Optional<Compilation> compilation = comRep.findById(compId);
        return compilation.orElseThrow(() -> new NotFoundException(compId, Compilation.class.getSimpleName()));
    }
}

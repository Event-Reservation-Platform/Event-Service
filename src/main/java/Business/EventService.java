package Business;


import Domain.Event;
import Exceptions.PageException;
import Exceptions.SizeException;
import Repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository repository;

    public List<Event> getEvents(int page, int size) {
        if (page < 1) {
            throw new PageException("Page cannot be lower than 1");
        }

        if (size < 1 || size > 100) {
            throw new SizeException("Size has to be between 1 and 100");
        }
        return repository.getPaginatedEvents(page, size);
    }
}

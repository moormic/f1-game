package com.moormic.f1.service;

import com.moormic.f1.model.Event;
import com.moormic.f1.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventService {
    private final EventRepository eventRepository;

    public List<Event> calendar() {
        var events = eventRepository.getAll();
        return events.stream()
                .filter(e -> "Race".equals(e.getType()))
                .collect(toList());
    }
}

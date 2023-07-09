package com.moormic.f1.api;

import com.moormic.f1.model.Event;
import com.moormic.f1.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CalendarController {
    private final EventRepository eventRepository;

    @GetMapping("/calendar")
    public List<Event> calendar() {
        return eventRepository.get();
    }

}

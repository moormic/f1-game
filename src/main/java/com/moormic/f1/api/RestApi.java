package com.moormic.f1.api;

import com.moormic.f1.model.Event;
import com.moormic.f1.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RestApi {
    private final EventService eventService;

    @GetMapping("/season/calendar")
    public List<Event> calendar() {
        return eventService.calendar();
    }

}

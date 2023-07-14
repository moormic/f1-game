package com.moormic.f1.game.api;

import com.moormic.f1.game.model.exception.RaceResultException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
public class ApiErrorHandler {
    @ExceptionHandler(RaceResultException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Object processError(RaceResultException e) {
        return e.getMessage();
    }
}

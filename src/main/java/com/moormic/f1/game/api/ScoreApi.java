package com.moormic.f1.game.api;

import com.moormic.f1.game.model.score.TotalScore;
import com.moormic.f1.game.model.Prediction;
import com.moormic.f1.game.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ScoreApi {
    private final ScoreService scoreService;

    @GetMapping("/prediction/season/{season}/race/{race}/score")
    public TotalScore score(
            @PathVariable(value = "season") Integer season,
            @PathVariable(value = "race") Integer race,
            @RequestParam(value = "pole") String pole,
            @RequestParam(value = "p1") String p1,
            @RequestParam(value = "p2") String p2,
            @RequestParam(value = "p3") String p3,
            @RequestParam(value = "fastestLap") String fastestLap,
            @RequestParam(value = "dnfCount") Integer dnfCount,
            @RequestParam(value = "dnfDriver", required = false) List<String> dnfDrivers
    ) {
        var prediction = new Prediction(
                season,
                race,
                pole,
                List.of(p1, p2, p3),
                fastestLap,
                dnfCount,
                dnfDrivers == null ? List.of() : dnfDrivers
        );
        return scoreService.score(prediction);
    }

}

package com.moormic.f1.game.repository.race;

import com.moormic.f1.game.model.exception.RaceResultsException;
import com.moormic.f1.game.repository.race.model.ErgastApiResponse;
import com.moormic.f1.game.repository.race.model.MRData;
import com.moormic.f1.game.repository.race.model.RaceResults;
import com.moormic.f1.game.repository.race.model.RaceTable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RaceResultsRepository {
    private static final String URL_TEMPLATE = "https://ergast.com/api/f1/%d/%d/results.json";
    private final RestTemplate restClient;

    public RaceResults get(Integer season, Integer round) {
        var responseEntity = restClient.getForEntity(String.format(URL_TEMPLATE, season, round), ErgastApiResponse.class);

        return Optional.ofNullable(responseEntity.getBody())
                .map(ErgastApiResponse::getMrData)
                .map(MRData::getRaceTable)
                .filter(rt -> season.equals(Integer.valueOf(rt.getSeason())))
                .filter(rt -> round.equals(Integer.valueOf(rt.getRound())))
                .map(RaceTable::getRaces)
                .stream()
                .flatMap(Collection::stream)
                .findFirst()
                .map(r -> new RaceResults(r.getRaceResults()))
                .orElseThrow(() -> new RaceResultsException(String.format("No race results found for %d season, round %d.", season, round)));
    }

}

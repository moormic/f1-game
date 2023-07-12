package com.moormic.f1.game.repository;

import com.moormic.f1.game.model.exception.RaceResultException;
import com.moormic.f1.game.repository.model.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RaceResultRepository {
    private static final String URL_TEMPLATE = "https://ergast.com/api/f1/%d/%d/results.json";
    private final RestTemplate restClient;

    public List<RaceResult> get(Integer season, Integer race) {
        var responseEntity = restClient.getForEntity(String.format(URL_TEMPLATE, season, race), ErgastApiResponse.class);

        return Optional.ofNullable(responseEntity.getBody())
                .map(ErgastApiResponse::getMrData)
                .map(MRData::getRaceTable)
                .filter(rt -> season.equals(Integer.valueOf(rt.getSeason())))
                .filter(rt -> race.equals(Integer.valueOf(rt.getRound())))
                .map(RaceTable::getRaces)
                .stream()
                .flatMap(Collection::stream)
                .findFirst()
                .map(Race::getRaceResults)
                .orElseThrow(() -> new RaceResultException(String.format("No race results found for %d season, round %d.", season, race)));
    }

}

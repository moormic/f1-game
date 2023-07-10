package com.moormic.f1.game.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moormic.f1.game.exception.ApiException;
import com.moormic.f1.game.repository.model.RaceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RaceResultRepository {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};
    private static final TypeReference<List<RaceResult>> RESULT_TYPE = new TypeReference<>() {};
    private static final String URL_TEMPLATE = "https://ergast.com/api/f1/%d/%d/results.json";
    private final RestTemplate restClient;

    public List<RaceResult> get(Integer season, Integer raceNumber) {
        ResponseEntity<String> responseEntity = restClient.getForEntity(String.format(URL_TEMPLATE, season, raceNumber), String.class);

        return Optional.ofNullable(responseEntity.getBody())
                .map(b -> {
                    try {
                        return OBJECT_MAPPER.readValue(b, MAP_TYPE);
                    } catch (IOException e) {
                        throw new ApiException();
                    }
                })
                .map(m -> OBJECT_MAPPER.convertValue(m.get("response"), RESULT_TYPE))
                .orElseThrow(ApiException::new);
    }

}

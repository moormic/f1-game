package com.moormic.f1.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moormic.f1.exception.ApiException;
import com.moormic.f1.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.util.concurrent.TimeUnit.HOURS;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventRepository {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};
    private static final TypeReference<List<Event>> RACE_TYPE = new TypeReference<>() {};
    private static final String URL = "https://api-formula-1.p.rapidapi.com/races?season=2023&timezone=America/New_York";
    private final RestTemplate restClient;
    private volatile List<Event> cache;

    public List<Event> getAll() {
        if (isEmpty(cache)) {
            refreshCache();
        }

        return cache;
    }

    @Scheduled(fixedDelay = 24, fixedRate = 24, timeUnit = HOURS)
    private void refreshCache() {
        ResponseEntity<String> responseEntity = restClient.getForEntity(URL, String.class);

        cache = Optional.ofNullable(responseEntity.getBody())
                .map(b -> {
                    try {
                        return OBJECT_MAPPER.readValue(b, MAP_TYPE);
                    } catch (IOException e) {
                        throw new ApiException();
                    }
                })
                .map(m -> OBJECT_MAPPER.convertValue(m.get("response"), RACE_TYPE))
                .orElseThrow(ApiException::new);
    }

}

package com.moormic.f1.game;

import com.moormic.f1.game.model.prediction.PlayerPrediction;
import com.moormic.f1.game.model.score.PlayerScore;
import com.moormic.f1.game.repository.prediction.PlayerPredictionRepository;
import com.moormic.f1.game.repository.score.PlayerScoreRepository;
import com.moormic.f1.game.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static java.util.stream.Collectors.toList;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.moormic.f1.game"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class F1PredictionGame implements CommandLineRunner {
    private final PlayerPredictionRepository playerPredictionRepository;
    private final ScoreService scoreService;
    private final PlayerScoreRepository playerScoreRepository;

    public static void main(String[] args) {
        new SpringApplicationBuilder(F1PredictionGame.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    public void run(String... args) {
        var raceNumber = getRaceNumber();
        System.out.println("Playing prediction game for race number " + raceNumber);
        var playerPredictions = playerPredictionRepository.get(raceNumber);
        var playerScores = getScores(playerPredictions);
        playerScores.forEach(playerScoreRepository::put);

    }

    private Integer getRaceNumber(String... args) {
        try {
            CommandLineParser parser = new DefaultParser();
            var cli = parser.parse(new Options().addOption("race", true, "race number"), args);
            var arg = cli.getOptionValue("race");
            return Integer.valueOf(arg);
        } catch (NumberFormatException | ParseException e) {
            System.out.println("Invalid '-race' argument supplied. Unable to determine race number. Exiting...");
            System.exit(-1);
            return null;
        }
    }

    private List<PlayerScore> getScores(List<PlayerPrediction> predictions) {
        return predictions.stream()
                .map(scoreService::score)
                .peek(s -> System.out.println(s.getPlayerName() + ": " + s.getScore()))
                .collect(toList());
    }

}

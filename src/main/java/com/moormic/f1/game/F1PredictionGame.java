package com.moormic.f1.game;

import com.moormic.f1.game.model.prediction.PlayerPrediction;
import com.moormic.f1.game.model.score.PlayerScore;
import com.moormic.f1.game.repository.prediction.PlayerPredictionRepository;
import com.moormic.f1.game.repository.race.RaceResultRepository;
import com.moormic.f1.game.repository.race.model.RaceResult;
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
    private static final CommandLineParser CMD_LINE_PARSER = new DefaultParser();
    private static final Options OPTIONS = new Options()
            .addOption("season", true, "season")
            .addOption("race", true, "race number");
    private final PlayerPredictionRepository playerPredictionRepository;
    private final RaceResultRepository raceResultRepository;
    private final ScoreService scoreService;
    private final PlayerScoreRepository playerScoreRepository;

    public static void main(String[] args) {
        new SpringApplicationBuilder(F1PredictionGame.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    public void run(String... args) {
        var season = Integer.getInteger(getCliArgument("season", args));
        var round = Integer.getInteger(getCliArgument("race", args));
        System.out.printf("Playing prediction game for %d round %d.\n", season, round);

        var playerPredictions = playerPredictionRepository.get(round);
        var raceResults = raceResultRepository.get(season, round);
        var playerScores = getScores(raceResults, playerPredictions);
        playerScores.forEach(playerScoreRepository::put);
    }

    private String getCliArgument(String arg, String... args) {
        try {
            var cli = CMD_LINE_PARSER.parse(OPTIONS, args);
            return cli.getOptionValue(arg);
        } catch (ParseException e) {
            System.out.println("Invalid arguments supplied. Please provide -season & -race arguments. Exiting...");
            System.exit(-1);
            return null;
        }
    }

    private List<PlayerScore> getScores(List<RaceResult> raceResults, List<PlayerPrediction> predictions) {
        return predictions.stream()
                .map(p -> scoreService.score(raceResults, p))
                .peek(s -> System.out.println(s.getPlayerName() + ": " + s.getScore()))
                .collect(toList());
    }

}

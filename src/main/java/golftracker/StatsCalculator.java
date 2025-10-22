package golftracker;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;

public class StatsCalculator {
    private final List<Round> rounds;

    public StatsCalculator(List<Round> rounds) {
        this.rounds = rounds;
    }

    public double getAverageScoreAllHoles() {
        return rounds.stream()
                .flatMap(r -> r.getHoleScores().stream())
                .mapToInt(Integer::intValue)
                .average()
                .orElse(Double.NaN);
    }

    public List<Double> getAverageScorePerHole() {
        List<Double> averages = new ArrayList<>();
        for (int hole = 0; hole < 18; hole++) {
            int finalHole = hole;
            IntSummaryStatistics stats = rounds.stream()
                    .mapToInt(r -> r.getHoleScores().get(finalHole))
                    .summaryStatistics();
            averages.add(stats.getAverage());
        }
        return averages;
    }

    public double getLowestTotalScore() {
        return rounds.stream()
                .mapToInt(Round::getTotalScore)
                .min()
                .orElse(-1);
    }

    public double getHighestTotalScore() {
        return rounds.stream()
                .mapToInt(Round::getTotalScore)
                .max()
                .orElse(-1);
    }

    public String summary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Golf Statistics ===\n");
        sb.append(String.format("Rounds Played: %d\n", rounds.size()));
        sb.append(String.format("Average Score (All Holes): %.2f\n", getAverageScoreAllHoles()));
        sb.append(String.format("Lowest Round Total: %.0f\n", getLowestTotalScore()));
        sb.append(String.format("Highest Round Total: %.0f\n", getHighestTotalScore()));

        sb.append("\nAverage per hole:\n");
        List<Double> perHole = getAverageScorePerHole();
        for (int i = 0; i < perHole.size(); i++) {
            sb.append(String.format("Hole %2d: %.2f\n", i + 1, perHole.get(i)));
        }

        return sb.toString();
    }
}

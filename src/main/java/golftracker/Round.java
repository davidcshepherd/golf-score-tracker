package golftracker;

import java.util.List;

public class Round {
    private final List<Integer> holeScores;

    public Round(List<Integer> holeScores) {
        if (holeScores.size() != 18) {
            throw new IllegalArgumentException("Each round must have exactly 18 holes.");
        }
        this.holeScores = holeScores;
    }

    public List<Integer> getHoleScores() {
        return holeScores;
    }

    public int getTotalScore() {
        return holeScores.stream().mapToInt(Integer::intValue).sum();
    }
}

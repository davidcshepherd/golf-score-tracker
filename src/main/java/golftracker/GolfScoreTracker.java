package golftracker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GolfScoreTracker {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar golf-score-tracker.jar <file1.csv> <file2.csv> ...");
            System.exit(1);
        }

        List<Round> rounds = new ArrayList<>();

        for (String fileName : args) {
            try {
                List<Integer> scores = readScoresFromCsv(fileName);
                rounds.add(new Round(scores));
                System.out.println("Loaded: " + fileName);
            } catch (Exception e) {
                System.err.println("Error reading " + fileName + ": " + e.getMessage());
            }
        }

        if (rounds.isEmpty()) {
            System.out.println("No valid rounds loaded.");
            return;
        }

        StatsCalculator stats = new StatsCalculator(rounds);
        System.out.println(stats.summary());
    }

    private static List<Integer> readScoresFromCsv(String filePath) throws IOException {
        List<Integer> scores = new ArrayList<>();
        try (FileReader reader = new FileReader(Paths.get(filePath).toFile());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord record : csvParser) {
                for (String value : record) {
                    scores.add(Integer.parseInt(value.trim()));
                }
            }
        }
        return scores;
    }
}

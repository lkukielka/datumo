import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PairSumFinderJavaTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-cases.csv", numLinesToSkip = 1)
    void testFindPairsWithSum(String input, String expected) {
        // given
        List<Integer> inputList = parseInput(input);
        List<PairSumFinderJava.Pair> expectedPairs = parseExpected(expected);

        // when
        List<PairSumFinderJava.Pair> actualPairs = PairSumFinderJava.findPairsWithSum(inputList);

        // then
        Map<PairSumFinderJava.Pair, Integer> result = countPairs(normalizePairs(actualPairs));
        Map<PairSumFinderJava.Pair, Integer> expectedResult = countPairs(normalizePairs(expectedPairs));
        assertEquals(result.size(),
                expectedResult.size(),
                String.format("Mismatch in pairs counts for input: %s. Expected: %d, Actual: %d",
                        inputList, expectedResult.size(), result.size()));
        result.forEach((pair, occur) ->
                assertEquals(occur, expectedResult.get(pair), String.format("Mismatch in occurrences for pair %s. Expected: %d, Actual: %d",
                        pair, expectedResult.get(pair), occur)));
    }

    private List<Integer> parseInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }

    private List<PairSumFinderJava.Pair> parseExpected(String expected) {
        if (expected == null || expected.trim().isEmpty()) {
            return Collections.emptyList();
        }

        List<PairSumFinderJava.Pair> pairs = new ArrayList<>();
        String[] pairStrings = expected.split(",");

        for (String pairString : pairStrings) {
            String[] numbers = pairString.trim().split(":");
            pairs.add(new PairSumFinderJava.Pair(
                    Integer.parseInt(numbers[0]),
                    Integer.parseInt(numbers[1])
            ));
        }
        return pairs;
    }

    private static List<PairSumFinderJava.Pair> normalizePairs(List<PairSumFinderJava.Pair> pairs) {
        if (pairs == null) return Collections.emptyList();
        List<PairSumFinderJava.Pair> normalized = new ArrayList<>();
        for (PairSumFinderJava.Pair pair : pairs) {
            int first = Math.min(pair.first(), pair.second());
            int second = Math.max(pair.first(), pair.second());
            normalized.add(new PairSumFinderJava.Pair(first, second));
        }
        return normalized;
    }

    private static Map<PairSumFinderJava.Pair, Integer> countPairs(List<PairSumFinderJava.Pair> pairs) {
        Map<PairSumFinderJava.Pair, Integer> pairToOccurrences = new HashMap<>();
        pairs.forEach(pair -> pairToOccurrences.merge(pair, 1, Integer::sum));
        return pairToOccurrences;
    }
}
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PairSumFinderJava {
    private static final int EXPECTED_SUM = 12;

    public static List<Pair> findPairsWithSum(List<Integer> numbers) {
        List<Pair> result = new ArrayList<>();

        Map<Integer, Integer> numberToOccurances = new HashMap<>();
        numbers.forEach(num -> numberToOccurances.merge(num, 1, Integer::sum));

        /*Szukamy par, więc iterujemy po liczbach całkowitych MAKSYMALNIE do połowy zbioru.
        W przypadku, gdy EXPECTED_SUM będzie nieparzyste wytarczy iterować do ilorazu
        zaokrąglonek w dół, zatem nie trzeba przejmować się częścią dziesiętną wyniku dzielenia.*/
        for (int i = 0; i <= EXPECTED_SUM / 2; i++) {
            int firstNr = numberToOccurances.getOrDefault(i, 0);
            int secondNr = numberToOccurances.getOrDefault(EXPECTED_SUM - i, 0);

            if (firstNr > 0 && secondNr > 0) {
                int occurrences = Math.min(firstNr, secondNr);
                // Jeśli para składa się z dwóch takich samych liczb, dzielimy wystąpienia przez 2
                if (i == EXPECTED_SUM - i) {
                    occurrences /= 2;
                }
                result.addAll(Collections.nCopies(occurrences, new Pair(i, EXPECTED_SUM - i)));
            }
        }

        return result;
    }


    public record Pair(int first, int second) {
        @Override
        public String toString() {
            return String.format("[%d,%d]", this.first, this.second);
        }
    }
}
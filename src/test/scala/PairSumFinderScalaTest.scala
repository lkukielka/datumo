import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import scala.io.Source
import scala.util.Using

class PairSumFinderScalaTest extends AnyFunSuite with Matchers with TableDrivenPropertyChecks {

  import PairSumFinderScala._

  private def loadTestCases(): List[(List[Int], List[Pair])] = {
      Using(Source.fromResource("test-cases.csv")) { source =>
        source.getLines()
          .drop(1)
          .map { line =>
            val Array(input, expected) = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", 2)
            val inputList =
              if (input.trim.isEmpty) List.empty[Int]
              else input
                .replaceAll("\"", "")
                .split(",")
                .filter(pairStr => !pairStr.isBlank)
                .map(_.trim)
                .map(_.toInt)
                .toList

            val expectedPairs =
              if (expected.trim.isEmpty) List.empty[Pair]
              else expected
                .replaceAll("\"", "")
                .trim.split(",")
                .filter(pairStr => !pairStr.isBlank)
                .map { pairStr =>
                  val Array(first, second) = pairStr.split(":")
                  Pair(first.toInt, second.toInt)
                }.toList

            (inputList, expectedPairs)
          }.toList
      }.getOrElse(List.empty)
  }

  private lazy val testCases = loadTestCases()

  test("Test cases from CSV file") {
    forAll(Table(("input", "expected"), testCases: _*)) { (input, expected) =>
      withClue(s"Failed for input: $input") {
        validateResult(input, expected)
      }
    }
  }

  private def normalizePairs(pairs: List[Pair]): List[Pair] =
    pairs.map(p => Pair(math.min(p.first, p.second), math.max(p.first, p.second)))

  private def countPairs(pairs: List[Pair]): Map[Pair, Int] =
    pairs.groupBy(identity).view.mapValues(_.size).toMap

  private def validateResult(input: List[Int], expected: List[Pair]): Unit = {
    val actual = findPairsWithSum(input)
    countPairs(normalizePairs(actual)) shouldEqual countPairs(normalizePairs(expected))
  }
}
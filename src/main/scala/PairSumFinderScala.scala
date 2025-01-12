object PairSumFinderScala {
  private val ExpectedSum = 12

  case class Pair(first: Int, second: Int) {
    override def toString: String = s"[$first,$second]"
  }

  def findPairsWithSum(numbers: List[Int]): List[Pair] = {
    val numberToOccurrences = numbers.groupBy(identity).view.mapValues(_.size).toMap

    (0 to ExpectedSum / 2).flatMap { i =>
      val firstNr = numberToOccurrences.getOrElse(i, 0)
      val secondNr = numberToOccurrences.getOrElse(ExpectedSum - i, 0)

      if (firstNr > 0 && secondNr > 0) {
        val occurrences = if (i == ExpectedSum - i) firstNr / 2 else math.min(firstNr, secondNr)
        List.fill(occurrences)(Pair(i, ExpectedSum - i))
      } else {
        Nil
      }
    }.toList
  }
}
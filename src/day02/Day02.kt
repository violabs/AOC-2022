package day02


import readInput

fun main() {
    test1("day-02-test-input-01", 15)
    test2("day-02-test-input-01", 12)
}

private fun test1(filename: String, expected: Int) {
    val input = readInput("day02/$filename")

    val actual: Int = runGuessingGame(input)

    println("EXPECT: $expected")
    println("ACTUAL: $actual")

    check(expected == actual)
}

private fun test2(filename: String, expected: Int) {
    val input = readInput("day02/$filename")

    val actual: Int = runKnownGame(input)

    println("EXPECT: $expected")
    println("ACTUAL: $actual")

    check(expected == actual)
}

enum class Shape(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    companion object {
        val symbolMap: Map<String, Shape> = mapOf(
            "A" to ROCK,
            "X" to ROCK,
            "B" to PAPER,
            "Y" to PAPER,
            "C" to SCISSORS,
            "Z" to SCISSORS
        )

        fun findShape(opponentShape: Shape, outcome: Outcome): Shape = when (outcome) {
            Outcome.LOSE -> findShapeForLose(opponentShape)
            Outcome.DRAW -> opponentShape
            Outcome.WIN -> findShapeForWin(opponentShape)
        }

        private fun findShapeForLose(opponentShape: Shape): Shape = when (opponentShape) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }

        private fun findShapeForWin(opponentShape: Shape): Shape = when (opponentShape) {
            ROCK -> PAPER
            PAPER -> SCISSORS
            SCISSORS -> ROCK
        }
    }
}

enum class Outcome(val score: Int) {
    LOSE(0),
    DRAW(3),
    WIN(6);

    companion object {
        val symbolMap: Map<String, Outcome> = mapOf(
            "X" to LOSE,
            "Y" to DRAW,
            "Z" to WIN
        )

        fun findOutcome(opponentShape: Shape, selfShape: Shape): Outcome = when (opponentShape) {
            Shape.ROCK -> findOutcomeAgainstRock(selfShape)
            Shape.PAPER -> findOutcomeAgainstPaper(selfShape)
            Shape.SCISSORS -> findOutcomeAgainstScissors(selfShape)
        }

        private fun findOutcomeAgainstRock(selfShape: Shape): Outcome = when (selfShape) {
            Shape.ROCK -> DRAW
            Shape.PAPER -> WIN
            Shape.SCISSORS -> LOSE
        }

        private fun findOutcomeAgainstPaper(selfShape: Shape): Outcome = when (selfShape) {
            Shape.ROCK -> LOSE
            Shape.PAPER -> DRAW
            Shape.SCISSORS -> WIN
        }

        private fun findOutcomeAgainstScissors(selfShape: Shape): Outcome = when (selfShape) {
            Shape.ROCK -> WIN
            Shape.PAPER -> LOSE
            Shape.SCISSORS -> DRAW
        }
    }
}

private fun runGuessingGame(input: List<String>): Int {
    return input
        .asSequence()
        .map { it.split(" ") }
        .map(::GuessedRound)
        .map(GuessedRound::score)
        .sum()
}

class GuessedRound(selections: List<String>) {
    private val opponentShape: Shape = Shape.symbolMap[selections[0]] ?: throw Exception("Missing opponent selection")

    private val myShape: Shape = Shape.symbolMap[selections[1]] ?: throw Exception("Missing my selection")

    private val outcome: Outcome = Outcome.findOutcome(opponentShape, myShape)

    val score: Int = myShape.score + outcome.score
}

private fun runKnownGame(input: List<String>): Int {
    return input
        .asSequence()
        .map { it.split(" ") }
        .map(::KnownRound)
        .map(KnownRound::score)
        .sum()
}

class KnownRound(selections: List<String>) {
    private val opponentShape: Shape = Shape.symbolMap[selections[0]] ?: throw Exception("Missing opponent selection")

    private val outcome: Outcome = Outcome.symbolMap[selections[1]] ?: throw Exception("Missing outcome")

    private val myShape: Shape = Shape.findShape(opponentShape, outcome)

    val score: Int = myShape.score + outcome.score
}
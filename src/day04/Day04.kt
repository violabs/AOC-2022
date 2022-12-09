package day04

import readInput


fun main() {
    test1("day-04-test-input-01", 2)
}

private fun test1(filename: String, expected: Int) {
    val input = readInput("day04/$filename")

    val actual: Int = findHighPriorityCleaningReassignments(input)

    println("EXPECT: $expected")
    println("ACTUAL: $actual")

    check(expected == actual)
}

private fun findHighPriorityCleaningReassignments(input: List<String>): Int {
    return input
        .asSequence()
        .map(::WorkPair)
        .filter(WorkPair::containsHighPriorityReassignment)
        .count()
}

class WorkPair(details: String) {
    private val splitDetails = details.split(",")
    private val firstSections: Pair<Int, Int> = createRange(splitDetails[0])
    private val secondSections: Pair<Int, Int> = createRange(splitDetails[1])


    private fun createRange(rangeString: String): Pair<Int, Int> {
        val sectionEnds = rangeString.split("-")

        return sectionEnds[0].toInt() to sectionEnds[1].toInt()
    }

    fun containsHighPriorityReassignment(): Boolean =
        firstSections.contains(secondSections) || secondSections.contains(firstSections)
}

fun Pair<Int, Int>.contains(other: Pair<Int, Int>): Boolean = this.first <= other.first && this.second >= other.second
package day02

import readInput


fun main() {
    test("day-02-test-input-01", 45000)
}

private fun test(filename: String, expected: Int) {
    val input = readInput("day02/$filename")

    val actual: Int = findPositionOfElfWithMostCalories(input)

    println("EXPECT: $expected")
    println("ACTUAL: $actual")

    check(expected == actual)
}

const val E = ""

private fun findPositionOfElfWithMostCalories(input: List<String>): Int {
    val tracker = ElfCalorieTracker()

    for (calories in input) {
        if (calories == E) {
            tracker.moveToNextElf()
            continue
        }

        tracker.addCaloriesToSum(calories)
    }

    return tracker
        .finalCalories()
        .sortedDescending()
        .take(3)
        .sum()
}

class ElfCalorieTracker(
    var currentSum: Int = 0,
    var caloriesList: MutableList<Int> = mutableListOf()
) {


    fun moveToNextElf() {
        caloriesList.add(currentSum)

        currentSum = 0
    }

    fun addCaloriesToSum(calories: String) {
        currentSum += calories.toInt()
    }

    fun finalCalories(): MutableList<Int> = caloriesList.also { it.add(currentSum) }
}

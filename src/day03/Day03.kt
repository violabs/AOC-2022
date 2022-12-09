package day03

import readInput

fun main() {
    test1("day-03-test-input-01", 157)
    test2("day-03-test-input-01", 70)
    test2("day-03-actual-test-input", 70)
}

private fun test1(filename: String, expected: Int) {
    val input = readInput("day03/$filename")

    val actual: Int = checkRucksacksForDuplicateItems(input)

    println("EXPECT: $expected")
    println("ACTUAL: $actual")

    check(expected == actual)
}

private fun test2(filename: String, expected: Int) {
    val input = readInput("day03/$filename")

    val actual: Int = checkGroupRucksackBadges(input)

    println("EXPECT: $expected")
    println("ACTUAL: $actual")

    check(expected == actual)
}

val priority = ('a' .. 'z') + ('A' .. 'Z')

fun checkRucksacksForDuplicateItems(ledger: List<String>): Int {
    return ledger
        .asSequence()
        .map(::Rucksack)
        .flatMap(Rucksack::findDuplicates)
        .map(priority::indexOf)
        .map(1::plus)
        .sum()
}

class Rucksack(contents: String) {
    private val capacity: Int = contents.length
    private val half = capacity / 2
    private val firstCompartment: String = contents.substring(0 until half)
    private val secondCompartment: String = contents.substring(half until capacity)

    fun findDuplicates(): Sequence<Char> =
        firstCompartment
            .asSequence()
            .filter(secondCompartment::contains)
            .distinct()
}

fun checkGroupRucksackBadges(ledger: List<String>): Int {
    return ledger
        .asSequence()
        .chunked(3)
        .map(::ElfGroup)
        .map(ElfGroup::findBadgeSymbol)
        .map(priority::indexOf)
        .map(1::plus)
        .sum()
}

class ElfGroup(rucksacks: List<String>) {
    private val sortedSacks: List<String> = rucksacks.sortedBy(String::length)

    private val smallestSack = sortedSacks[0]
    private val sack2 = sortedSacks[1]
    private val sack3 = sortedSacks[2]

    fun findBadgeSymbol(): Char =
        smallestSack
            .firstOrNull { sack2.contains(it) && sack3.contains(it) }
            ?: throw Exception("Missing badge")
}
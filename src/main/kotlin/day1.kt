import util.readFile

private fun part1(): Int {
    // a + b = 2020
    // a = 2020 - b
    val input = readFile("day1.txt").map { it.toInt() }
    // for each number, subtract form 2020 and then search the list for that value
    for (i in input) {
        val target = 2020 - i
        for (j in input) {
            if (target == j) {
                return i * j
            }
        }
    }
    return -1
}

private fun part2(): Int {
    // a + b + c = 2020
    // b + c = 2020 - a
    // b + c = remainingSum
    // c = remainingSum - b
    val input = readFile("day1.txt").map { it.toInt() }
    for (a in input) {
        // hash set used because we can do fast lookups by hash instead of linear traversal of list with 3 for loops
        val hashSet = HashSet<Int>()
        val remainingSum = 2020 - a
        for (b in input) {
            if (hashSet.contains(remainingSum - b)) {
                return a * b * (remainingSum - b)
            }
            hashSet.add(b)
        }
    }
    return -1
}

fun main() {
    println("Day 1")
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

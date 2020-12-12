import util.readFile

val input = readFile("day9.txt").map { it.toLong() }
val windowSize = 25

// [1, 2, 3, 4, 5] 6 must be sum of two of the previous 5 numbers
// iterate over prev values in window, use 2sum approach with a hashSet and search for 6 - i
// if found, move window, if not found, return num
// use index and index + windowSize to keep track of window, doesn't need to be a new list
// already know values in hashSet, just need to remove the value that falls out of the window and add new value

fun part1(): Long {
    for (i in input.indices - windowSize) {
        val seenBefore = HashSet<Long>()
        var found = false
        for (j in i until i + windowSize) {
            val target = input[i + windowSize] - input[j]
            if (seenBefore.contains(target)) {
                found = true
                break
            }
            seenBefore.add(input[j])
        }
        if (!found) return input[i + windowSize]
    }
    return -1
}

// use a sliding window, add elements to the window while sum < target, if sum > target remove elements from start of window

fun part2(): Long {
    val target = part1()
    var i = 0
    var j = 1
    while (i < input.size && j < input.size) {
        val currentSum = input.subList(i, j).sum()
        if (currentSum == target) break
        else if (currentSum < target) j++
        else i++
    }
    return input.subList(i, j).maxOrNull()!! + input.subList(i, j).minOrNull()!!
}

println("Part 1: ${part1()}")
println("Part 2: ${part2()}")

import util.readFile

// put input contents into a list of Pairs (op, value)
// keep a hashSet of visited indices
// start at the beginning of the list executing instructions
// use a while loop, keep track of current index and current sum
// index & sum start at 0
// while(true)
// noop = index += 1
// acc = sum += value
// jmp = index += value
// at the start of every loop check hashSet to see if we have been here before
// if yes, return sum
// at the end of every loop, add index to hashSet

val input = readFile("day8.txt")

fun parseInput(input: List<String>): List<Pair<String, Int>> {
    val regex = Regex("(\\w{3}) ([+-]\\d+)")
    val list = mutableListOf<Pair<String, Int>>()
    input.forEach {
        val regexMatch = regex.find(it)!!.groups
        list.add(Pair(regexMatch[1]!!.value, regexMatch[2]!!.value.toInt()))
    }
    return list
}

fun part1(): Int {
    val parsedInput = parseInput(input)

    var accumulator = 0
    var index = 0
    val seenBefore = HashSet<Int>()
    while (true) {
        if (seenBefore.contains(index)) return accumulator

        val currentPair = parsedInput[index]
        val prevIndex = index
        when (currentPair.first) {
            "nop" -> {
                index++
            }
            "acc" -> {
                accumulator += currentPair.second
                index++
            }
            "jmp" -> {
                index += currentPair.second
            }
        }
        seenBefore.add(prevIndex)
    }
}

fun generateModifiedInstructions(input: List<Pair<String, Int>>): MutableList<MutableList<Pair<String, Int>>> {
    val modifiedInstructions = mutableListOf<MutableList<Pair<String, Int>>>()
    var j = 0
    for (i in input.indices) {
        if (input[i].first == "nop" || input[i].first == "jmp") {
            modifiedInstructions.add(input.toMutableList())
            modifiedInstructions[j][i] =
                if (input[i].first == "nop") Pair("jmp", input[i].second) else Pair("nop", input[i].second)
            j++
        }
    }
    return modifiedInstructions
}

fun part2(): Int {
    val parsedInput = parseInput(input)
    generateModifiedInstructions(parsedInput).forEach {
        var accumulator = 0
        var index = 0
        val seenBefore = HashSet<Int>()
        while (true) {
            if (seenBefore.contains(index)) break

            val currentPair = it[index]
            val prevIndex = index
            when (currentPair.first) {
                "nop" -> {
                    index++
                }
                "acc" -> {
                    accumulator += currentPair.second
                    index++
                }
                "jmp" -> {
                    index += currentPair.second
                }
            }
            if (index == input.size) return accumulator
            seenBefore.add(prevIndex)
        }
    }
    return -1
}

println("Part 1: ${part1()}")
println("Part 2: ${part2()}")

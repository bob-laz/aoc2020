import util.readFile

// sort input list
// start at zero, take first element in list, if diff of 1, increment ones counter, if diff of 3, increment threes counter

val input = readFile("day10.txt").map { it.toInt() }.sorted()

fun part1(): Int {
    var currentJolt = 0
    var onesCounter = 0
    var threesCounter = 1 // starts at 1 to account for built in adapter increment

    input.forEach {
        when {
            it - currentJolt == 1 -> onesCounter++
            it - currentJolt == 3 -> threesCounter++
            else -> throw IllegalStateException("diff is not 1 or 3")
        }
        currentJolt = it
    }

    println("onesCounter: $onesCounter")
    println("threesCounter: $threesCounter")
    return onesCounter * threesCounter
}

// use a map to keep track of number of paths to each voltage
// iterate over input, check map for # of paths to voltage - 1, voltage - 2, voltage - 3, sum these 3 values
// sum of last 3 values is # of paths to current voltage, add this to map
// total number of paths to last voltage is map value for large input voltage
fun part2(): Long {
    val voltagePathMap: MutableMap<Int, Long> = mutableMapOf(0 to 1L)
    input.forEach { voltage ->
        voltagePathMap[voltage] = (1..3).map { lookBehind ->
            voltagePathMap.getOrDefault(voltage - lookBehind, 0)
        }.sum()
    }
    return voltagePathMap.getValue(input.last())
}

println("Part 1: ${part1()}")
println("Part 2: ${part2()}")

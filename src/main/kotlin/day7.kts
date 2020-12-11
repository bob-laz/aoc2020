import util.readFile

// light red -> bright white, muted yellow TRUE
// dark orange -> bright white, muted yellow TRUE
// bright white -> shiny gold (BASE_CASE) TRUE
// muted yellow bags -> shiny gold (BASE_CASE), faded blue TRUE
// shiny gold -> dark olive, vibrant plum
// dark olive -> faded blue, dotted black
// vibrant plum -> faded blue, dotted black
// faded blue -> BASE_CASE
// dotted black -> BASE_CASE

// build a map of rules by parsing input, only concerned with color
// write a recursive function to check whether a color can contain a shiny gold
// keep a map of cached results as I discover true/false for colors
// recursive function will check whether input color is in map, if so return result
// next check rule list for BASE_CASE (shiny gold = true or empty)
// otherwise call function with value(s) found in rule map for this color to check next color(s)

val input = readFile("day7.txt")

fun parseInput(): MutableMap<String, List<String>?> {
    val lineRegex = Regex("([\\w ]+) bags contain ([\\w ,]+)")
    val contentsRegex = Regex("\\d ([\\w ]+) bag")

    val ruleMap = mutableMapOf<String, List<String>?>()

    for (line in input) {
        val regexMatch = lineRegex.find(line)
        val key = regexMatch!!.groups[1]!!.value
        val value = regexMatch.groups[2]!!.value

        if (value == "no other bags") ruleMap[key] = null
        else {
            val valueList = contentsRegex.findAll(value).map { it.groups[1]!!.value }.toList()
            if (ruleMap[key] == null) ruleMap[key] = valueList else ruleMap[key]!!.plus(valueList)
        }
    }
    return ruleMap
}

fun parseInputWithCount(): MutableMap<String, List<Pair<String, Int>>?> {
    val lineRegex = Regex("([\\w ]+) bags contain ([\\w ,]+)")
    val contentsRegex = Regex("(\\d) ([\\w ]+) bag")

    val ruleMap = mutableMapOf<String, List<Pair<String, Int>>?>()

    for (line in input) {
        val regexMatch = lineRegex.find(line)
        val key = regexMatch!!.groups[1]!!.value
        val value = regexMatch.groups[2]!!.value

        if (value == "no other bags") ruleMap[key] = null
        else {
            val valueList =
                contentsRegex.findAll(value).map { Pair(it.groups[2]!!.value, it.groups[1]!!.value.toInt()) }
                    .toList()
            if (ruleMap[key] == null) ruleMap[key] = valueList else ruleMap[key]!!.plus(valueList)
        }
    }
    return ruleMap
}

fun checkContents(
    color: String,
    ruleMap: Map<String, List<String>?>,
    resultsMap: MutableMap<String, Boolean>
): Boolean {
    when {
        resultsMap.containsKey(color) -> {
            return resultsMap[color]!!
        }
        ruleMap[color] == null -> {
            resultsMap[color] = false
            return false
        }
        ruleMap[color]?.contains("shiny gold")!! -> {
            resultsMap[color] = true
            return true
        }
        else -> {
            var returnVal = false
            ruleMap[color]?.forEach {
                returnVal = returnVal || checkContents(it, ruleMap, resultsMap)
            }
            resultsMap[color] = returnVal
            return returnVal
        }
    }
}

fun countContents(
    color: String,
    ruleMap: MutableMap<String, List<Pair<String, Int>>?>,
    resultsMap: MutableMap<String, Int>,
    sum: Int
): Int {
    when {
        resultsMap.containsKey(color) -> {
            return resultsMap[color]!!
        }
        ruleMap[color] == null -> {
            resultsMap[color] = sum
            return sum
        }
        else -> {
            var runningSum = sum
            ruleMap[color]!!.forEach {
                val contentCount = countContents(it.first, ruleMap, resultsMap, sum)
                runningSum += if (contentCount == 0) it.second else it.second * contentCount + it.second
            }
            resultsMap[color] = runningSum
            return runningSum
        }
    }
}

fun part1(): Int {
    val ruleMap = parseInput()
    val resultsMap = mutableMapOf<String, Boolean>()
    ruleMap.keys.forEach { checkContents(it, ruleMap, resultsMap) }
    return resultsMap.values.count { value -> value }
}

fun part2(): Int {
    val ruleMap = parseInputWithCount()
    return countContents("shiny gold", ruleMap, mutableMapOf(), 0)
}

println("part 1: ${part1()}")
println("part 2: ${part2()}")

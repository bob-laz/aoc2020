package util

fun readFile(fileName: String): List<String> {
    val clazzLoader = object {}::class.java.classLoader
    val stream = clazzLoader.getResourceAsStream(fileName)
    val reader = stream?.bufferedReader()
    if (reader != null) {
        val data = reader.readLines()
        reader.close()
        return data
    }
    throw RuntimeException("The requested file $fileName not found")
}

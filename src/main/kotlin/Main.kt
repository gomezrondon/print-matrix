import java.io.File

fun main() {

/*
Before
    id,first_name,last_name,email,gender,ip_address
    1,Dagmar,Fortnon,dfortnon0@theglobeandmail.com,Female,247.251.222.16
    2,Kyle,Surridge,ksurridge1@shinystat.com,Female,234.33.225.78
    ----
After
 id  | first_name | last_name    | email                              | gender     | ip_address
 1   | Dagmar     | Fortnon      | dfortnon0@theglobeandmail.com      | Female     | 247.251.222.16
 2   | Kyle       | Surridge     | ksurridge1@shinystat.com           | Female     | 234.33.225.78
    */

    val delimiters = ","
    val readLines = File("data/MOCK_DATA.csv").readLines()

    val dataFrame = loadMatrix(readLines, delimiters)

    val outSeparator = "|"
    val toList = printMatrix(dataFrame,outSeparator)

    toList
//        .take(10)
        .forEach { println(it) }

}// end

private fun printMatrix(dataFrame: MatrixData, outSeparator: String ): List<String> {
    val sizeList= dataFrame.getMaxColumnSizes()
    val toList = (0 until dataFrame.getRowSize()).map { row ->
        (0 until dataFrame.getColumSize()).map { col ->
            val insrtStr = dataFrame.matrix[col][row]
            val str = " ".repeat(sizeList[col]) + " "
            val outStr = insertString(str, insrtStr, 2) ?: ""
            outStr
        }.joinToString(outSeparator)

    }.toList()
    return toList
}


// create a method that replace the last n characters of a string with a given string
// example: "FXG0000" , "10" , 6 -> "FXG0010"
fun insertString(cadena: String, inserta: String, posicion: Int): String? {
    var posicion = posicion
    posicion--
    val algo = cadena.length - inserta.length
    if (posicion < 0) {
        posicion = 0
    }
    if (posicion > algo) {
        posicion = algo
    }
    val sb = StringBuilder(cadena)
    sb.replace(posicion, posicion + inserta.length, inserta)
    return sb.substring(0, cadena.length)
}


data class MatrixData(val matrix: MutableList<List<String>>){
    fun getRowSize(): Int = matrix[0].size
    fun getColumSize(): Int = matrix.size

    fun getMaxColumnSizes(): MutableList<Int> {
        val sizeList = mutableListOf<Int>()

        (0 until this.getColumSize()).forEach { col ->
            val max = findMax(this.matrix[col])?:0
            sizeList.add(max)
        }
        return sizeList
    }
}

/***
 * load a line by line table of data into a matrix
 * readLines: the list of lines of text
 * delimiter: the delimiter of the data to be splitter by
 * return a list of list
 */
private fun loadMatrix(readLines: List<String>, delimiters: String): MatrixData {
    val matrix = mutableListOf<List<String>>()
    val sizeRow = readLines.size
    val sizeCol = readLines[0].split(delimiters).size

    (0 until sizeCol).forEach { colIndex ->
        val colList = mutableListOf<String>()
        (0 until sizeRow).forEach { rowIndex ->
            val value = readLines[rowIndex].split(delimiters)[colIndex]
            colList.add(value)
        }
        matrix.add(colList)
    }

    return MatrixData(matrix)
}


fun findMax(list: List<String>): Int? {
    return list.map { it.length }.max()
}
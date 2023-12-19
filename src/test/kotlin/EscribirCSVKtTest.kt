import org.apache.commons.csv.CSVFormat
import org.isc4151.dan.creadorManual.CompendioPracticas
import org.isc4151.dan.creadorManual.Practica
import org.isc4151.dan.creadorManual.escribirCSV
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.io.FileWriter
import java.io.InputStream
import java.nio.file.Paths

class EscribirCSVKtTest {

    @AfterEach
    fun tearDown() {
        File("src/test/resources/todos.csv").delete()
    }

    @Test
    fun escribirCSV() {
        val practicas1 = listOf(
            Practica("Numeros pares", 1, "Switch", "", null),
            Practica("Numeros impares", 2, "If-else", "", null)
        )
        val primerCompendio = CompendioPracticas(1,practicas1,2, Paths.get("src/test/resources/"))
        val practicas2 = listOf(
            Practica("Posada en algunas cosas", 3, "", "", null),
            Practica("Arbol de navidad", 4, "NAVIDAD", "", null)
        )
        val segundoCompendio = CompendioPracticas(1,practicas2,2, Paths.get("src/test/resources/"))
        val archivoCSV = FileWriter("src/test/resources/todos.csv")
        archivoCSV.escribirCSV(listOf(primerCompendio,segundoCompendio))
        archivoCSV.close()
        assert(File("src/test/resources/todos.csv").exists())
        val titulos = this.leerCSV(File("src/test/resources/todos.csv").inputStream())
        assertEquals("Numeros pares (Switch) y numeros impares (If-else)",titulos[0])
        assertEquals("Posada en algunas cosas y arbol de navidad (NAVIDAD)",titulos[1])
    }

    // Retorna una lista con los titutlos, solo de esa columna
    private fun leerCSV(iS: InputStream): List<String> {
        return CSVFormat.Builder.create(CSVFormat.DEFAULT).build().parse(iS.reader()).drop(1).map { it[0] }
    }
}
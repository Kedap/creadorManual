import org.apache.commons.csv.CSVFormat
import org.isc4151.dan.creadorManual.*
import org.isc4151.dan.creadorManual.lenguajes.LenguajeCPP
import org.isc4151.dan.creadorManual.utilidadesEjecutables.Capturador
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.io.FileWriter
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

class EscribirCSVKtTest {

    @AfterEach
    fun tearDown() {
        File("src/test/resources/todos.csv").delete()
    }

    private fun limpiar(p: Practica) {
        val codigoActual = p.obtenerRutacodigo()
        val codigoEsperado = Paths.get("src/test/resources/", codigoActual!!.fileName.toString())
        Files.move(codigoActual, codigoEsperado)
        File(p.rutaAbsoluta!!.toString()).deleteRecursively()
    }

    @Test
    fun escribirCSV() {
        val practicas = listOf(
            Practica(
                "Prueba1",
                2,
                "Practica super chida",
                "src/test/resources/vacia.cpp",
                Paths.get("src/test/resources/2"),
                LenguajeCPP(ConfiguracionTest.COMPILADORCPP.cmd, listOf())
            )
        )
        val primerCompendio = CompendioPracticas(2, practicas, 1, Paths.get("src/test/resources/"))
        val archivoCSV = FileWriter("src/test/resources/todos.csv")
        practicas[0].generarCarpetaTrab()
        practicas[0].compilar()
        practicas[0].crearEntradas()
        practicas[0].ejecutar()
        val silicon = Capturador(ConfiguracionTest.CAPTURADOR.cmd, "silicon", null)
        practicas[0].crearCapturas(silicon)
        archivoCSV.escribirCSV(listOf(primerCompendio))
        archivoCSV.close()
        assert(File("src/test/resources/todos.csv").exists())
        val titulos = this.leerCSV(File("src/test/resources/todos.csv").inputStream())
        assertEquals("Prueba1 (Practica super chida)", titulos[0])
        this.limpiar(practicas[0])
    }

    @Test
    fun escribirCSVImagenes() {
        val ejemplo = Practica(
            "Prueba2",
            2,
            "",
            "src/test/resources/vacia.cpp",
            Paths.get("src/test/resources/2"),
            LenguajeCPP(ConfiguracionTest.COMPILADORCPP.cmd, listOf())
        )
        ejemplo.generarCarpetaTrab()
        ejemplo.compilar()
        ejemplo.crearEntradas()
        ejemplo.ejecutar()
        val silicon = Capturador(ConfiguracionTest.CAPTURADOR.cmd, "silicon", null)
        ejemplo.crearCapturas(silicon)
        val primerCompendio = CompendioPracticas(2, listOf(ejemplo), 1, Paths.get("src/test/resources"))
        val archivoCSV = FileWriter("src/test/resources/todos.csv")
        archivoCSV.escribirCSV(listOf(primerCompendio))
        archivoCSV.close()
        val imagenesCodigo = this.leerCSVImgCodigo(File("src/test/resources/todos.csv").inputStream())
        val imagenesSalida = this.leerCSVImgSalida(File("src/test/resources/todos.csv").inputStream())
        if (obtenerOS() == SistemaOperativo.LINUX) {
            assert(imagenesCodigo[0].contains("src/test/resources/2/capturas/codigos/vacia.png"))
            assert(imagenesSalida[0].contains("src/test/resources/2/capturas/salidas/vacia.png"))
        } else {
            assert(imagenesCodigo[0].contains("src\\\\test\\\\resources\\\\2\\\\capturas\\\\codigos\\\\vacia.png"))
            assert(imagenesSalida[0].contains("src\\\\test\\\\resources\\\\2\\\\capturas\\\\salidas\\\\vacia.png"))
        }
        this.limpiar(ejemplo)
    }

    // Retorna una lista con los titutlos, solo de esa columna
    private fun leerCSV(iS: InputStream): List<String> {
        return CSVFormat.Builder.create(CSVFormat.DEFAULT).build().parse(iS.reader()).drop(1).map { it[0] }
    }

    // Retorna una lista con las imagenes del codigo, solo de esa columna
    private fun leerCSVImgCodigo(iS: InputStream): List<String> {
        return CSVFormat.Builder.create(CSVFormat.DEFAULT).build().parse(iS.reader()).drop(1).map { it[2] }
    }

    // Retorna una lista con las imagenes de las salidas, solo de esa columna
    private fun leerCSVImgSalida(iS: InputStream): List<String> {
        return CSVFormat.Builder.create(CSVFormat.DEFAULT).build().parse(iS.reader()).drop(1).map { it[3] }
    }
}
import lenguajes.ConfiguracionDeTest
import org.isc4x51.dan.creadorManual.Lenguajes.LenguajeCPP
import org.isc4x51.dan.creadorManual.Practica
import org.isc4x51.dan.creadorManual.lenguajes.LenguajeJava
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

class PracticaTest {
    private fun limpiarCPP(p: Practica) {
        val codigoActual = p.getRutaCodigo()
        val codigoEsperado = Path("src/test/resources/cpp").resolve(codigoActual!!.fileName)
        Files.move(codigoActual, codigoEsperado)
        p.rutaAbsoluta.toFile().deleteRecursively()
    }

    private fun limpiarJava(p: Practica) {
        val codigoActual = p.getRutaCodigo()
        val codigoEsperado = Path("src/test/resources/java").resolve(codigoActual!!.fileName)
        Files.move(codigoActual, codigoEsperado)
        p.rutaAbsoluta.toFile().deleteRecursively()
    }

    @Test
    fun compilar() {
        val ejemplo = Practica(
            0,
            "Prueba1",
            "",
            "simpleCpp.cpp",
            Path("src/test/resources/cpp"),
            Path("src/test/resources/cpp/1"),
            LenguajeCPP(
                ConfiguracionDeTest.COMPILADORCPP.cmd,
                listOf()
            )
        )
        ejemplo.generarCarpetaTrabajo()
        ejemplo.compilar()
        assertNotEquals(ejemplo.getRutaProducto(), null)
        assert(ejemplo.getRutaProducto()!!.exists())
        limpiarCPP(ejemplo)
    }

    @Test
    fun compilarJava() {
        val ejemplo = Practica(
            0,
            "Prueba1",
            "",
            "holaMundo.java",
            Path("src/test/resources/java"),
            Path("src/test/resources/java/1"),
            LenguajeJava(
                "javac",
                listOf(),
                "java",
                listOf()
            )
        )
        ejemplo.generarCarpetaTrabajo()
        ejemplo.compilar()
        assertNotEquals(ejemplo.getRutaProducto(), null)
        assert(ejemplo.getRutaProducto()!!.exists())
        limpiarJava(ejemplo)
    }

    @Test
    fun compilarCompiladorArgumentos() {
        val ejemplo = Practica(
            0,
            "Prueba1",
            "",
            "simpleCpp.cpp",
            Path("src/test/resources/cpp"),
            Path("src/test/resources/cpp/1"),
            LenguajeCPP(
                ConfiguracionDeTest.COMPILADORCPP.cmd,
                listOf("-Wall")
            )
        )
        ejemplo.generarCarpetaTrabajo()
        ejemplo.compilar()
        assertNotEquals(ejemplo.getRutaProducto(), null)
        assert(ejemplo.getRutaProducto()!!.exists())
        limpiarCPP(ejemplo)
    }

    @Test
    fun compilarArgumentosJava() {
        val ejemplo = Practica(
            0,
            "Prueba1",
            "",
            "holaMundo.java",
            Path("src/test/resources/java"),
            Path("src/test/resources/java/1"),
            LenguajeJava(
                "javac",
                listOf("-source", "8"),
                "java",
                listOf()
            )
        )
        ejemplo.generarCarpetaTrabajo()
        ejemplo.compilar()
        assertNotEquals(ejemplo.getRutaProducto(), null)
        assert(ejemplo.getRutaProducto()!!.exists())
        limpiarJava(ejemplo)
    }

    @Test
    fun crearEntradasVacias() {
        val ejemplo = Practica(
            0,
            "Prueba1",
            "",
            "vacia.cpp",
            Path("src/test/resources/cpp"),
            Path("src/test/resources/cpp/1"),
            LenguajeCPP(
                ConfiguracionDeTest.COMPILADORCPP.cmd,
                listOf("-Wall")
            )
        )
        ejemplo.generarCarpetaTrabajo()
        ejemplo.compilar()
        ejemplo.generarEntradas()
        val entradas = ejemplo.getEntradas()
        assertNotEquals(entradas, null)
        if (entradas.isNotEmpty()) assertEquals(
            "    std::cout << \"Hola mundo!\" << std::endl;",
            entradas.first().entrada
        )
        limpiarCPP(ejemplo)
    }

    @Test
    fun ejecutar() {
    }

    @Test
    fun modificarEntrada() {
    }

    @Test
    fun crearCapturas() {
    }

    @Test
    fun generarCarpetaTrabajo() {
    }

    @Test
    fun getRutaCapturas() {
    }

    @Test
    fun extisteCodigoFuente() {
    }

    @Test
    fun getRutaProducto() {
    }

    @Test
    fun getEntradas() {
    }

    @Test
    fun getNombre() {
    }

    @Test
    fun getCarpetaCodigos() {
    }

    @Test
    fun getRutaAbsoluta() {
    }
}
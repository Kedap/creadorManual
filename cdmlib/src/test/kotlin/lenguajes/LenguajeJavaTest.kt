package lenguajes

import org.isc4x51.dan.creadorManual.lenguajes.LenguajeJava
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.nio.file.Path
import kotlin.io.path.Path

class LenguajeJavaTest {
    private fun limpiar(archivo: Path) {
        archivo.toFile().delete()
    }

    @Test
    fun compilar() {
        val lenguaje = LenguajeJava(
            ConfiguracionDeTest.COMPILADORJAVA.cmd,
            listOf(),
            ConfiguracionDeTest.EJECUTADORJAVA.cmd,
            listOf()
        )
        val codigo = Path("src/test/resources/pruebaJava/holaMundo.java")
        var salida = Path("src/test/resources/pruebaJava")
        salida = lenguaje.compilar(codigo, salida)
        assert(salida.toFile().exists())
        limpiar(salida)
    }

    @Test
    fun compilarBanderas() {
        val lenguaje = LenguajeJava(
            ConfiguracionDeTest.COMPILADORJAVA.cmd,
            listOf("-source", "8"),
            ConfiguracionDeTest.EJECUTADORJAVA.cmd,
            listOf()
        )
        val codigo = Path("src/test/resources/pruebaJava/holaMundo.java")
        var salida = Path("src/test/resources/pruebaJava")
        salida = lenguaje.compilar(codigo, salida)
        assert(salida.toFile().exists())
        this.limpiar(salida)
    }

    @Test
    fun obtenerEjecucion() {
        val lenguaje = LenguajeJava(
            ConfiguracionDeTest.COMPILADORJAVA.cmd,
            listOf("-source", "8"),
            ConfiguracionDeTest.EJECUTADORJAVA.cmd,
            listOf()
        )
        val salida = Path("src/test/resources/pruebaJava/holaMundo.class")
        assertEquals(
            listOf(
                ConfiguracionDeTest.EJECUTADORJAVA.cmd,
                "-cp",
                "src/test/resources/pruebaJava",
                "holaMundo"
            ), lenguaje.obtenerEjecucion(salida)
        )
    }
}

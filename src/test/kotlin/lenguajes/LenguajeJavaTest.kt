package lenguajes

import org.isc4151.dan.creadorManual.lenguajes.LenguajeJava
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class LenguajeJavaTest {
    private fun limpiar(archivo: String) {
        File(archivo).delete()
    }

    @Test
    fun compilar() {
        val lenguaje = LenguajeJava(ConfiguracionTest.COMPILADORJAVA.cmd, listOf(), ConfiguracionTest.EJECUTADORJAVA.cmd, listOf())
        val codigo = "src/test/resources/ejemplosJava/holaMundo.java"
        var salida = "src/test/resources/ejemplosJava/"
        salida = lenguaje.compilar(codigo, salida)
        assert(File(salida).exists())
        this.limpiar(salida)
    }

    @Test
    fun compilarBanderas() {
        val lenguaje = LenguajeJava(ConfiguracionTest.COMPILADORJAVA.cmd, listOf("-source", "8"), ConfiguracionTest.EJECUTADORJAVA.cmd, listOf())
        val codigo = "src/test/resources/ejemplosJava/sumaDos.java"
        var salida = "src/test/resources/ejemplosJava/"
        salida = lenguaje.compilar(codigo, salida)
        assert(File(salida).exists())
        this.limpiar(salida)
    }

    @Test
    fun obtenerEjecucion() {
        val lenguaje = LenguajeJava(ConfiguracionTest.COMPILADORJAVA.cmd, listOf("-source", "8"), ConfiguracionTest.EJECUTADORJAVA.cmd, listOf())
        val salida = "src/test/resources/ejemplosJava/holamundo.class"
        assertEquals(listOf(ConfiguracionTest.EJECUTADORJAVA.cmd,"-cp","src/test/resources/ejemplosJava","holamundo"), lenguaje.obtenerEjecucion(salida))
    }
}
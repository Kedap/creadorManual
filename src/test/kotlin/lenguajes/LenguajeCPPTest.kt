package lenguajes

import org.isc4151.dan.creadorManual.lenguajes.LenguajeCPP
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class LenguajeCPPTest {

    private fun limipiar(archivo: String) {
        File(archivo).delete()
    }

    @Test
    fun compilar() {
        val lenguaje = LenguajeCPP(ConfiguracionTest.COMPILADORCPP.cmd, listOf())
        val codigo = "src/test/resources/prueba.cpp"
        var salida = "src/test/resources/"
        salida = lenguaje.compilar(codigo, salida)
        assert(File(salida).exists())
        this.limipiar(salida)
    }

    @Test
    fun compilarBanderas() {
        val lenguaje = LenguajeCPP(ConfiguracionTest.COMPILADORCPP.cmd, listOf("-Wall"))
        val codigo = "src/test/resources/prueba.cpp"
        var salida = "src/test/resources/"
        salida = lenguaje.compilar(codigo, salida)
        assert(File(salida).exists())
        this.limipiar(salida)
    }

    @Test
    fun compilarClang() {
        val lenguaje = LenguajeCPP(ConfiguracionTest.COMPILADORCPP_SECUNDARIO.cmd, listOf())
        val codigo = "src/test/resources/prueba.cpp"
        var salida = "src/test/resources"
        salida = lenguaje.compilar(codigo, salida)
        assert(File(salida).exists())
        this.limipiar(salida)
    }

    @Test
    fun obtenerEjecucion() {
        val lenguaje = LenguajeCPP(ConfiguracionTest.COMPILADORCPP_SECUNDARIO.cmd, listOf())
        val salida = "src/test/resources/prueba"
        assertEquals(listOf(salida), lenguaje.obtenerEjecucion(salida))
    }
}
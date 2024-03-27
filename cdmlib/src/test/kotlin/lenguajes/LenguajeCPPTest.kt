package lenguajes

import org.isc4x51.dan.creadorManual.Lenguajes.LenguajeCPP
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists

class LenguajeCPPTest {
    private fun limpiar(archivo: Path) {
        archivo.toFile().delete()
    }

    @Test
    fun compilar() {
        val lenguaje = LenguajeCPP(ConfiguracionDeTest.COMPILADORCPP.cmd, listOf())
        val codigo = Path("src/test/resources/simpleCpp.cpp")
        var salida = Path("src/test/resources/")
        salida = lenguaje.compilar(codigo, salida)
        assert(salida.exists())
        limpiar(salida)
    }

    @Test
    fun compilarBanderas() {
        val lenguaje = LenguajeCPP(ConfiguracionDeTest.COMPILADORCPP.cmd, listOf("-Wall"))
        val codigo = Path("src/test/resources/simpleCpp.cpp")
        var salida = Path("src/test/resources/")
        salida = lenguaje.compilar(codigo, salida)
        assert(salida.exists())
        limpiar(salida)
    }

    @Test
    fun compilarSecundario() {
        val lenguaje = LenguajeCPP(ConfiguracionDeTest.COMPILADORCPP_SECUNDARIO.cmd, listOf())
        val codigo = Path("src/test/resources/simpleCpp.cpp")
        var salida = Path("src/test/resources/")
        salida = lenguaje.compilar(codigo, salida)
        assert(salida.exists())
        limpiar(salida)
    }

    @Test
    fun obtenerEjecucion() {
        val lenguaje = LenguajeCPP(ConfiguracionDeTest.COMPILADORCPP_SECUNDARIO.cmd, listOf())
        val salida = "src/test/resources/prueba"
        assertEquals(listOf(salida), lenguaje.obtenerEjecucion(Path(salida)))
    }
}
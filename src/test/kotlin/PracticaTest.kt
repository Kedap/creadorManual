import org.isc4151.dan.creadorManual.Practica
import org.isc4151.dan.creadorManual.utilidadesEjecutables.Capturador
import org.isc4151.dan.creadorManual.utilidadesEjecutables.Compilador
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Paths
import java.security.MessageDigest

class PracticaTest {

    private fun limpiar(p: Practica) {
        val codigoActual = p.obtenerRutacodigo()
        val codigoEsperado = Paths.get("src/test/resources/", codigoActual!!.fileName.toString())
        Files.move(codigoActual, codigoEsperado)
        File(p.rutaAbsoluta!!.toString()).deleteRecursively()
    }

    @Test
    fun compilar() {
        val ejemplo = Practica(
            "Prueba1",
            0,
            "",
            "src/test/resources/prueba.cpp",
            Paths.get("src/test/resources/1")
        )
        val gcc = Compilador("g++", "Gnu Compiler", null)
        ejemplo.generarCarpetaTrab()
        ejemplo.compilar(gcc)
        assertNotEquals(ejemplo.obtenerRutaBinario(), null)
        assert(File(ejemplo.obtenerRutaBinario()!!).exists())
        this.limpiar(ejemplo)
    }

    @Test
    fun compilarCompiladorArgumentos() {
        val ejemplo = Practica(
            "Prueba1",
            0,
            "",
            "src/test/resources/prueba.cpp",
            Paths.get("src/test/resources/1")
        )
        val gcc = Compilador("g++", "Gnu Compiler", listOf("-Wall"))
        ejemplo.generarCarpetaTrab()
        ejemplo.compilar(gcc)
        assertNotEquals(ejemplo.obtenerRutaBinario(), null)
        assert(File(ejemplo.obtenerRutaBinario()!!).exists())
        this.limpiar(ejemplo)
    }

    @Test
    fun modificarRutaCompila() {
        val ejemplo = Practica("Prueba1", 0, "", "src/test/resources/prueba.cpp")
        ejemplo.cambiarRutaAbsoluta(Paths.get("src/test/resources/1"))
        val gcc = Compilador("g++", "Gnu Compiler", null)
        ejemplo.generarCarpetaTrab()
        ejemplo.compilar(gcc)
        assertNotEquals(ejemplo.obtenerRutaBinario(), null)
        assert(File(ejemplo.obtenerRutaBinario()!!).exists())
        this.limpiar(ejemplo)
    }

    @Test
    fun crearEntradas() {
        val ejemplo = Practica(
            "Prueba1",
            0,
            "",
            "src/test/resources/prueba.cpp",
            Paths.get("src/test/resources/1")
        )
        val gcc = Compilador("g++", "Gnu Compiler", null)
        ejemplo.generarCarpetaTrab()
        ejemplo.compilar(gcc)
        ejemplo.crearEntradas()
        val entradas = ejemplo.obtenerEntradas()
        assertNotEquals(entradas, null)
        if (entradas != null) assertNotEquals(entradas.first().entrada, null)
        this.limpiar(ejemplo)
    }

    @Test
    fun crearEntradasVacias() {
        val ejemplo = Practica(
            "Prueba2",
            1,
            "",
            "src/test/resources/vacia.cpp",
            Paths.get("src/test/resources/2")
        )
        val gcc = Compilador("g++", "Gnu Compiler", null)
        ejemplo.generarCarpetaTrab()
        ejemplo.compilar(gcc)
        ejemplo.crearEntradas()
        val entradas = ejemplo.obtenerEntradas()
        assertNotEquals(entradas, null)
        if (entradas != null) assertEquals(entradas.first().entrada, null)
        this.limpiar(ejemplo)
    }

    @Test
    fun ejecutarSinEntradas() {
        val ejemplo = Practica(
            "Prueba2",
            1,
            "",
            "src/test/resources/vacia.cpp",
            Paths.get("src/test/resources/2")
        )
        val gcc = Compilador("g++", "Gnu Compiler", null)
        ejemplo.generarCarpetaTrab()
        ejemplo.compilar(gcc)
        ejemplo.crearEntradas()
        ejemplo.ejecutar()
        this.limpiar(ejemplo)
    }

    //Test
    //un ejecutarConEntradas() {
    //   val ejemplo = Practica("Prueba1", 0, "", "src/test/resources/prueba.cpp", Paths.get("src/test/resources/1"))
    //   val gcc = Compilador("g++", "Gnu Compiler", null)
    //   ejemplo.generarCarpetaTrab()
    //   ejemplo.compilar(gcc)
    //   ejemplo.crearEntradas()
    //   ejemplo.ejecutar()
    //

    @Test
    fun crearCapturas() {
        val ejemplo = Practica(
            "Prueba2",
            1,
            "",
            "src/test/resources/vacia.cpp",
            Paths.get("src/test/resources/2")
        )
        val gcc = Compilador("g++", "Gnu Compiler", null)
        ejemplo.generarCarpetaTrab()
        ejemplo.compilar(gcc)
        ejemplo.crearEntradas()
        ejemplo.ejecutar()
        val silicon = Capturador("silicon", "silicon", null)
        ejemplo.crearCapturas(silicon)
        assert(File("src/test/resources/2/capturas").exists())
        assert(File("src/test/resources/2/capturas/codigos").exists())
        assert(File("src/test/resources/2/capturas/salidas").exists())
        assert(File("src/test/resources/2/capturas/codigos/vacia.png").exists())
        assert(File("src/test/resources/2/capturas/salidas/vacia.png").exists())
        this.limpiar(ejemplo)
    }

    @Test
    fun crearCapturasOpciones() {
        val ejemplo = Practica("Prueba2", 1, "", "src/test/resources/vacia.cpp", Paths.get("src/test/resources/2"))
        val gcc = Compilador("g++", "Gnu Compiler", null)
        ejemplo.generarCarpetaTrab()
        ejemplo.compilar(gcc)
        ejemplo.crearEntradas()
        ejemplo.ejecutar()
        val silicon = Capturador("silicon", "silicon", null)
        silicon.cambiarOpciones(listOf("--background", "#16f2d5"))
        ejemplo.crearCapturas(silicon)
        assert(File("src/test/resources/2/capturas").exists())
        assert(File("src/test/resources/2/capturas/codigos").exists())
        assert(File("src/test/resources/2/capturas/salidas").exists())
        assert(File("src/test/resources/2/capturas/codigos/vacia.png").exists())
        assert(File("src/test/resources/2/capturas/salidas/vacia.png").exists())

        var capturaCodigo = Files.readAllBytes(Paths.get("src/test/resources/2/capturas/codigos/vacia.png"))
        var hash = MessageDigest.getInstance("MD5").digest(capturaCodigo)
        var suma = BigInteger(1, hash).toString(16)
        assertEquals("f70ea09d2ef1d194f114c46193403159", suma)
        capturaCodigo = Files.readAllBytes(Paths.get("src/test/resources/2/capturas/salidas/vacia.png"))
        hash = MessageDigest.getInstance("MD5").digest(capturaCodigo)
        suma = BigInteger(1, hash).toString(16)
        assertEquals("bb3a5b13baf2efe3200b74c5575fdd01", suma)
        this.limpiar(ejemplo)
    }

//    @Test
//    fun ejecutarYModificarEntrada() {
//        val ejemplo = Practica("Prueba2", 1, "", "src/test/resources/prueba.cpp", Paths.get("src/test/resources/2"))
//        val gcc = Compilador("g++", "Gnu Compiler", null)
//        ejemplo.generarCarpetaTrab()
//        ejemplo.compilar(gcc)
//        ejemplo.crearEntradas()
//        ejemplo.ejecutar()
//        val editor = if (obtenerOS() == SistemaOperativo.WINDOWS) EditoresTexto.NOTEPAD else EditoresTexto.LEAFPAD
//        ejemplo.modSalida(editor.editor)
//        val silicon = Capturador("silicon", "silicon", null)
//        ejemplo.crearCapturas(silicon)
//    }

    @Test
    fun existeCodigoFuenteTest() {
        val practicas = listOf(
            Practica(
                nombre = "Hola mundo",
                id = 1,
                observacion = "",
                codigo = Paths.get("src/test/resources/ejemplosCodigo", "hola_mundo.cpp").toString(),
                rutaAbsoluta = null
            ),
            Practica(
                nombre = "Matrices",
                id = 2,
                observacion = "",
                codigo = Paths.get("src/test/resources/ejemplosCodigo", "matricez.cpp").toString(),
                rutaAbsoluta = null
            )
        )
        assert(practicas[0].existeCodigoFuente())
        assert(!practicas[1].existeCodigoFuente())
    }
}
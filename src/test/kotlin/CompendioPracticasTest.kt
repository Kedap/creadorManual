import org.isc4151.dan.creadorManual.CompendioPracticas
import org.isc4151.dan.creadorManual.Practica
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.nio.file.Paths

class CompendioPracticasTest {

    @Test
    fun obtenerTitulo() {
        val practicas = listOf(
            Practica("Numeros pares", 1, "", "", null),
            Practica("Numeros impares", 2, "", "", null)
        )
        val nuevoCompendio = CompendioPracticas(1, practicas, 2, Paths.get("src/test/resources/"))
        assertEquals(nuevoCompendio.obtenerTitulo(), "Numeros pares y numeros impares")
    }

    @Test
    fun obtenerTituloCompuesto() {
        val practicas = listOf(
            Practica("Numeros pares", 1, "Switch", "", null),
            Practica("Numeros impares", 2, "If-else", "", null)
        )
        val nuevoCompendio = CompendioPracticas(1, practicas, 2, Paths.get("src/test/resources/"))
        assertEquals(nuevoCompendio.obtenerTitulo(), "Numeros pares (Switch) y numeros impares (If-else)")
    }

    @Test
    fun obtenerTituloConAnexos() {
        val practicas = listOf(
            Practica("Numeros pares", 1, "", "", null),
            Practica("Igualdad entre numeros", 2, "", "", null)
        )
        val nuevoCompendio = CompendioPracticas(1, practicas, 2, Paths.get("src/test/resources/"))
        assertEquals(nuevoCompendio.obtenerTitulo(), "Numeros pares e igualdad entre numeros")
    }

    @Test
    fun obtenerTituloConAnexosAlFinal() {
        val practicas = listOf(
            Practica("Comparaciones entre si", 1, "", "", null),
            Practica("Matrices", 2, "", "", null)
        )
        val nuevoCompendio = CompendioPracticas(1, practicas, 2, Paths.get("src/test/resources/"))
        assertEquals(nuevoCompendio.obtenerTitulo(), "Comparaciones entre si y matrices")
    }

    @Test
    fun obtenerTituloConTres() {
        val practicas = listOf(
            Practica("Comparaciones entre si", 1, "", "", null),
            Practica("Matrices", 2, "Arreglos", "", null),
            Practica("Condicionales", 3, "", "", null)
        )
        val nuevoCompendio = CompendioPracticas(1, practicas, 3, Paths.get("src/test/resources/"))
        assertEquals(nuevoCompendio.obtenerTitulo(), "Comparaciones entre si, matrices (Arreglos) y condicionales")
    }

    @Test
    fun obtenerRutaAbsoluta() {
        val practicas = listOf(
            Practica("Comparaciones entre si", 1, "", "", null),
            Practica("Matrices", 2, "", "", null)
        )
        val nuevoCompendio = CompendioPracticas(1, practicas, 2, Paths.get("src/test/resources/"))
        assertEquals(nuevoCompendio.obtenerRutaAbsoluta(), "src/test/resources/1")
        val otroCompendio = CompendioPracticas(10, practicas, 2, Paths.get("src/test/resources/"))
        assertEquals(otroCompendio.obtenerRutaAbsoluta(), "src/test/resources/10")
    }

    @Test
    fun cambiarRutaPracticas() {
        val practicas = listOf(
            Practica("Prueba1", 0, "", "prueba.cpp", Paths.get("src/test/resources/")),
            Practica("Prueba2", 1, "", "vacia.cpp", Paths.get("src/test/resources/"))
        )
        val miCompendio = CompendioPracticas(1, practicas, 2, Paths.get("src/test/resources/"))
        for (practica in miCompendio.listaPracticas) {
            assertEquals(practica.rutaAbsoluta!!, Paths.get("src/test/resources/1"))
        }
    }

    @Test
    fun utilizarFormatoGPT() {
        val practicas = listOf(
            Practica("Prueba1", 0, "", "prueba.cpp", Paths.get("src/test/resources/")),
            Practica("Prueba2", 1, "", "vacia.cpp", Paths.get("src/test/resources/"))
        )
        val miCompendio = CompendioPracticas(1, practicas, 2, Paths.get("src/test/resources/"))
        miCompendio.crearNuevaEntradaGPT(Paths.get("src/test/resources/formato_gpt.txt"))
        assertEquals(File("src/test/resources/formato_esperado.txt").readText(), miCompendio.entradaGPT)
    }
}
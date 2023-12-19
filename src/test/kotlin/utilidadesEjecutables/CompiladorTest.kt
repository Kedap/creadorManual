package utilidadesEjecutables

import org.isc4151.dan.creadorManual.utilidadesEjecutables.Compilador
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CompiladorTest {

    @Test
    fun agregarArgumentos() {
        val gcc = Compilador("gcc", "Gnu C Compiler", listOf("-Wall"))
        gcc.agregarArgumentos("main.c -o ejecutable")
        assertEquals(listOf("-Wall"),gcc.obtenerListaArgumentos())
        assertEquals("gcc -Wall main.c -o ejecutable",gcc.obtenerComando())
    }
}
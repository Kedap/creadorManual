import org.isc4151.dan.creadorManual.ListaPracticas
import org.isc4151.dan.creadorManual.Practica
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.nio.file.Paths

class ListaPracticasTest {

    @Test
    fun obtenerNumRegistros() {
        val miLista = ListaPracticas("src/test/resources/test.xlsx")
        assertEquals(6, miLista.obtenerNumRegistros())
    }

    @Test
    fun aPracticas() {
        val miLista = ListaPracticas("src/test/resources/test.xlsx")
        val practicas = listOf(
            Practica("Hola mundo",1,"","src/test/resources/hola_mundo.cpp", Paths.get("src/test/resources/1")),
            Practica("Matrices",2,"","src/test/resources/matrices.cpp",Paths.get("src/test/resources/2")),
            Practica("Tabla del 9",3,"For","src/test/resources/tabla9.cpp",Paths.get("src/test/resources/3")),
            Practica("Tabla del 9",4,"While","src/test/resources/todas_tablas.cpp",Paths.get("src/test/resources/4")),
            Practica("Entrada",5,"","src/test/resources/entrada.cpp",Paths.get("src/test/resources/5")),
            Practica("Iteración",6,"For","src/test/resources/iteracion.cpp",Paths.get("src/test/resources/6")),
        )
        for (i in practicas.indices) {
            val esperado = practicas[i]
            val obtendio = miLista.aPracticas(Paths.get("src/test/resources/"))[i]
            assertEquals(esperado.nombre, obtendio.nombre)
            assertEquals(esperado.observacion, obtendio.observacion)
            assertEquals(esperado.codigo, obtendio.codigo)
            assertEquals(esperado.rutaAbsoluta, obtendio.rutaAbsoluta)

        }
    }

    @Test
    fun practicasACompendios1() {
        val miLista = ListaPracticas("src/test/resources/test.xlsx")
        val practicas = miLista.aPracticas(Paths.get("src/test/resources/"))
        val compendios = miLista.practicasACompendios(practicas,1)
        val practicasEsperadas = listOf(
            Practica("Hola mundo",1,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Matrices",2,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Tabla del 9",3,"For","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Tabla del 9",4,"While","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Entrada",5,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Iteración",6,"For","no_existo.cpp",Paths.get("src/test/resources/")),
        )
        assertEquals(6, compendios.size)
        for (i in compendios.indices) {
            assertEquals(i, compendios[i].numeroCompendio)
            assertEquals(1, compendios[i].listaPracticas.size)
            assertEquals(practicasEsperadas[i].nombre, compendios[i].listaPracticas.first().nombre)
        }
    }

    @Test
    fun practicasACompendios2() {
        val miLista = ListaPracticas("src/test/resources/test.xlsx")
        val practicas = miLista.aPracticas(Paths.get("src/test/resources/"))
        val compendios = miLista.practicasACompendios(practicas,2)
        val practicasEsperadas = listOf(
            Practica("Hola mundo",1,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Matrices",2,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Tabla del 9",3,"For","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Tabla del 9",4,"While","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Entrada",5,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Iteración",6,"For","no_existo.cpp",Paths.get("src/test/resources/")),
        )
        assertEquals(3, compendios.size)
        for (i in compendios.indices) {
            assertEquals(i, compendios[i].numeroCompendio)
            assertEquals(2, compendios[i].listaPracticas.size)
        }
        assertEquals(practicasEsperadas[0].nombre, compendios[0].listaPracticas[0].nombre)
        assertEquals(practicasEsperadas[1].nombre, compendios[0].listaPracticas[1].nombre)
        assertEquals(practicasEsperadas[2].nombre, compendios[1].listaPracticas[0].nombre)
        assertEquals(practicasEsperadas[3].nombre, compendios[1].listaPracticas[1].nombre)
        assertEquals(practicasEsperadas[4].nombre, compendios[2].listaPracticas[0].nombre)
        assertEquals(practicasEsperadas[5].nombre, compendios[2].listaPracticas[1].nombre)
    }

    @Test
    fun practicasACompendios3() {
        val miLista = ListaPracticas("src/test/resources/test.xlsx")
        val practicas = miLista.aPracticas(Paths.get("src/test/resources/"))
        val compendios = miLista.practicasACompendios(practicas,3)
        val practicasEsperadas = listOf(
            Practica("Hola mundo",1,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Matrices",2,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Tabla del 9",3,"For","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Tabla del 9",4,"While","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Entrada",5,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Iteración",6,"For","no_existo.cpp",Paths.get("src/test/resources/")),
        )
        assertEquals(2, compendios.size)
        for (i in compendios.indices) {
            assertEquals(i, compendios[i].numeroCompendio)
            assertEquals(3, compendios[i].listaPracticas.size)
        }
        assertEquals(practicasEsperadas[0].nombre, compendios[0].listaPracticas[0].nombre)
        assertEquals(practicasEsperadas[1].nombre, compendios[0].listaPracticas[1].nombre)
        assertEquals(practicasEsperadas[2].nombre, compendios[0].listaPracticas[2].nombre)
        assertEquals(practicasEsperadas[3].nombre, compendios[1].listaPracticas[0].nombre)
        assertEquals(practicasEsperadas[4].nombre, compendios[1].listaPracticas[1].nombre)
        assertEquals(practicasEsperadas[5].nombre, compendios[1].listaPracticas[2].nombre)
    }

    @Test
    fun practicasACompendios5() {
        val miLista = ListaPracticas("src/test/resources/test.xlsx")
        val practicas = miLista.aPracticas(Paths.get("src/test/resources/"))
        val compendios = miLista.practicasACompendios(practicas,5)
        val practicasEsperadas = listOf(
            Practica("Hola mundo",1,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Matrices",2,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Tabla del 9",3,"For","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Tabla del 9",4,"While","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Entrada",5,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Iteración",6,"For","no_existo.cpp",Paths.get("src/test/resources/")),
        )
        assertEquals(2, compendios.size)
        for (i in compendios.indices) {
            assertEquals(i, compendios[i].numeroCompendio)
            if (i == 1) {
                assertEquals(1, compendios[i].listaPracticas.size)
            } else {
                assertEquals(5, compendios[i].listaPracticas.size)
            }
        }
        assertEquals(practicasEsperadas[0].nombre, compendios[0].listaPracticas[0].nombre)
        assertEquals(practicasEsperadas[1].nombre, compendios[0].listaPracticas[1].nombre)
        assertEquals(practicasEsperadas[2].nombre, compendios[0].listaPracticas[2].nombre)
        assertEquals(practicasEsperadas[3].nombre, compendios[0].listaPracticas[3].nombre)
        assertEquals(practicasEsperadas[4].nombre, compendios[0].listaPracticas[4].nombre)
        assertEquals(practicasEsperadas[5].nombre, compendios[1].listaPracticas[0].nombre)
    }

    @Test
    fun practicasACompendios4() {
        val miLista = ListaPracticas("src/test/resources/test.xlsx")
        val practicas = miLista.aPracticas(Paths.get("src/test/resources/"))
        val compendios = miLista.practicasACompendios(practicas,4)
        val practicasEsperadas = listOf(
            Practica("Hola mundo",1,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Matrices",2,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Tabla del 9",3,"For","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Tabla del 9",4,"While","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Entrada",5,"","no_existo.cpp",Paths.get("src/test/resources/")),
            Practica("Iteración",6,"For","no_existo.cpp",Paths.get("src/test/resources/")),
        )
        assertEquals(2, compendios.size)
        for (i in compendios.indices) {
            assertEquals(i, compendios[i].numeroCompendio)
            if (i == 1) {
                assertEquals(2, compendios[i].listaPracticas.size)
            } else {
                assertEquals(4, compendios[i].listaPracticas.size)
            }
        }
        assertEquals(practicasEsperadas[0].nombre, compendios[0].listaPracticas[0].nombre)
        assertEquals(practicasEsperadas[1].nombre, compendios[0].listaPracticas[1].nombre)
        assertEquals(practicasEsperadas[2].nombre, compendios[0].listaPracticas[2].nombre)
        assertEquals(practicasEsperadas[3].nombre, compendios[0].listaPracticas[3].nombre)
        assertEquals(practicasEsperadas[4].nombre, compendios[1].listaPracticas[0].nombre)
        assertEquals(practicasEsperadas[5].nombre, compendios[1].listaPracticas[1].nombre)
    }
}
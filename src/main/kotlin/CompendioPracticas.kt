package org.isc4151.dan.creadorManual

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class CompendioPracticas(
    val numeroCompendio: Int,
    private val practicas: List<Practica>,
    private val practicasPorCompendio: Int,
    private val rutaTrabajo: Path
) {
    private val rutaAbsoluta = Paths.get(rutaTrabajo.toString(),numeroCompendio.toString())
    var listaPracticas = this.cambiarRutaPracticas()
    var titulo: String? = this.crearTitulo()
    var entradaGPT: String? = crearEntradaGPT()

    private fun crearTitulo(): String {
        var nuevoTitulo = ""
        for (practica in practicas) {
            var obseravacion = ""
            if (practica.observacion != "") obseravacion=" (${practica.observacion})"

            if (nuevoTitulo.isEmpty()) {
                nuevoTitulo = practica.nombre + obseravacion
            } else if (practica === practicas.last()){
                val primeraLetraSiguientePrac = practica.nombre.lowercase().first()
                var anexo = " y "
                if (primeraLetraSiguientePrac == 'y' || primeraLetraSiguientePrac == 'i') anexo = " e "
                nuevoTitulo += anexo + practica.nombre.lowercase() + obseravacion
            } else {
                nuevoTitulo += ", ${practica.nombre.lowercase()}$obseravacion"
            }
        }
        return nuevoTitulo
    }

    private fun cambiarRutaPracticas(): MutableList<Practica> {
        val nuevasPracticas = mutableListOf<Practica>()
        for (practica in this.practicas) {
            practica.cambiarRutaAbsoluta(Paths.get(this.obtenerRutaAbsoluta()))
            nuevasPracticas.add(practica)
        }
        return nuevasPracticas
    }

    private fun crearEntradaGPT(): String {
        if (titulo == null) throw Exception("No se ha creado ningún titulo como para crear el prompt")
        return """
            Eres un estudiante de universidad y tienes que escribir una breve conclusion
            para un manual fundamentos de programación en donde se lista(n) $practicasPorCompendio practica(s) en
            c++, utilizando este lenguaje para dichas practicas tienen como nombre '$titulo'.
            
            Ejemplo cuando es las practicas son 'Sucesión de fibonacci y tablas de multiplicar (for)':
            ```
            En estas dos practicas me resulto interesante como se puede utilizar algunas
            palabras reservadas de C++ para hacer cosas interesantes, tanto como para
            tener el numero de fibonacci que se tiene tanto como para mostrar en pantalla
            todas las tablas de multiplicar. Es realmente util, más a la hora de conocer
            una estructura como lo puede llegar a ser el ciclo For.
            ```
            
            Ahora escribe tu la conclusión para $practicasPorCompendio practica(s) que tiene(n) como titulo '$titulo':
        """.trimIndent()
    }

    fun crearNuevaEntradaGPT(rutaFormato: Path) {
        var contenidoArchivo = File(rutaFormato.toString()).readText()
        val busquedaPracticas = Regex("#n(um(ero)?)?practicas#", RegexOption.IGNORE_CASE)
        contenidoArchivo = contenidoArchivo.replace(busquedaPracticas, practicasPorCompendio.toString())
        val busquedaTitulo = Regex("#titulo#", RegexOption.IGNORE_CASE)
        contenidoArchivo = contenidoArchivo.replace(busquedaTitulo, titulo.toString())
        this.entradaGPT = contenidoArchivo
    }

    fun obtenerTitulo(): String? {
        return this.titulo
    }

    fun obtenerRutaAbsoluta(): String {
        return this.rutaAbsoluta.toString()
    }
}
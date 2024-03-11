package org.isc4151.dan.creadorManual.lenguajes

import org.isc4151.dan.creadorManual.cli.EntradaUsuario
import org.isc4151.dan.creadorManual.cli.EntradaUsuarioOmision

class FabricaJava : FabricaLenguaje() {
    override var rutaCompilador: String = "javac"
    override var opciones: List<String> = listOf()
    var rutaJavaEjecutador: String = "java"
    var opcionesEjecucion: List<String> = listOf()

    override fun configurarLenguaje() {
        val preguntarRutaCompilador = EntradaUsuario("Ingresa la ruta del binario para el compilador")
        preguntarRutaCompilador.mostrar()
        rutaCompilador = preguntarRutaCompilador.obtenerEntrada()

        val preguntarOpciones = EntradaUsuarioOmision("Ingresa la opciones que quieres para el compilador", "")
        preguntarOpciones.mostrar()
        opciones = preguntarOpciones.obtenerEntrada().split(' ')

        val preguntarRutaEjecutador = EntradaUsuario("Ingresa la ruta del binario para el ejecutador")
        preguntarRutaEjecutador.mostrar()
        rutaJavaEjecutador = preguntarRutaEjecutador.obtenerEntrada()

        val preguntarOpcionesEjecucion = EntradaUsuarioOmision("Ingresa la opciones que quieres para el ejecutador", "")
        preguntarOpcionesEjecucion.mostrar()
        opcionesEjecucion = preguntarOpcionesEjecucion.obtenerEntrada().split(' ')
    }

    override fun obtenerLenguaje(): Lenguaje {
        return LenguajeJava(rutaCompilador, opciones, rutaJavaEjecutador, opcionesEjecucion)
    }
}
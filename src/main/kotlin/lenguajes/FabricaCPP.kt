package org.isc4151.dan.creadorManual.lenguajes

import org.isc4151.dan.creadorManual.cli.EntradaUsuario
import org.isc4151.dan.creadorManual.cli.EntradaUsuarioOmision

class FabricaCPP : FabricaLenguaje() {
    override var rutaCompilador: String = "g++"
    override var opciones: List<String> = listOf()

    override fun configurarLenguaje() {
        val preguntarRutaCompilador = EntradaUsuario("Ingresa la ruta del binario para el compilador")
        preguntarRutaCompilador.mostrar()
        rutaCompilador = preguntarRutaCompilador.obtenerEntrada()

        val preguntarOpciones = EntradaUsuarioOmision("Ingresa la opciones que quieres para el compilador", "")
        preguntarOpciones.mostrar()
        opciones = preguntarOpciones.obtenerEntrada().split(' ')
    }

    override fun obtenerLenguaje(): Lenguaje {
        return LenguajeCPP(rutaCompilador, opciones)
    }
}
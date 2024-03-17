package org.isc4151.dan.creadorManual.lenguajes

abstract class FabricaLenguaje {
    abstract var rutaCompilador: String
    abstract var opciones: List<String>

    abstract fun configurarLenguaje()
    abstract fun obtenerLenguaje(): Lenguaje
}
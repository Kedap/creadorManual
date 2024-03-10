package org.isc4151.dan.creadorManual.lenguajes

abstract class Lenguaje(protected var rutaCompilador: String, protected var opciones: List<String>) {
    abstract fun compilar(codigo: String, salida: String)
    abstract fun obtenerEjecucion(salida: String)
}
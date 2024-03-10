package org.isc4151.dan.creadorManual.lenguajes

abstract class Lenguaje(protected val rutaCompilador: String, protected val opciones: List<String>) {
    abstract fun compilar(codigo: String, salida: String): String
    abstract fun obtenerEjecucion(salida: String): String
}
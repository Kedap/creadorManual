package org.isc4151.dan.creadorManual.lenguajes

class LenguajeJava(
    rutaCompilador: String,
    opciones: List<String>,
    private val rutaJavaEjecutador: String,
    private val opcionesEjecucion: List<String>
) : Lenguaje(rutaCompilador, opciones) {
    override fun compilar(codigo: String, salida: String) {
        TODO("Not yet implemented")
    }

    override fun obtenerEjecucion(salida: String): String {
        TODO("Not yet implemented")
    }
}
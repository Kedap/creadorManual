package org.isc4151.dan.creadorManual.lenguajes

class LenguajeJava(
    rutaCompilador: String,
    opciones: List<String>,
    private val rutaJavaCompilador: String,
    private val opcionesLenguaje: List<String>
) : Lenguaje(rutaCompilador, opciones) {
    override fun compilar(codigo: String, salida: String) {
        TODO("Not yet implemented")
    }

    override fun obtener(salida: String) {
        TODO("Not yet implemented")
    }
}
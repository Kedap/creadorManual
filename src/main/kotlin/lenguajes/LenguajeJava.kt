package org.isc4151.dan.creadorManual.lenguajes

import java.util.concurrent.TimeUnit

class LenguajeJava(
    rutaCompilador: String,
    opciones: List<String>,
    private val rutaJavaEjecutador: String,
    private val opcionesEjecucion: List<String>
) : Lenguaje(rutaCompilador, opciones) {
    override fun compilar(codigo: String, salida: String): String {
        var comando = rutaCompilador
        opciones.forEach { comando+= "$ $it" }
        comando += " $codigo -d $salida"
        println("$ $comando")
        val procesoHijo = Runtime.getRuntime().exec(comando)
        procesoHijo.waitFor(20, TimeUnit.SECONDS)
        if (procesoHijo.exitValue() != 0) throw Exception("Ocurrio un error al compilar")
        return salida
    }

    override fun obtenerEjecucion(salida: String): String {
        var comando = rutaJavaEjecutador
        opcionesEjecucion.forEach { comando += " $it" }
        comando += salida
        return comando
    }
}
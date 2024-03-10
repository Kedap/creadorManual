package org.isc4151.dan.creadorManual.lenguajes

import org.isc4151.dan.creadorManual.SistemaOperativo
import org.isc4151.dan.creadorManual.obtenerOS
import java.util.concurrent.TimeUnit

class LenguajeCPP(rutaCompilador: String, opciones: List<String>) : Lenguaje(rutaCompilador, opciones) {
    override fun compilar(codigo: String, salida: String) {
        var comando = rutaCompilador
        opciones.forEach { comando += " $it" }
        val archivoSalida = if (obtenerOS() == SistemaOperativo.WINDOWS) "$salida.exe" else salida
        comando += " $codigo -o $archivoSalida"
        println("$ $comando")
        val procesoHijo = Runtime.getRuntime().exec(comando)
        procesoHijo.waitFor(20, TimeUnit.SECONDS)
        if (procesoHijo.exitValue() != 0) throw Exception("Ocurrio un error al compilar")
    }

    override fun obtenerEjecucion(salida: String): String {
        return salida
    }
}
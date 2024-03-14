package org.isc4151.dan.creadorManual.lenguajes

import org.apache.commons.io.FilenameUtils
import org.isc4151.dan.creadorManual.SistemaOperativo
import org.isc4151.dan.creadorManual.obtenerOS
import java.util.concurrent.TimeUnit

class LenguajeCPP(rutaCompilador: String, opciones: List<String>) :
    Lenguaje(rutaCompilador, opciones, Regex("cout"), Regex("cin")) {
    override fun compilar(codigo: String, directorioSalida: String): String {
        var comando = rutaCompilador
        opciones.forEach { comando += " $it" }
        var archivoSalida = FilenameUtils.getBaseName(codigo)
        archivoSalida = "$directorioSalida/$archivoSalida"
        if (obtenerOS() == SistemaOperativo.WINDOWS) archivoSalida += ".exe"
        comando += " $codigo -o $archivoSalida"
        println("$ $comando")
        val procesoHijo = Runtime.getRuntime().exec(comando)
        procesoHijo.waitFor(20, TimeUnit.SECONDS)
        if (procesoHijo.exitValue() != 0) throw Exception("Ocurrio un error al compilar")
        return archivoSalida
    }

    override fun obtenerEjecucion(salida: String): List<String> {
        return listOf(salida)
    }
}
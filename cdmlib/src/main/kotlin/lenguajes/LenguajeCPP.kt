package org.isc4x51.dan.creadorManual.Lenguajes

import org.apache.commons.io.FilenameUtils
import org.isc4x51.dan.creadorManual.SistemaOperativo
import org.isc4x51.dan.creadorManual.obtenerOS
import java.nio.file.Path
import java.util.concurrent.TimeUnit

/**
 * Lenguaje C++ para compilar y ejecutar aplicaciones
 * @property rutaCompilador Nombre o ruta del compilador de C++
 * @property opciones Banderas o argumentos para el compilador de C++
 */
class LenguajeCPP(rutaCompilador: String, opciones: List<String>) : Lenguaje(
    rutaCompilador, opciones,
    Regex("cout"), Regex("cin")
) {
    override fun compilar(codigo: Path, directorioSalida: Path): Path {
        var comando = rutaCompilador
        opciones.forEach { comando += " $it" }
        val archivoSalida = directorioSalida.resolve(FilenameUtils.getBaseName(codigo.toString()))
        if (obtenerOS() == SistemaOperativo.WINDOWS) archivoSalida.resolveSibling("${archivoSalida.fileName}.exe")
        comando += " $codigo -o $archivoSalida"
        val procesoHijo = Runtime.getRuntime().exec(comando)
        procesoHijo.waitFor(20, TimeUnit.SECONDS)
        if (procesoHijo.exitValue() != 0) throw Exception("Ocurrió un error al compilar $codigo con el comando $$comando")
        return archivoSalida
    }

    override fun obtenerEjecucion(productoCompilacion: Path): List<String> {
        return listOf(productoCompilacion.toString())
    }
}
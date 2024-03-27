package org.isc4x51.dan.creadorManual.lenguajes

import org.apache.commons.io.FilenameUtils
import org.isc4x51.dan.creadorManual.Lenguajes.Lenguaje
import org.isc4x51.dan.creadorManual.SistemaOperativo
import org.isc4x51.dan.creadorManual.obtenerOS
import java.nio.file.Path
import java.util.concurrent.TimeUnit

/**
 * Soporte para el lenguaje Java
 * @property rutaJavaEjecutador Nombre del comando o ruta del interprete de `java`
 * @property opcionesEjecucion Argumentos o banderas para el interprete de Java
 * @property rutaCompilador Nombre del comando o ruta del compilador Java
 * @property opciones Argumentos o banderas para el compilador de Java
 */
class LenguajeJava(
    rutaCompilador: String,
    opciones: List<String>,
    private val rutaJavaEjecutador: String,
    private val opcionesEjecucion: List<String>
) : Lenguaje(rutaCompilador, opciones, Regex("System.out"), Regex("""(?:readLine|nextInt|next|nextLine)\(\)""")) {
    override fun compilar(codigo: Path, directorioSalida: Path): Path {
        var comando = rutaCompilador
        opciones.forEach { comando += " $it" }
        comando += " $codigo -d $directorioSalida"
        val procesoHijo = Runtime.getRuntime().exec(comando)
        procesoHijo.waitFor(20, TimeUnit.SECONDS)
        if (procesoHijo.exitValue() != 0) throw Exception("Ocurrió un error al intentar compilar $codigo con $comando")
        val resultado = directorioSalida.resolve("${FilenameUtils.getBaseName(codigo.toString())}.class")
        if (!resultado.toFile()
                .exists()
        ) throw Exception("$resultado no se encuentra, el resultado de compilar $codigo ($comando)")
        return resultado
    }

    override fun obtenerEjecucion(productoCompilacion: Path): List<String> {
        val rutaCarpeta = productoCompilacion.parent
        val comando = mutableListOf(rutaJavaEjecutador)
        comando.add(
            if (obtenerOS() == SistemaOperativo.WINDOWS) {
                "-classpath"
            } else {
                "-cp"
            }
        )
        comando.add(rutaCarpeta.toString())
        comando.plus(opcionesEjecucion)
        comando.add(FilenameUtils.getBaseName(productoCompilacion.toString()))
        return comando
    }
}
package org.isc4151.dan.creadorManual.lenguajes

import org.apache.commons.io.FilenameUtils
import org.isc4151.dan.creadorManual.SistemaOperativo
import org.isc4151.dan.creadorManual.obtenerOS
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.io.path.Path

class LenguajeJava(
    rutaCompilador: String,
    opciones: List<String>,
    private val rutaJavaEjecutador: String,
    private val opcionesEjecucion: List<String>
) : Lenguaje(rutaCompilador, opciones, Regex("System.out"), Regex("""(?:readLine|nextInt|next|nextLine)\(\)""")) {
    override fun compilar(codigo: String, directorioSalida: String): String {
        var comando = rutaCompilador
        opciones.forEach { comando += " $it" }
        comando += " $codigo -d $directorioSalida"
        println("$ $comando")
        val procesoHijo = Runtime.getRuntime().exec(comando)
        procesoHijo.waitFor(20, TimeUnit.SECONDS)
        if (procesoHijo.exitValue() != 0) throw Exception("Ocurrio un error al compilar")
        val resultado = "$directorioSalida/${FilenameUtils.getBaseName(codigo)}.class"
        if (!File(resultado).exists()) throw Exception("$codigo no se compilo correctamente")
        return resultado
    }

    override fun obtenerEjecucion(salida: String): List<String> {
        val rutaCarpeta = Path(salida).parent
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
        comando.add(FilenameUtils.getBaseName(salida))
        return comando
    }
}
package org.isc4x51.dan.creadorManual.utilidadEjecutable

/**
 * Clase para crear un comando ejecutable
 * @property comando El nombre o ruta del programa
 * @property nombre Un nombre para identificar el programa
 * @property opciones Los argumentos o banderas del programa
 * @property argumentos Los argumentos temporales para una ejecución en especifico
 */
class UtilidadEjecutable(
    val comando: String,
    val nombre: String,
    val opciones: List<String> = listOf(),
    var argumentos: List<String> = listOf(),
) {

    /**
     * Agrega [argumentos] en forma de lista
     * @param _argumentos los argumentos en forma de lista (separados por espacio)
     * por agregar
     */
    fun agregarArgumentos(_argumentos: List<String>) {
        argumentos.plus(_argumentos)
    }

    /**
     * Borra los [argumentos] que son de manera temporal
     */
    fun borrarArgumentos() {
        argumentos = listOf()
    }

    /**
     * Obtiene una lista para ejecutar, compuesto de [opciones] y [argumentos]
     */
    fun obtenerComando(): List<String> {
        val cmd = mutableListOf(comando)
        cmd.plus(opciones)
        cmd.plus(argumentos)
        return cmd
    }
}
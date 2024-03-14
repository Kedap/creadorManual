package org.isc4151.dan.creadorManual.lenguajes

abstract class Lenguaje(protected val rutaCompilador: String, protected val opciones: List<String>) {
    /**
     * Se le pasa como argumento la ruta del código a compilar y se le pasa la salida el directorio en donde
     * se va a colocar el resultado final según el lenguaje, el resultado final se llamara igual que el archivo,
     * pero con la extensión según corresponda, es par ello que también se retorna
     *
     * @param codigo ruta del código fuente a compilar
     * @param directorioSalida directorio en donde se colocara el resultado de la compilación
     * @return se devuelve la ruta del resultado de la compilación
     */
    abstract fun compilar(codigo: String, directorioSalida: String): String
    abstract fun obtenerEjecucion(salida: String): String
}
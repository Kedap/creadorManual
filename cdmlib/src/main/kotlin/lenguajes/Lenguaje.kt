package org.isc4x51.dan.creadorManual.Lenguajes

import java.nio.file.Path

/**
 *  Clase abstracta de lenguaje pensado para ser la base en caso de agregar
 *  un nuevo lenguaje
 *
 * @constructor La clase esta pensada para que los lenguajes que deriven de ella ejecuten su
 * constructor, ya que los lenguajes podrán modificar el compilador y sus banderas, pero las
 * entradas seran fijas
 *  @property rutaCompilador Indica el comando o la ruta del compilador del lenguaje
 *  en el caso de ser interpretado este puede ser ""
 *  @property opciones Son los argumentos al momento de ejecutar el compilador, un ejemplo
 *  puede ser:
 *  `cpp = LenguajeCPP("g++", listOf("-Wall", "-g"))`
 *  @property codigoSalida Es una expresión regular que sirve para marcar las *salidas*
 *  de [org.isc4x51.dan.creadorManual.EntradaEjecutable] de un programa, un ejemplo de
 *  esto puede ser: `cpp = LenguajeCPP(null,null,Regex("cout"))`, cada vez que se encuentre
 *  *_cout_* en el código se mostrara como salida en el lenguaje que este heredando
 *  @property codigoEntrada Es una expresión regular que sirve para marcar las *entradas*
 *  del usuario al ejecutar el programa, un ejemplo de esto puede ser `python = LenguajePython(null.null.null,Regex("input")`
 */
abstract class Lenguaje(
    protected val rutaCompilador: String,
    protected val opciones: List<String>,
    private val codigoSalida: Regex,
    private val codigoEntrada: Regex
) {
    /**
     * Compila un código dado y devuelve la ruta del producto de la compilación
     * @param codigo Ruta del código fuente a compilar
     * @param directorioSalida Ruta de la carpeta en donde dentro de ella se colocara el
     * producto de la compilación
     * @return Ruta del producto de la compilación
     */
    abstract fun compilar(codigo: Path, directorioSalida: Path): Path

    /**
     * Obtiene los comandos para ejecutar el programa
     * @param productoCompilacion Ruta del producto de una compilación
     * @return Comandos para ejecutar el programa separados por argumentos y banderas
     */
    abstract fun obtenerEjecucion(productoCompilacion: Path): List<String>
    fun getCodigoSalida(): Regex {
        return codigoSalida
    }

    fun getCodigoEntrada(): Regex {
        return codigoEntrada
    }
}
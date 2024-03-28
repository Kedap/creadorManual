package org.isc4x51.dan.creadorManual

import org.apache.commons.io.FilenameUtils
import org.isc4x51.dan.creadorManual.Lenguajes.Lenguaje
import org.isc4x51.dan.creadorManual.utilidadEjecutable.EditoresTexto
import org.isc4x51.dan.creadorManual.utilidadEjecutable.UtilidadEjecutable
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import kotlin.io.path.absolute
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.moveTo

/**
 * Se encarga de gestionar cada practica por separado, según se encuentra en la lista de xlsx
 * @constructor Son todos los campos de se encuentran en la lista xlsx, ademas de [rutaAbsoluta]
 * y [lenguaje]
 * @property id Es el id que viene en la plantilla xlsx
 * @property nombre Es el nombre que viene en la plantilla xlsx
 * @property observaciones Son las observaciones que viene en la plantilla xlsx
 * @property codigo Es el codigo que viene en la plantilla xlsx
 * @property carpetaCodigos Es la carpeta en donde se encuentran todos los códigos fuentes, es ahí en donde
 * va buscar el código para posteriormente moverlo * a su [rutaAbsoluta], puede decirse que es la ruta `code`
 * @property rutaAbsoluta Es la ruta en donde se espera que sea su carpeta de trabajo de esa practica
 * @property lenguaje Es el lenguaje que se va a trabajar para [compilar] y [ejecutar] la practica
 * @property rutaProducto Es la ruta del producto de compilación al [compilar]
 * @property rutaCodigo Es la ruta del código fuente de la practica, se diferencia de
 * [codigo] ya que [codigo] es solo el String y [rutaCodigo] es [codigo] adjuntado con la [rutaAbsoluta]
 * @property entradas Entradas y salidas del usuario utilizando [org.isc4x51.dan.creadorManual.EntradaEjecutable]
 * @property rutaSalidaEjecucion Ruta del archivo *txt* en donde se encuentra la ejecución del mismo
 * programa
 * @property capturaCodigo Ruta de la captura del [rutaCodigo] utilizando ya sea Silicon o Germanium
 * @property capturaSalida Ruta de la captura del [rutaSalidaEjecucion] utilizando ya sea Silicon o Germanium
 */
class Practica(
    private val id: Int,
    val nombre: String,
    private val observaciones: String,
    private val codigo: String,
    val carpetaCodigos: Path,
    val rutaAbsoluta: Path,
    private val lenguaje: Lenguaje
) {
    private var rutaProducto: Path? = null
    private var rutaCodigo: Path? = null
    private var entradas: List<EntradaEjecutable> = listOf()
    private var rutaSalidaEjecucion: Path? = null
    private var capturaCodigo: Path? = null
    private var capturaSalida: Path? = null

    /**
     * Compila la practica, es necesario tener [rutaCodigo] definida, sin o mandara una exception
     */
    fun compilar() {
        if (rutaCodigo == null) throw Exception("No se ha movido el código ($codigo) a la carpeta de trabajo ($rutaAbsoluta)")
        val carpetaBinarios = rutaAbsoluta.resolve("binarios")
        if (!carpetaBinarios.isDirectory()) carpetaBinarios.toFile().mkdirs()
        rutaProducto = lenguaje.compilar(rutaCodigo!!, carpetaBinarios)
    }

    /**
     * genera las [entradas], es necesario tener [rutaCodigo] definido si no mandara una exception
     */
    fun generarEntradas() {
        if (rutaCodigo == null) throw Exception("No se ha movido el código ($codigo) a la carpeta de trabajo ($rutaAbsoluta)")
        val entradas = mutableListOf<EntradaEjecutable>()
        rutaCodigo!!.toFile().forEachLine {
            if (it.contains(lenguaje.getCodigoEntrada())) {
                val nuevaEntrada = EntradaEjecutable(it, "")
                entradas.add(nuevaEntrada)
            } else if (it.contains(lenguaje.getCodigoSalida())) {
                if (entradas.isEmpty()) {
                    entradas.add(EntradaEjecutable("", it))
                } else {
                    val ultimaEntrada = entradas.last()
                    ultimaEntrada.entrada = it
                    entradas.removeLast()
                    entradas.add(ultimaEntrada)
                }
            }
        }
        if (entradas.isNotEmpty() && entradas.last().entrada!!.isEmpty()) entradas.last().entrada = null
        this.entradas = entradas
    }

    // TODO: terminar de plantear como es que se va a ejecutar
    fun ejecutar() {
        TODO()
    }

    /**
     * Modificar las entradas para que pueda coincidir con el problema dado
     * @param e El editor de texto que se va a utilizar para modificar la salida
     */
    fun modificarEntrada(e: EditoresTexto) {
        if (rutaSalidaEjecucion == null) throw Exception("No se puede modificar las salidas de ejecución si aún no se han creado")
        val cmd = e.editor.obtenerComando().plus(rutaSalidaEjecucion!!.toString())
        Runtime.getRuntime().exec(cmd.joinToString(" "))
    }

    /**
     * Crea las capturas para la practica, tanto la captura del código como la salida de ejecución
     * @param capturador Es el comando para el capturador según el usuario, este puede ser Silicon o Germanium
     */
    fun crearCapturas(capturador: UtilidadEjecutable) {
        if (rutaCodigo == null || rutaSalidaEjecucion == null) throw Exception("No se ha movido $codigo a su ruta absoluta o no se han generado las salidas de ejecución")
        val carpetaCapturas = rutaAbsoluta.resolve("capturas")
        if (!carpetaCapturas.isDirectory()) carpetaCapturas.toFile().mkdirs()

        capturar(capturador, carpetaCapturas.resolve("salidas"), true)
        capturar(capturador, carpetaCapturas.resolve("codigos"), false)
    }

    /**
     * Crea la captura según su tipo
     * @param capturador Es el capturador, ya sea Silicon o Germanium
     * @param carpetaCapturas Es la ruta en donde se colocaran las capturas
     * @param salida Si es verdadero, se asume que se esta tomando captura del la salida de ejecución y se modifica
     * [capturaSalida], en el caso contrario se asume que esta tomando captura al código, por lo que se modificara
     * [capturaCodigo]
     */
    private fun capturar(capturador: UtilidadEjecutable, carpetaCapturas: Path, salida: Boolean) {
        val banderasSiliconMarkdown = listOf("-l", "md")
        if (!carpetaCapturas.isDirectory()) carpetaCapturas.toFile().mkdirs()
        val capturaSalida = carpetaCapturas.resolve("${FilenameUtils.getBaseName(rutaCodigo!!.toString())}.png")
        capturador.agregarArgumentos(listOf(rutaSalidaEjecucion!!.toString(), capturaSalida.toString()))
        if (salida) capturador.agregarArgumentos(banderasSiliconMarkdown)
        val cmd = capturador.obtenerComando()

        val procesoHijo = Runtime.getRuntime().exec(cmd.joinToString(" "))
        procesoHijo.waitFor(20, TimeUnit.SECONDS)
        if (procesoHijo.exitValue() != 0) throw Exception("Ocurrió un error al ejecutar $cmd")
        if (salida) {
            this.capturaSalida = capturaSalida
        } else {
            this.capturaCodigo = capturaSalida
        }
        capturador.borrarArgumentos()
    }

    /**
     * Crea la carpeta de trabajo y mueve el código correspondiente a la carpeta de trabajo
     */
    fun generarCarpetaTrabajo() {
        if (rutaAbsoluta.isDirectory() && rutaCodigo != null) return
        rutaAbsoluta.toFile().mkdir()
        rutaCodigo = carpetaCodigos.resolve(codigo).moveTo(rutaAbsoluta.resolve(codigo))
    }

    /**
     * Obtener ambas rutas absolutas con las capturas tanto del código y de la ejecución, modificadas
     * levemente para que puedan usarse con ms-word (se le agregarón doble barra invertida \)
     * @return rutaCodigo, rutaEjecucion
     */
    fun getRutaCapturas(): Pair<String, String> {
        if (capturaSalida == null || capturaCodigo == null) throw Exception("Parece que no se han tomado las capturas")
        val rutaCodigo = capturaCodigo!!.absolute().toString().replace("\\", "\\\\")
        val rutaEjecucion = capturaSalida!!.absolute().toString().replace("\\", "\\\\")
        return Pair(rutaCodigo, rutaEjecucion)
    }

    /**
     * Verifica si existe [codigo] en la [carpetaCodigos]
     */
    fun extisteCodigoFuente(): Boolean {
        return carpetaCodigos.resolve(codigo).exists()
    }

    fun getRutaProducto(): Path? {
        return rutaProducto
    }

    fun getEntradas(): List<EntradaEjecutable> {
        return entradas
    }

    fun getRutaCodigo(): Path? {
        return rutaCodigo
    }
}
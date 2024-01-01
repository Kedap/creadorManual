package org.isc4151.dan.creadorManual

import org.apache.commons.io.FilenameUtils
import org.isc4151.dan.creadorManual.utilidadesEjecutables.Capturador
import org.isc4151.dan.creadorManual.utilidadesEjecutables.Compilador
import org.isc4151.dan.creadorManual.utilidadesEjecutables.EditorTexto
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import kotlin.io.path.absolute

class Practica(
    val nombre: String,
    val id: Int,
    val observacion: String,
    val codigo: String,
    var rutaAbsoluta: Path? = null
) {
    private var rutaBinario: String? = null
    private var rutaCodigo: Path? = null
    private var entradas: List<EntradaEjecutable>? = null
    private var rutaSalida: String? = null
    private var capturaCodigo: String? = null
    private var capturaSalida: String? = null

    fun compilar(c: Compilador) {
        if (this.rutaCodigo == null || !File(this.rutaAbsoluta.toString()).isDirectory) throw Exception("No se ha movido el codigo o no se han creado la carpeta de trabajo")
        val carpetaBinario = "${this.rutaAbsoluta}/binarios"
        if (!File(carpetaBinario).isDirectory) File(carpetaBinario).mkdirs()
        var rutaBinario = "$carpetaBinario/${FilenameUtils.getBaseName(this.rutaCodigo.toString())}"
        if (obtenerOS() == SistemaOperativo.WINDOWS) rutaBinario = "$rutaBinario.exe"
        val argumentos = "$rutaCodigo -o $rutaBinario"
        c.agregarArgumentos(argumentos)
        val comando = c.obtenerComando()
        println("Compilando $rutaCodigo...")
        println("$ $comando")

        val procesoHijo = Runtime.getRuntime().exec(comando)
        procesoHijo.waitFor(20, TimeUnit.SECONDS)
        if (procesoHijo.exitValue() != 0) throw Exception("Ocurrio un error al compilar $nombre")
        this.rutaBinario = rutaBinario
    }

    fun crearEntradas() {
        if (this.rutaCodigo == null) throw Exception("La ruta absoluta del codigo no exsite, no se ha compilado...")
        val entradas = mutableListOf<EntradaEjecutable>()
        File(this.rutaCodigo.toString()).forEachLine {
            if (it.contains("cout")) {
                val nuevaEntrada = EntradaEjecutable(it, "")
                entradas.add(nuevaEntrada)
            } else if (it.contains("cin")) {
                val ultimaEntrada = entradas.last()
                ultimaEntrada.entrada = it
                entradas.removeLast()
                entradas.add(ultimaEntrada)
            }
        }
        if (entradas.last().entrada!!.isEmpty()) entradas.last().entrada = null
        this.entradas = entradas
    }

    fun ejecutar() {
        if (this.rutaBinario == null || this.entradas == null) {
            throw Exception("El binario no a sido compilado o las entradas no se han preguntado en ${this.nombre}")
        }
        val carpetasSalidasTexto = "${this.rutaAbsoluta}/salidas_texto"
        if (!File(carpetasSalidasTexto).isDirectory) File(carpetasSalidasTexto).mkdirs()
        val archivoSalida = "$carpetasSalidasTexto/${FilenameUtils.getBaseName(rutaCodigo.toString())}_salida.txt"
        for (entrada in this.obtenerEntradas()!!) {
            var msgEntrada = "SIN ENTRADA"
            if (entrada.entrada != null) msgEntrada = entrada.entrada!!
            println("${entrada.mensaje}\n\n${msgEntrada}")
        }
        val procesoHijo = ProcessBuilder(this.rutaBinario)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .redirectInput(ProcessBuilder.Redirect.INHERIT)
            .start()
        procesoHijo.waitFor(15, TimeUnit.MINUTES)
        val salida = procesoHijo.inputStream.bufferedReader().readText()
        File(archivoSalida).writeText(salida)
        if (procesoHijo.exitValue() != 0) throw Exception("Ocurrio algún error al ejecutar ${this.rutaBinario}")
        println("-------------------------------------------------")
        this.rutaSalida = archivoSalida
    }

    fun modSalida(e: EditorTexto) {
        if (this.rutaSalida == null) throw Exception("No se puede modificar la salida si no se ha creado")
        val comando = "${e.comando} ${this.rutaSalida}"
        Runtime.getRuntime().exec(comando)
        println("Preciona ENTER cuando hayas dejado de modificar")
        readln()
    }

    private fun caputrarCodigo(c: Capturador) {
        val carpetaCapturasCodigo = "${this.rutaAbsoluta}/capturas/codigos"
        if (!File(carpetaCapturasCodigo).isDirectory) File(carpetaCapturasCodigo).mkdir()
        val rutaCaptura = "$carpetaCapturasCodigo/${FilenameUtils.getBaseName(this.rutaCodigo.toString())}.png"
        val argumentos = "$rutaCodigo -o $rutaCaptura"
        c.agregarArgumentos(argumentos)
        val comando = c.obtenerComando()
        println("Capturando el codigo $rutaCodigo...")
        println("$ $comando")

        val procesoHijo = Runtime.getRuntime().exec(comando)
        procesoHijo.waitFor(20, TimeUnit.SECONDS)
        if (procesoHijo.exitValue() != 0) throw Exception("Ocurrio un error al capturar el codigo de $nombre")
        this.capturaCodigo = rutaCaptura
    }

    private fun caputrarSalida(c: Capturador) {
        val carpetaCapturasSalida = "${this.rutaAbsoluta}/capturas/salidas"
        if (!File(carpetaCapturasSalida).isDirectory) File(carpetaCapturasSalida).mkdir()
        val rutaCaptura = "$carpetaCapturasSalida/${FilenameUtils.getBaseName(this.rutaCodigo.toString())}.png"
        val argumentos = "$rutaSalida -o $rutaCaptura"
        c.agregarArgumentos(argumentos)
        val comando = c.obtenerComando()
        println("Capturando la salida $rutaSalida...")
        println("$ $comando")

        val procesoHijo = Runtime.getRuntime().exec(comando)
        procesoHijo.waitFor(20, TimeUnit.SECONDS)
        if (procesoHijo.exitValue() != 0) throw Exception("Ocurrio un error al capturar la salida de $nombre")
        this.capturaSalida = rutaCaptura
    }

    fun crearCapturas(c: Capturador) {
        if (this.rutaCodigo == null || this.rutaSalida == null) throw Exception("No se ha compilado el codigo o no se ha ejecutado")
        val carpetaCapturas = "${this.rutaAbsoluta}/capturas"
        if (!File(carpetaCapturas).isDirectory) File(carpetaCapturas).mkdir()
        this.caputrarCodigo(c)
        c.borrarArgumentos()
        val opcionesAnteriores = c.obtenerListaArgumentos()
        val nuevasOpciones = opcionesAnteriores.toMutableList()
        nuevasOpciones.add("-l md")
        c.cambiarOpciones(nuevasOpciones)
        this.caputrarSalida(c)
        c.cambiarOpciones(opcionesAnteriores)
    }

    fun generarCarpetaTrab() {
        File(this.rutaAbsoluta.toString()).mkdir()
        this.moverCodigo()
    }

    fun existeCodigoFuente(): Boolean {
        return File(this.codigo).exists()
    }

    private fun moverCodigo() {
        if (this.rutaCodigo != null) throw Exception("Parece que ya se movio el codigo")
        val fuenteCodigo = Paths.get(this.codigo)
        val destinoCodigo = Paths.get(this.rutaAbsoluta.toString(), fuenteCodigo.fileName.toString())
        Files.move(fuenteCodigo, destinoCodigo)
        this.rutaCodigo = destinoCodigo
    }

    fun obtenerRutaBinario(): String? {
        return this.rutaBinario
    }

    fun obtenerEntradas(): List<EntradaEjecutable>? {
        return this.entradas
    }

    fun obtenerRutacodigo(): Path? {
        return this.rutaCodigo
    }

    fun cambiarRutaAbsoluta(ruta: Path) {
        this.rutaAbsoluta = ruta
    }

    fun obtenerRutaCapturas(): Pair<String, String> {
        if (this.capturaCodigo==null||this.capturaSalida==null) {
            throw Exception("Parece que aun no se toman las capturas :/")
        }
        val ccodigo = Paths.get(this.capturaCodigo!!).absolute().toString()
        ccodigo.replace("\\","\\\\")
        val csalida = Paths.get(this.capturaSalida!!).absolute().toString()
        csalida.replace("\\","\\\\")
        return Pair(ccodigo,csalida)
    }
}
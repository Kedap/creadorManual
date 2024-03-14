package org.isc4151.dan.creadorManual

import org.isc4151.dan.creadorManual.cli.EntradaUsuario
import org.isc4151.dan.creadorManual.cli.EntradaUsuarioCerrada
import org.isc4151.dan.creadorManual.cli.EntradaUsuarioOmision
import org.isc4151.dan.creadorManual.lenguajes.FabricaCPP
import org.isc4151.dan.creadorManual.lenguajes.FabricaJava
import org.isc4151.dan.creadorManual.utilidadesEjecutables.Capturador
import org.isc4151.dan.creadorManual.utilidadesEjecutables.EditoresTexto
import java.io.FileWriter
import java.nio.file.Paths
import kotlin.system.exitProcess

fun main() {
    val preguntarExcel = EntradaUsuario("Ingresa la ruta de tu archivo Excel")
    preguntarExcel.mostrar()
    val rutaExcel = preguntarExcel.obtenerEntrada()

    val preguntarPracticas = EntradaUsuarioOmision("Ingresa cuantas practicas quieres tener por formato", "1")
    preguntarPracticas.mostrar()
    val practicasPorCompendio = preguntarPracticas.obtenerEntrada().toInt()

    val preguntarRutaTrabajo = EntradaUsuario("Ingresa la carpeta de trabajo")
    preguntarRutaTrabajo.mostrar()
    val rutaTrabajo = preguntarRutaTrabajo.obtenerEntrada()

    val preguntarCarpetaCodigo =
        EntradaUsuarioOmision("Ingresa el nombre de la carpeta donde se encuentra el codigo", "code")
    preguntarCarpetaCodigo.mostrar()
    val rutaCodigo = preguntarCarpetaCodigo.obtenerEntrada()

    val preguntarLenguaje = EntradaUsuarioCerrada("Desea cambiar a otro lenguaje que no sea C++?")
    preguntarLenguaje.mostrar()
    val fabricaLenguaje = if (preguntarLenguaje.obtenerEntrada()) FabricaJava() else FabricaCPP()

    val preguntarModCompilador = EntradaUsuarioCerrada("Desea modificar el compliador (${fabricaLenguaje.rutaCompilador})?")
    preguntarModCompilador.mostrar()
    if (preguntarModCompilador.obtenerEntrada()) fabricaLenguaje.configurarLenguaje()

    var capturador = Capturador("silicon", "Silicon", null)
    val preguntarModCapturador = EntradaUsuarioCerrada("Desea modificar el capturador (${capturador.comando})")
    preguntarModCapturador.mostrar()
    if (preguntarModCapturador.obtenerEntrada()) capturador = nuevoCapturador()

    val preguntarModSalidas = EntradaUsuarioCerrada("¿Desea modificar las salidas despues de ejecutar?")
    preguntarModSalidas.mostrar()
    val modificarSalidas = preguntarModSalidas.obtenerEntrada()

    val preguntarModPrompt = EntradaUsuarioCerrada("¿Quiere cambiar el prompt GPT por defecto?")
    preguntarModPrompt.mostrar()
    val modPromptDefecto = preguntarModPrompt.obtenerEntrada()

    println("Leyendo el archivo $rutaExcel")
    val archivoExcel = ListaPracticas(rutaExcel)
    println("Se han encontrado ${archivoExcel.obtenerNumRegistros()} registros")

    val todasPracticas = archivoExcel.aPracticas(Paths.get(rutaTrabajo, rutaCodigo), fabricaLenguaje.obtenerLenguaje())

    println("Verificando que existan los archivos dentro de su lista...")
    for (practica in todasPracticas) {
        if (!practica.existeCodigoFuente()) {
            println("El archivo ${practica.codigo} de la practica ${practica.nombre}(${practica.id}) no fue encontrado brow :/")
            exitProcess(1)
        }
    }

    val compendios = archivoExcel.practicasACompendios(todasPracticas, practicasPorCompendio)
    println("Se estan contemplando ${compendios.size} compendios")

    for (compendio in compendios) {
        for (practica in compendio.listaPracticas) {
            practica.generarCarpetaTrab()
            practica.compilar()
            practica.crearEntradas()
            practica.ejecutar()
            if (modificarSalidas) {
                val editor =
                    if (obtenerOS() == SistemaOperativo.WINDOWS) EditoresTexto.NOTEPAD else EditoresTexto.LEAFPAD
                practica.modSalida(editor.editor)
            }
            practica.crearCapturas(capturador)
            capturador.borrarArgumentos()
        }
    }

    if (modPromptDefecto) {
        val preguntarFormato = EntradaUsuario("Ingresa la ruta del formato en donde esta tu prompt")
        preguntarFormato.mostrar()
        val rutaFormato = preguntarFormato.obtenerEntrada()
        for (compendio in compendios) compendio.crearNuevaEntradaGPT(Paths.get(rutaFormato))
    }

    val rutaCSV = Paths.get(rutaTrabajo, "practicas.csv")
    println("Escribiendo CSV en $rutaCSV")
    val archivoCSV = FileWriter(rutaCSV.toString())
    archivoCSV.escribirCSV(compendios)
    archivoCSV.close()
}

fun nuevoCapturador(): Capturador {
    val preguntarRutaCapturador = EntradaUsuario("Ingresa la ruta del binario para el capturador")
    preguntarRutaCapturador.mostrar()
    val rutaCapturador = preguntarRutaCapturador.obtenerEntrada()

    val preguntarOpciones = EntradaUsuarioOmision("Ingresa la opciones que quieres para el capturador", "")
    preguntarOpciones.mostrar()
    val opciones: List<String>? =
        if (preguntarOpciones.obtenerEntrada().isEmpty()) null else preguntarOpciones.obtenerEntrada().split(' ')

    return Capturador(rutaCapturador, "Custom", opciones)
}
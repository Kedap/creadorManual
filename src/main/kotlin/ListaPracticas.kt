package org.isc4151.dan.creadorManual

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.isc4151.dan.creadorManual.lenguajes.Lenguaje
import java.io.FileInputStream
import java.nio.file.Path
import java.nio.file.Paths

class ListaPracticas(private val ruta: String) {
    private var hoja: Sheet? = null
    private var registros: Int? = null

    init {
        val archivoExcel = FileInputStream(this.ruta)
        val xlWb = WorkbookFactory.create(archivoExcel)
        this.hoja = xlWb.getSheetAt(0)
        this.registros = this.hoja!!.lastRowNum
    }

    fun obtenerNumRegistros(): Int? {
        return this.registros
    }

    fun aPracticas(rutaTrabajo: Path, lenguaje: Lenguaje): List<Practica> {
        val practicas = mutableListOf<Practica>()
        for (fila in this.hoja!!) {
            if (obtenerValorCelda(fila.getCell(0)) == "ID") continue
            var observaciones = ""
            if (fila.getCell(2) != null) observaciones = obtenerValorCelda(fila.getCell(2))
            val practica = Practica(
                obtenerValorCelda(fila.getCell(1)),
                obtenerValorCelda(fila.getCell(0)).toInt(),
                observaciones,
                Paths.get(rutaTrabajo.toString(), obtenerValorCelda(fila.getCell(3))).toString(),
                Paths.get(rutaTrabajo.toString(), obtenerValorCelda(fila.getCell(0)).toInt().toString()),
                lenguaje
            )
            practicas.add(practica)
        }
        return practicas.toList()
    }

    fun practicasACompendios(practicas: List<Practica>, numPorCompendio: Int): List<CompendioPracticas> {
        val practicasIrregulares = practicas.size % numPorCompendio
        if (practicasIrregulares == 0) {
            return dividirPracticas(practicas, 0, numPorCompendio)
        } else {
            val practicasRegulares = practicas.size - practicasIrregulares
            val listaRegulares = practicas.slice(0..<practicasRegulares)
            val compendios = dividirPracticas(listaRegulares, 0, numPorCompendio)
            val listaIrregulares = practicas.slice(practicasRegulares..<practicas.size)
            val compendiosIrregulares =
                dividirPracticas(listaIrregulares, compendios.last().numeroCompendio + 1, practicasIrregulares)
            return compendios.plus(compendiosIrregulares)
        }
    }

    private fun dividirPracticas(
        practicas: List<Practica>,
        primerIndice: Int,
        practicasPorCompendio: Int
    ): List<CompendioPracticas> {
        val rutaTrabajo = practicas[0].rutaAbsoluta!!.parent
        val compendios = mutableListOf<CompendioPracticas>()
        var practicasCompendio = mutableListOf<Practica>()
        var contadorCompendios = primerIndice
        for (i in practicas.indices) {
            when {
                practicasPorCompendio == 1 -> {
                    compendios.add(
                        CompendioPracticas(
                            contadorCompendios,
                            listOf(practicas[i]),
                            practicasPorCompendio,
                            rutaTrabajo
                        )
                    )
                    contadorCompendios++
                }

                i == 0 -> practicasCompendio.add(practicas[i])
                i % practicasPorCompendio == 0 -> {
                    practicasCompendio = mutableListOf()
                    practicasCompendio.add(practicas[i])
                }

                i == (practicasPorCompendio - 1) || i % practicasPorCompendio == (practicasPorCompendio - 1) -> {
                    practicasCompendio.add(practicas[i])
                    compendios.add(
                        CompendioPracticas(
                            contadorCompendios,
                            practicasCompendio.toList(),
                            practicasPorCompendio,
                            rutaTrabajo
                        )
                    )
                    contadorCompendios++
                }

                else -> practicasCompendio.add(practicas[i])
            }
        }
        return compendios.toList()
    }
}

fun obtenerValorCelda(c: Cell): String {
    return when (c.cellType) {
        CellType.STRING -> c.stringCellValue
        CellType.NUMERIC -> c.numericCellValue.toInt().toString()
        else -> ""
    }
}
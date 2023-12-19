package org.isc4151.dan.creadorManual

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
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
    fun aPracticas(rutaTrabajo: Path): List<Practica> {
        val practicas = mutableListOf<Practica>()
        for (fila in this.hoja!!) {
            if (obtenerValorCelda(fila.getCell(0)) == "ID") continue
            var observaciones = ""
            if (fila.getCell(2) != null) observaciones = obtenerValorCelda(fila.getCell(2))
            val practica = Practica(
                obtenerValorCelda(fila.getCell(1)),
                obtenerValorCelda(fila.getCell(0)).toInt(),
                observaciones,
                Paths.get(rutaTrabajo.toString(),obtenerValorCelda(fila.getCell(3))).toString(),
                Paths.get(rutaTrabajo.toString(), obtenerValorCelda(fila.getCell(0)).toInt().toString()),
            )
            practicas.add(practica)
        }
        return practicas.toList()
    }

    fun practicasACompendios(practicas: List<Practica>, numPorCompendio: Int): List<CompendioPracticas> {
        val rutaTrabajo = practicas[0].rutaAbsoluta!!.parent
        val compendios = mutableListOf<CompendioPracticas>()
        var practicasCompendio = mutableListOf<Practica>()
        var contadorCompendios = 0
        for (i in practicas.indices) {
            when {
                numPorCompendio == 1 -> {
                    compendios.add(
                        CompendioPracticas(
                            contadorCompendios,
                            listOf(practicas[i]),
                            numPorCompendio,
                            rutaTrabajo
                        )
                    )
                    contadorCompendios++
                }
                i == 0 -> practicasCompendio.add(practicas[i])
                i % numPorCompendio == 0 -> {
                    practicasCompendio = mutableListOf()
                    practicasCompendio.add(practicas[i])
                }

                i == (numPorCompendio-1) || i % numPorCompendio == (numPorCompendio-1) -> {
                    practicasCompendio.add(practicas[i])
                    compendios.add(
                        CompendioPracticas(
                            contadorCompendios,
                            practicasCompendio.toList(),
                            numPorCompendio,
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
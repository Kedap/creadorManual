package org.isc4151.dan.creadorManual

import org.apache.commons.csv.CSVFormat
import java.io.Writer

fun Writer.escribirCSV(lista: List<CompendioPracticas>) {
    CSVFormat.DEFAULT.print(this).apply {
        printRecord("Titulos", "Prompt")
        lista.forEach { printRecord(it.titulo,it.entradaGPT) }
    }
}
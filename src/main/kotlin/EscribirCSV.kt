package org.isc4151.dan.creadorManual

import org.apache.commons.csv.CSVFormat
import java.io.Writer

fun Writer.escribirCSV(lista: List<CompendioPracticas>) {
    val listaColumnas = mutableListOf("Titulos", "Prompt")
    for (i in 1.rangeTo(lista[0].listaPracticas.size)) {
        listaColumnas.add("Captura$i")
        listaColumnas.add("Salida$i")
    }
    val argumentos = listaColumnas.toTypedArray()
    CSVFormat.DEFAULT.print(this).apply {
        printRecord(*argumentos)
        lista.forEach {
            val listaFila = mutableListOf(it.titulo, it.entradaGPT)
            for (practica in it.listaPracticas) {
                val (codigo, salida) = practica.obtenerRutaCapturas()
                listaFila.add(codigo)
                listaFila.add(salida)
            }
            val fila = listaFila.toTypedArray()
            printRecord(*fila)
        }
    }
}
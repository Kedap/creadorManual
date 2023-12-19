package org.isc4151.dan.creadorManual.utilidadesEjecutables

abstract class UtilidadEjecutable {
    abstract val comando: String
    abstract val nombre: String
    abstract val opciones: List<String>?
}
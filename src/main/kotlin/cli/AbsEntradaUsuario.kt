package org.isc4151.dan.creadorManual.cli

abstract class AbsEntradaUsuario<T>(val mensaje: String) {
    protected var entrada: T? = null
    abstract fun mostrar()

    fun obtenerEntrada(): T = this.entrada!!
}
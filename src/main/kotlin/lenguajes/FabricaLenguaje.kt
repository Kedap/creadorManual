package org.isc4151.dan.creadorManual.lenguajes

abstract class FabricaLenguaje() {
    abstract var rutaCompilador: String;
    abstract var opciones: List<String>;
    fun configurarObtenerLenguaje(): Lenguaje {
        configurarLenguaje();
        return obtenerLenguaje();
    }

    abstract fun configurarLenguaje()
    abstract fun obtenerLenguaje(): Lenguaje;
}
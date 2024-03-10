package org.isc4151.dan.creadorManual.lenguajes

class FabricaCPP : FabricaLenguaje() {
    override var rutaCompilador: String = "g++";
    override var opciones: List<String> = listOf();

    override fun configurarLenguaje() {
        TODO("Not yet implemented")
    }

    override fun obtenerLenguaje(): Lenguaje {
        TODO("Not yet implemented")
    }
}
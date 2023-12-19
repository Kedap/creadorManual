package org.isc4151.dan.creadorManual.utilidadesEjecutables

class EditorTexto(override val comando: String, override val nombre: String, override val opciones: List<String>?) :
    UtilidadEjecutable() {
    private var listaArgumentos: MutableList<String> =
        if (this.opciones == null) mutableListOf() else opciones.toMutableList()
    private var argumentos: MutableList<String> = mutableListOf()

    fun agregarArgumentos(argumentos: String) {
        val nuevosArgumentos = argumentos.split(' ')
        nuevosArgumentos.forEach { this.argumentos.add(it) }
    }

    fun obtenerComando(): String {
        var cmd = this.comando
        listaArgumentos.forEach { cmd += " $it" }
        argumentos.forEach { cmd += " $it" }
        return cmd
    }


    fun cambiarOpciones(nuevasOpciones: List<String>) {
        this.listaArgumentos = nuevasOpciones.toMutableList()
    }

    fun obtenerListaArgumentos(): List<String> {
        return this.listaArgumentos
    }

    fun borrarArgumentos() {
        this.argumentos = mutableListOf()
    }
}

enum class EditoresTexto(val editor: EditorTexto) {
    LEAFPAD(EditorTexto("leafpad", "Leafpad", null)),
    NOTEPAD(EditorTexto("notepad", "Notepad", null)),
}
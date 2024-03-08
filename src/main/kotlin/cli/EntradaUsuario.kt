package org.isc4151.dan.creadorManual.cli

class EntradaUsuario(msg: String) : AbsEntradaUsuario<String>(msg) {
    override fun mostrar() {
        var usuarioEntrada: String
        do {
            println(this.mensaje)
            print("> ")
            usuarioEntrada = readln()
        } while (usuarioEntrada.isEmpty())
        this.entrada = usuarioEntrada
    }
}
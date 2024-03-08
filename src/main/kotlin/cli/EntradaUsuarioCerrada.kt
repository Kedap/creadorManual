package org.isc4151.dan.creadorManual.cli

class EntradaUsuarioCerrada(msg: String) : AbsEntradaUsuario<Boolean>(msg) {
    override fun mostrar() {
        println("${this.mensaje} [S/n]")
        print("> ")
        val usuarioEntrada = readln()
        if (usuarioEntrada.isEmpty()) this.entrada = false else this.entrada =
            usuarioEntrada.first().lowercase() == "s" || usuarioEntrada.lowercase() == "chi"
    }
}
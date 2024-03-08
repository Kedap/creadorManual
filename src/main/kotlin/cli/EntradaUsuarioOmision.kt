package org.isc4151.dan.creadorManual.cli

class EntradaUsuarioOmision(msg: String, private val valorDefecto: String) : AbsEntradaUsuario<String>(msg) {
    override fun mostrar() {
        println("${this.mensaje}\n($valorDefecto) por omision")
        print("> ")
        val usuarioEntrada = readln()
        if (usuarioEntrada.isEmpty()) this.entrada = valorDefecto else this.entrada = usuarioEntrada
    }
}
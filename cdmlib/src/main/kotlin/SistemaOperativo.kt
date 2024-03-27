package org.isc4x51.dan.creadorManual

enum class SistemaOperativo {
    WINDOWS, LINUX
}

fun obtenerOS(): SistemaOperativo? {
    val os = System.getProperty("os.name").lowercase()
    return when {
        os.contains("win") -> SistemaOperativo.WINDOWS
        os.contains("nix") || os.contains("nux") || os.contains("aix") -> SistemaOperativo.LINUX
        else -> null
    }
}
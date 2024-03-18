enum class ConfiguracionTest(val cmd: String) {
    CAPTURADOR("silicon"),
    COMPILADORCPP("g++"),
    COMPILADORCPP_SECUNDARIO("clang++"),
    COMPILADORJAVA("javac"),
    EJECUTADORJAVA("java")
}
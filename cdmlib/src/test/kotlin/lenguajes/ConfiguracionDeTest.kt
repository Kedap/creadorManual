package lenguajes

/**
 * Es un enum que puedes modificar dependiendo los compiladores y la
 * plataforma en donde te encuentres para hacer los test mas facile
 */
enum class ConfiguracionDeTest(val cmd: String) {
    CAPTURADOR("silicon"),
    COMPILADORCPP("g++"),
    COMPILADORCPP_SECUNDARIO("clang++"),
    COMPILADORJAVA("javac"),
    EJECUTADORJAVA("java")
}
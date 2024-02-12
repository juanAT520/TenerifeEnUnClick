package com.juan.tenerifeenunclick.navigation

sealed class Ruta(val ruta: String) {
    object Inicial : Ruta("inicial")
    object Principal : Ruta("principal")
    object Autoctonas : Ruta("autoctonas")
    object Rutas : Ruta("rutas")
    object Recreativas : Ruta("recreativas")
    object Exoticas : Ruta("exoticas")
}
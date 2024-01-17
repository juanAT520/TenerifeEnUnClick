package com.juan.tenerifeenunclick.navigation

sealed class Rutas(val ruta: String) {
    object Inicial : Rutas("inicial")
    object CrearCuenta : Rutas("crearCuenta")
    object IniciarSesion : Rutas("iniciarSesion")
    object Principal : Rutas("principal")
}
package com.juan.tenerifeenunclick.`class`

import com.google.firebase.firestore.Exclude

data class Camino(
    val AltitudMaxima: Double = 0.0,
    val Codigo: String = "",
    val Descripcion: String = "",
    val Distancia: Double = 0.0,
    val Nombre: String = ""
)
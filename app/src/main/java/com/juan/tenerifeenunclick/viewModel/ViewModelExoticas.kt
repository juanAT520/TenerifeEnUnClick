package com.juan.tenerifeenunclick.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelExoticas : ViewModel() {
    private val _dialogoCrearUsuario = MutableStateFlow(false)
    val dialogoCrearUsuario = _dialogoCrearUsuario.asStateFlow()
    private val _dialogoIniciarSesion = MutableStateFlow(false)
    val dialogoIniciarSesion = _dialogoIniciarSesion.asStateFlow()
    private val _elementosDeFondo = MutableStateFlow(true)
    private val _estaAbierto = MutableStateFlow(false)
    val estaAbierto = _estaAbierto.asStateFlow()
    val elementosDeFondo = _elementosDeFondo.asStateFlow()
    private val _nombrePlanta = MutableStateFlow("")
    val nombrePlanta = _nombrePlanta.asStateFlow()
    private val _nombreMunicipio = MutableStateFlow("")
    val nombreMunicipio = _nombreMunicipio.asStateFlow()
    private val _textoNombreUsuario = MutableStateFlow("")
    val textoNombreUsuario = _textoNombreUsuario.asStateFlow()
    private val _textoPassword = MutableStateFlow("")
    val textoPassword = _textoPassword.asStateFlow()
    private val _textoRepitePassword = MutableStateFlow("")
    val textoRepitePassword = _textoRepitePassword.asStateFlow()
    private val _opcionesAfeccion = MutableStateFlow(listOf("Baja", "Media", "Alta"))
    val opcionesAfeccion = _opcionesAfeccion.asStateFlow()
    private val _opcionSeleccionada = MutableStateFlow("Baja")
    val opcionSeleccionada = _opcionSeleccionada.asStateFlow()


    fun AbreCierraDropDown() {
        _estaAbierto.value = !_estaAbierto.value
    }

    fun abrirCrearUsuario() {
        _dialogoCrearUsuario.value = !_dialogoCrearUsuario.value
        _elementosDeFondo.value = !_elementosDeFondo.value
    }

    fun abrirIniciarSesion() {
        _dialogoIniciarSesion.value = !_dialogoIniciarSesion.value
        _elementosDeFondo.value = !_elementosDeFondo.value
    }

    fun actualizaNombrePlanta(nuevoTexto: String) {
        _nombrePlanta.value = nuevoTexto
    }

    fun actualizaEmail(nuevoTexto: String) {
        _nombreMunicipio.value = nuevoTexto
    }

    fun actualizaNombreUsuario(nuevoTexto: String) {
        _textoNombreUsuario.value = nuevoTexto
    }

    fun actualizaPassword(nuevoTexto: String) {
        _textoPassword.value = nuevoTexto
    }

    fun actualizaPasswordOtraVez(nuevoTexto: String) {
        _textoRepitePassword.value = nuevoTexto
    }

    fun updateOpcionSeleccionada(opcion: String) {
        _opcionSeleccionada.value = opcion
    }

    fun crearListaDeExoticas(): MutableStateFlow<List<String>> {
        val especies = listOf(
            "Cactus",
            "Caña común",
            "Crásula rosada",
            "Crestagallo del cabo",
            "Pinillo",
            "Pluchea",
            "Plumacho pampero",
            "Rabogato",
            "Tojo",
            "Tunera india",
            "Valeriana roja",
            "Otra distinta"
        )
        return MutableStateFlow(especies)
    }

    fun crearListaDeMunicipios(): MutableStateFlow<List<String>> {
        val municipios = listOf(
            "Adeje",
            "Arafo",
            "Arico",
            "Arona",
            "Buenavista del Norte",
            "Candelaria",
            "El Rosario",
            "El Sauzal",
            "El Tanque",
            "Fasnia",
            "Garachico",
            "Granadilla de Abona",
            "Guía de Isora",
            "Güímar",
            "Icod de los Vinos",
            "La Guancha",
            "La Matanza de Acentejo",
            "La Orotava",
            "La Victoria de Acentejo",
            "Los Realejos",
            "Los Silos",
            "Puerto de la Cruz",
            "San Cristóbal de La Laguna",
            "San Juan de la Rambla",
            "San Miguel de Abona",
            "Santa Cruz de Tenerife",
            "Santa Úrsula",
            "Santiago del Teide",
            "Tacoronte",
            "Tegueste",
            "Vilaflor"
        )
        return MutableStateFlow(municipios)
    }
}
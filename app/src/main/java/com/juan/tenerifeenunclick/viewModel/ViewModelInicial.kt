package com.juan.tenerifeenunclick.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelInicial : ViewModel() {
    private val _dialogoCrearUsuario = MutableStateFlow(false)
    val dialogoCrearUsuario = _dialogoCrearUsuario.asStateFlow()
    private val _dialogoIniciarSesion = MutableStateFlow(false)
    val dialogoIniciarSesion = _dialogoIniciarSesion.asStateFlow()
    private val _elementosDeFondo = MutableStateFlow(true)
    val elementosDeFondo = _elementosDeFondo.asStateFlow()
    private val _textoNombre = MutableStateFlow("")
    val textoNombre = _textoNombre.asStateFlow()
    private val _textoEmail = MutableStateFlow("")
    val textoEmail = _textoEmail.asStateFlow()
    private val _textoNombreUsuario = MutableStateFlow("")
    val textoNombreUsuario = _textoNombreUsuario.asStateFlow()
    private val _textoPassword = MutableStateFlow("")
    val textoPassword = _textoPassword.asStateFlow()
    private val _textoRepitePassword = MutableStateFlow("")
    val textoRepitePassword = _textoRepitePassword.asStateFlow()

    fun abrirCrearUsuario() {
        _dialogoCrearUsuario.value = !_dialogoCrearUsuario.value
        _elementosDeFondo.value = !_elementosDeFondo.value
    }

    fun abrirIniciarSesion() {
        _dialogoIniciarSesion.value = !_dialogoIniciarSesion.value
        _elementosDeFondo.value = !_elementosDeFondo.value
    }

    fun actualizaNombre(nuevoTexto: String) {
        _textoNombre.value = nuevoTexto
    }

    fun actualizaEmail(nuevoTexto: String) {
        _textoEmail.value = nuevoTexto
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
}

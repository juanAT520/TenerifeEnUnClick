package com.juan.tenerifeenunclick.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelInicial : ViewModel() {
    private val _dialogoAbierto = MutableStateFlow(false)
    val dialogoAbierto = _dialogoAbierto.asStateFlow()
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

    fun abrirDialogo() {
        _dialogoAbierto.value = !_dialogoAbierto.value
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

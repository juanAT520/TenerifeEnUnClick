package com.juan.tenerifeenunclick.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import com.juan.tenerifeenunclick.entity.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelInicial : ViewModel() {
    private val conexion = FirebaseFirestore.getInstance()
    private lateinit var listener: ListenerRegistration
    private var _listaUsuarios = MutableStateFlow(mutableStateListOf<Usuario>())
    var listaUsuarios = _listaUsuarios.asStateFlow()
    private val _dialogoCrearUsuario = MutableStateFlow(false)
    val dialogoCrearUsuario = _dialogoCrearUsuario.asStateFlow()
    private val _dialogoIniciarSesion = MutableStateFlow(false)
    val dialogoIniciarSesion = _dialogoIniciarSesion.asStateFlow()
    private val _muestraMensajePassword = MutableStateFlow(false)
    val muestraMensajePassword = _muestraMensajePassword.asStateFlow()
    private val _muestraMensajeDatos = MutableStateFlow(false)
    val muestraMensajeDatos = _muestraMensajeDatos.asStateFlow()
    private val _elementosDeFondo = MutableStateFlow(true)
    val elementosDeFondo = _elementosDeFondo.asStateFlow()
    private val _datosUserValidos = MutableStateFlow(0)
    val datosUserValidos = _datosUserValidos.asStateFlow()
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

    fun crearListener() {
        listener = conexion.collection("Usuarios").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambio ->
                    when (cambio.type) {
                        DocumentChange.Type.ADDED -> {
                            val usuario = cambio.document.toObject<Usuario>()
                            _listaUsuarios.value.add(usuario)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val usuario = cambio.document.toObject<Usuario>()
                            _listaUsuarios.value[cambio.newIndex] = usuario
                        }
                        DocumentChange.Type.REMOVED -> {
                            val usuario = cambio.document.toObject<Usuario>()
                            _listaUsuarios.value.remove(usuario)
                        }
                    }
                }
            }
        }
    }

    fun borrarListener() {
        listener.remove()
    }

    fun compruebaPassword(password: String, repitePassword: String): Int {
        return if ((password.trim() == repitePassword.trim()) && password.trim().isNotEmpty()) {
            1
        } else {
            2
        }
    }

    fun compruebaUsuarioExistente(nombreUser: String): Int {
        val usuarios = _listaUsuarios.value
        usuarios.forEach{ usuario ->
            Log.d("nombre de usuario", usuario.nombreUser)
            Log.d("nombre introducido",nombreUser)
            if (usuario.nombreUser == nombreUser) {
                return 1
            }
        }
        return 2
    }

    fun compruebaCredenciales(nombreUser: String, password: String): Boolean {
        val usuarios = _listaUsuarios.value
        usuarios.forEach{ usuario ->
            if (usuario.nombreUser == nombreUser) {
                if (usuario.password == password) {
                    return true
                }
            }
        }
        _datosUserValidos.value = 0
        return false
    }

    fun reiniciaValidacionDatos(valor: Int) {
        _datosUserValidos.value = valor
    }

    fun addUser(email: String, nombre: String, nombreUser: String, password: String) {
        val nuevoUser = Usuario(email, nombre, nombreUser, password)
        conexion.collection("Usuarios").document(email).set(nuevoUser)
    }

    fun abrirCerrarMensajePassword() {
        _muestraMensajePassword.value = !_muestraMensajePassword.value
    }

    fun abrirCerrarMensajeDatos() {
        _muestraMensajeDatos.value = !_muestraMensajeDatos.value
    }

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

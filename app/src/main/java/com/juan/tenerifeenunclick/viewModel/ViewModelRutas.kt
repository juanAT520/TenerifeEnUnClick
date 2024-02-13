package com.juan.tenerifeenunclick.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.traceEventEnd
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import com.juan.tenerifeenunclick.R
import com.juan.tenerifeenunclick.`class`.Camino
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelRutas: ViewModel() {
    private val conexion = FirebaseFirestore.getInstance()
    private lateinit var listener: ListenerRegistration
    private var _listaRutas = MutableStateFlow(mutableStateListOf<Camino>())
    var listaRutas = _listaRutas.asStateFlow()
    private val _estaAbierto = MutableStateFlow(false)
    val estaAbierto = _estaAbierto.asStateFlow()
    private val _muestraMedidas = MutableStateFlow(false)
    val muestraMedidas = _muestraMedidas.asStateFlow()
    private val _nombreRuta = MutableStateFlow("")
    val nombreRuta = _nombreRuta.asStateFlow()
    private val _textoDesc = MutableStateFlow("Tenerife tiene multitud de rutas y senderos que abarcan todo el territorio de la isla, recorren desde los paisajes frondosos y verdes del Parque Rural de Anaga hasta otros más áridos en la zona sur.\n" +
            "\n" +
            "En esta sección podrás ver todas las rutas habilitadas para circular en bicicleta, caballo o vehículos a motor además de algunas curiosidades y lo que te puedes encontrar a lo largo de cada ruta.")
    val textoDesc = _textoDesc.asStateFlow()
    private val _imagenRuta = MutableStateFlow(R.drawable.imagenrutas)
    val imagenRuta = _imagenRuta.asStateFlow()
    private val _codigoRuta = MutableStateFlow("")
    val codigoRuta = _codigoRuta.asStateFlow()
    private val _altitudRuta = MutableStateFlow(0.0)
    val altitudRuta = _altitudRuta.asStateFlow()
    private val _distanciaRuta = MutableStateFlow(0.0)
    val distanciaRuta = _distanciaRuta.asStateFlow()
    private val _listaImagenes = addImagenes()
    val listaImagenes = _listaImagenes.asStateFlow()
    fun crearListener() {
        listener = conexion.collection("Rutas").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambio ->
                    when (cambio.type) {
                        DocumentChange.Type.ADDED -> {
                            val camino = cambio.document.toObject<Camino>()
                            _listaRutas.value.add(camino)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val camino = cambio.document.toObject<Camino>()
                            val index = _listaRutas.value.indexOfFirst { it.Codigo == camino.Codigo }
                            if (index != -1) {
                                _listaRutas.value[index] = camino
                            }
                        }
                        DocumentChange.Type.REMOVED -> {
                            val camino = cambio.document.toObject<Camino>()
                            _listaRutas.value.remove(camino)
                        }
                    }
                }
            }
        }
    }


    fun borrarListener() {
        listener.remove()
    }

    fun AbreCierraDropDown() {
        _estaAbierto.value = !_estaAbierto.value
    }

    fun MuestraMedidas() {
        _muestraMedidas.value = true
    }

    fun CambiaRuta(nombre: String, descripcion: String, imagen: Int, altitud: Double, distancia: Double, codigo: String) {
        _nombreRuta.value = nombre
        _textoDesc.value = descripcion
        _imagenRuta.value = imagen
        _altitudRuta.value = altitud
        _distanciaRuta.value = distancia
        _codigoRuta.value = codigo
    }

    private fun addImagenes(): MutableStateFlow<List<Int>> {
        val imagenes = listOf(
            R.drawable.vm3,
            R.drawable.bc5,
            R.drawable.bc1_3,
            R.drawable.bc1_2,
            R.drawable.bc1_1,
            R.drawable.vm15,
            R.drawable.bc1,
            R.drawable.bc1_12,
            R.drawable.vm20,
            R.drawable.bc1_6,
            R.drawable.bc1_7,
            R.drawable.vm13,
            R.drawable.bc1_11,
            R.drawable.bc3,
            R.drawable.vm14,
            R.drawable.bc4,
            R.drawable.vm5,
            R.drawable.vm21,
            R.drawable.vm6,
            R.drawable.vm17,
            R.drawable.bc1_9,
            R.drawable.vm1,
            R.drawable.vm11,
            R.drawable.vm2,
            R.drawable.bc2,
            R.drawable.vm9,
            R.drawable.bc1_4,
            R.drawable.vm7,
            R.drawable.vm12,
            R.drawable.bc2_1,
            R.drawable.vm4,
            R.drawable.vm19,
            R.drawable.bc1_8,
            R.drawable.vm18,
            R.drawable.bc1_5
        )
        return MutableStateFlow(imagenes)
    }
}
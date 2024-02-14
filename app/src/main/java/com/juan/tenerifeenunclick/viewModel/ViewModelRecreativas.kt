package com.juan.tenerifeenunclick.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import com.juan.tenerifeenunclick.R
import com.juan.tenerifeenunclick.entity.AreasRecreativas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelRecreativas: ViewModel() {
    private val conexion = FirebaseFirestore.getInstance()
    private lateinit var listener: ListenerRegistration
    private var _listaAreasRecreativas = MutableStateFlow(mutableStateListOf<AreasRecreativas>())
    var listaAreasRecreativas = _listaAreasRecreativas.asStateFlow()
    private val _estaAbierto = MutableStateFlow(false)
    val estaAbierto = _estaAbierto.asStateFlow()
    private val _muestraInfo = MutableStateFlow(false)
    val muestraInfo = _muestraInfo.asStateFlow()
    private val _nombreArea = MutableStateFlow("")
    val nombreArea = _nombreArea.asStateFlow()
    private val _imagenArea = MutableStateFlow(R.drawable.imagenareasrecreativas)
    val imagenArea = _imagenArea.asStateFlow()
    private val _localidad = MutableStateFlow("Selecciona un área")
    val localidad = _localidad.asStateFlow()
    private val _capacidad = MutableStateFlow(0.0)
    val capacidad = _capacidad.asStateFlow()
    private val _listaImagenes = addImagenes()
    val listaImagenes = _listaImagenes.asStateFlow()
    fun crearListener() {
        listener = conexion.collection("AreasRecreativas").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambio ->
                    when (cambio.type) {
                        DocumentChange.Type.ADDED -> {
                            val camino = cambio.document.toObject<AreasRecreativas>()
                            _listaAreasRecreativas.value.add(camino)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val frutita = cambio.document.toObject<AreasRecreativas>()
                            _listaAreasRecreativas.value[cambio.newIndex] = frutita
                        }
                        DocumentChange.Type.REMOVED -> {
                            val camino = cambio.document.toObject<AreasRecreativas>()
                            _listaAreasRecreativas.value.remove(camino)
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

    fun MuestraInfo() {
        _muestraInfo.value = true
    }

    fun CambiaArea(nombre: String, localidad: String, imagen: Int, capacidad: Double) {
        _nombreArea.value = nombre
        _localidad.value = localidad
        _imagenArea.value = imagen
        _capacidad.value = capacidad
    }

    private fun addImagenes(): MutableStateFlow<List<Int>> {
        val imagenes = listOf(
            R.drawable.el_contador,
            R.drawable.los_pedregales,
            R.drawable.san_jose_de_los_llanos,
            R.drawable.las_calderetas,
            R.drawable.hoya_del_abade,
            R.drawable.arenas_negras,
            R.drawable.el_lagar,
            R.drawable.la_laguneta_chica,
            R.drawable.la_caldera,
            R.drawable.la_quebrada,
            R.drawable.las_raices,
            R.drawable.llano_de_los_viejos,
            R.drawable.los_frailes,
            R.drawable.la_tahona,
            R.drawable.ramon_el_caminero,
            R.drawable.las_hayas,
            R.drawable.chanajiga,
            R.drawable.lomo_de_la_jara,
            R.drawable.chio,
            R.drawable.las_lajas
        )
        return MutableStateFlow(imagenes)
    }
}
package com.juan.tenerifeenunclick.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
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
    private val _listaUbicaciones = addUbicaciones()
    val listaUbicaciones = _listaUbicaciones.asStateFlow()
    private val _ubiSeleccionada = MutableStateFlow(_listaUbicaciones.value[0])
    val ubiSeleccionada = _ubiSeleccionada.asStateFlow()

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
                            val camino = cambio.document.toObject<AreasRecreativas>()
                            _listaAreasRecreativas.value[cambio.newIndex] = camino
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

    fun abreCierraDropDown() {
        _estaAbierto.value = !_estaAbierto.value
    }

    fun muestraInfo() {
        _muestraInfo.value = true
    }

    fun cambiaArea(nombre: String, localidad: String, imagen: Int, capacidad: Double, ubicacion: LatLng) {
        _nombreArea.value = nombre
        _localidad.value = localidad
        _imagenArea.value = imagen
        _capacidad.value = capacidad
        _ubiSeleccionada.value = ubicacion
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

    private fun addUbicaciones(): MutableStateFlow<List<LatLng>> {
        val ubicaciones = listOf(
            LatLng(28.207851178737684, -16.540087947168196),
            LatLng(28.342696858237712, -16.85648041832707),
            LatLng(28.327772144705076, -16.78359300667996),
            LatLng(28.44721660176364, -16.4114785355819),
            LatLng(28.415548615861532, -16.442975608599216),
            LatLng(28.320078604986914, -16.757523749073222),
            LatLng(28.343483295500373, -16.654338708599216),
            LatLng(28.39384722703261, -16.482706625998908),
            LatLng(28.357721027528708, -16.501601322090554),
            LatLng(28.53283652702193, -16.300886562017876),
            LatLng(28.419922801705255, -16.378431264418115),
            LatLng(28.52675451021855, -16.28535900859922),
            LatLng(28.355294820771636, -16.438249699093664),
            LatLng(28.351480649423532, -16.629663140288905),
            LatLng(28.329950496183756, -16.532805550926778),
            LatLng(28.36157538385459, -16.678012966759265),
            LatLng(28.344500866103996, -16.584649625286506),
            LatLng(28.45572172661129, -16.403704205149886),
            LatLng(28.266680166106376, -16.746622557705184),
            LatLng(28.19040280050728, -16.665541840302094)
        )
        return MutableStateFlow(ubicaciones)
    }
}


package com.juan.tenerifeenunclick.viewModel

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import com.juan.tenerifeenunclick.entity.Reportes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.util.concurrent.Executor

class ViewModelExoticas : ViewModel() {
    private val conexion = FirebaseFirestore.getInstance()
    private lateinit var listener: ListenerRegistration
    private val _estaAbierto = MutableStateFlow(false)
    val estaAbierto = _estaAbierto.asStateFlow()
    private val _nombrePlanta = MutableStateFlow("")
    val nombrePlanta = _nombrePlanta.asStateFlow()
    private val _nombreMunicipio = MutableStateFlow("")
    val nombreMunicipio = _nombreMunicipio.asStateFlow()
    private val _opcionesAfeccion = MutableStateFlow(listOf("Baja", "Media", "Alta"))
    val opcionesAfeccion = _opcionesAfeccion.asStateFlow()
    private val _opcionRadioButtomSeleccionada = MutableStateFlow(_opcionesAfeccion.value[0])
    val opcionSeleccionada = _opcionRadioButtomSeleccionada.asStateFlow()
    private var _listaReportes = MutableStateFlow(mutableStateListOf<Reportes>())
    var listaReportes = _listaReportes.asStateFlow()

    fun crearListener() {
        listener = conexion.collection("Reportes").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambio ->
                    when (cambio.type) {
                        DocumentChange.Type.ADDED -> {
                            val reportes = cambio.document.toObject<Reportes>()
                            _listaReportes.value.add(reportes)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val reportes = cambio.document.toObject<Reportes>()
                            _listaReportes.value[cambio.newIndex] = reportes
                        }
                        else -> {
                            val reportes = cambio.document.toObject<Reportes>()
                            _listaReportes.value.remove(reportes)
                        }
                    }
                }
            }
        }
    }

    fun borrarListener() {
        listener.remove()
    }

    fun establecerNombrePlanta(nombre: String) {
        _nombrePlanta.value = nombre
    }

    fun establecerNombreMunicipio(nombre: String) {
        _nombreMunicipio.value = nombre
    }

    fun updateOpcionSeleccionada(opcion: String) {
        _opcionRadioButtomSeleccionada.value = opcion
    }

    fun addReporte(especie: String, municipio: String, incidencia: String) {
        val nuevoReporte = Reportes(especie, municipio, incidencia)
        conexion.collection("Reportes").add(nuevoReporte)
    }

    fun abreCierraCamara() {
        _estaAbierto.value = !_estaAbierto.value
    }

    fun limpiarCampos() {
        _nombrePlanta.value = ""
        _nombreMunicipio.value = ""
        _opcionRadioButtomSeleccionada.value = _opcionesAfeccion.value[0]
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

    fun tomarFoto(
        camController: LifecycleCameraController,
        executor: Executor,
        imagenURI: MutableState<Uri?>
    ) {
        val foto = File.createTempFile("imagen", ".jpg")
        val destino = ImageCapture.OutputFileOptions.Builder(foto).build()
        camController.takePicture(destino, executor, object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                println(outputFileResults.savedUri)
                imagenURI.value = outputFileResults.savedUri
            }
            override fun onError(exception: ImageCaptureException) {
                // si la imagen no se guarda el código vendrá aquí
            }
        })
    }
}
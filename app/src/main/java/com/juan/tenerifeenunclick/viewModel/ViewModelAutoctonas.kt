package com.juan.tenerifeenunclick.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import com.juan.tenerifeenunclick.R
import com.juan.tenerifeenunclick.`class`.Autoctonas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelAutoctonas : ViewModel() {
    private val conexion = FirebaseFirestore.getInstance()
    private lateinit var listener: ListenerRegistration
    private var _listaAutoctonas = MutableStateFlow(mutableStateListOf<Autoctonas>())
    var listaAutoctonas = _listaAutoctonas.asStateFlow()
    private var _listaImagenes = addImagenes()
    var listaImagenes = _listaImagenes.asStateFlow()

    fun crearListener() {
        listener = conexion.collection("PlantasAutoctonas").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambio ->
                    when (cambio.type) {
                        DocumentChange.Type.ADDED -> {
                            val frutita = cambio.document.toObject<Autoctonas>()
                            _listaAutoctonas.value.add(frutita)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val frutita = cambio.document.toObject<Autoctonas>()
                            _listaAutoctonas.value[cambio.newIndex] = frutita
                        }
                        else -> {
                            val frutita = cambio.document.toObject<Autoctonas>()
                            _listaAutoctonas.value.remove(frutita)
                        }
                    }
                }
            }
        }
    }
    fun borrarListener() {
        listener.remove()
    }

    private fun addImagenes(): MutableStateFlow<List<Int>> {
        val imagenes = listOf(
            R.drawable.acebino,
            R.drawable.bicacaro,
            R.drawable.hierba_pajonera,
            R.drawable.boton_de_oro,
            R.drawable.brezo,
            R.drawable.cardon,
            R.drawable.cedro,
            R.drawable.palmera_canaria,
            R.drawable.codeso,
            R.drawable.lotus_berthelotii,
            R.drawable.cresta_de_gallo,
            R.drawable.drago,
            R.drawable.tabaiba_dulce,
            R.drawable.faya,
            R.drawable.pijara,
            R.drawable.madrono,
            R.drawable.tarajal,
            R.drawable.malva_de_risco,
            R.drawable.laurel_bien,
            R.drawable.retama,
            R.drawable.mato_blanco,
            R.drawable.pino_canario,
            R.drawable.violeta_del_teide,
            R.drawable.tajinaste_rojo
        )
        return MutableStateFlow(imagenes)
    }
}

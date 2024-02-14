package com.juan.tenerifeenunclick.gui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.juan.tenerifeenunclick.R
import com.juan.tenerifeenunclick.ui.theme.colorFuente
import com.juan.tenerifeenunclick.ui.theme.fondoTextField
import com.juan.tenerifeenunclick.viewModel.ViewModelRecreativas

@Composable
fun PantallaRecreativas(){
    val viewModelRecreativas: ViewModelRecreativas = viewModel()
    val listaRutas = viewModelRecreativas.listaAreasRecreativas.collectAsState().value
    val listaImagenes = viewModelRecreativas.listaImagenes.collectAsState().value
    val abierto = viewModelRecreativas.estaAbierto.collectAsState().value
    val muestraInfo = viewModelRecreativas.muestraInfo.collectAsState().value
    val nombreArea = viewModelRecreativas.nombreArea.collectAsState().value
    val imagenArea = viewModelRecreativas.imagenArea.collectAsState().value
    val capacidad = viewModelRecreativas.capacidad.collectAsState().value
    val localidad = viewModelRecreativas.localidad.collectAsState().value
    val textoDesc = "En esta página verás una lista con todas las áreas recreativas e información, como el número de plazas disponibles, forma de contacto para pedir permisos para quedarte a dormir en ellas o hacer una parrillada, fotos para hacerte una idea de lo que te puedes encontrar y un mapa para saber exactamente donde están."
    DisposableEffect(viewModelRecreativas) {
        viewModelRecreativas.crearListener()
        onDispose { viewModelRecreativas.borrarListener() }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp, 0.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        LogotipoChato()
        Text(
            text = "Áreas:",
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            color = colorFuente,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 25.dp)
        )
        TextButton(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(fondoTextField),
            onClick = { viewModelRecreativas.AbreCierraDropDown() }) {
            Text(
                color = colorFuente,
                textAlign = TextAlign.Center,
                text = "$localidad - $nombreArea"
            )
            DropdownMenu(
                expanded = abierto,
                onDismissRequest = { viewModelRecreativas.AbreCierraDropDown() }) {
                listaRutas.forEachIndexed() { index, area ->
                    DropdownMenuItem(
                        text = { Text("${area.localidad} - ${area.nombre}") },
                        onClick = {
                            viewModelRecreativas.CambiaArea(
                                area.nombre,
                                area.localidad,
                                listaImagenes[index],
                                area.Plazas
                            )
                            viewModelRecreativas.MuestraInfo()
                            viewModelRecreativas.AbreCierraDropDown()
                        }
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = imagenArea),
            contentDescription = "Imagen $nombreArea",
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
        )
        if (muestraInfo) {
            Card (
                modifier = Modifier
                    .size(300.dp)
                    .padding(top = 40.dp)
            ) {
                val cameraPositionState = CameraPositionState(CameraPosition(LatLng(28.208343, -16.539128),15f,0f, 0f))
                val marker = LatLng(28.208343, -16.539128)
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    content = { Marker(position = marker) }
                )
            }
            Text(text = "Número de plazas: $capacidad",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                color = colorFuente,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 15.dp)
            )
            Text(text = "Se puede solicitar autorización e información a través de:",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                color = colorFuente,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp, 10.dp)
            )
            Text(text = "·Número de teléfono: 922 843 097",
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                color = colorFuente,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp, 10.dp)
            )
            Text(text = "·Dirección de email: coordinacionmam@tenerife.es",
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                color = colorFuente,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, bottom = 40.dp)
            )
        } else {
            Text(text = textoDesc,
                fontSize = 15.sp,
                color = colorFuente,
                modifier = Modifier.padding(0.dp, 35.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.municipios),
                contentDescription = "Imagen municipios",
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .padding(0.dp, 25.dp)
            )
        }
    }
}
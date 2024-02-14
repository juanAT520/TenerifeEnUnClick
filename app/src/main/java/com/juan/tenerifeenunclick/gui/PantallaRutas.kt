package com.juan.tenerifeenunclick.gui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
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
import com.juan.tenerifeenunclick.ui.theme.colorFuente
import com.juan.tenerifeenunclick.ui.theme.fondoTextField
import com.juan.tenerifeenunclick.viewModel.ViewModelRutas

@Composable
fun PantallaRutas() {
    val viewModelRutas: ViewModelRutas = viewModel()
    val listaRutas = viewModelRutas.listaRutas.collectAsState().value
    val listaImagenes = viewModelRutas.listaImagenes.collectAsState().value
    val abierto = viewModelRutas.estaAbierto.collectAsState().value
    val muestraMedidas = viewModelRutas.muestraMedidas.collectAsState().value
    val nombreRuta = viewModelRutas.nombreRuta.collectAsState().value
    val textoDescripcion = viewModelRutas.textoDesc.collectAsState().value
    val imagenRuta = viewModelRutas.imagenRuta.collectAsState().value
    val altitudRuta = viewModelRutas.altitudRuta.collectAsState().value
    val distanciaRuta = viewModelRutas.distanciaRuta.collectAsState().value
    val codigoRuta = viewModelRutas.codigoRuta.collectAsState().value
    DisposableEffect(viewModelRutas) {
        viewModelRutas.crearListener()
        onDispose { viewModelRutas.borrarListener() }
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
            text = "Ruta:",
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
            onClick = { viewModelRutas.AbreCierraDropDown() }) {
            Text(
                color = colorFuente,
                textAlign = TextAlign.Center,
                text = "$codigoRuta - $nombreRuta"
            )
            DropdownMenu(
                expanded = abierto,
                onDismissRequest = { viewModelRutas.AbreCierraDropDown() }) {
                listaRutas.forEachIndexed() { index, ruta ->
                    DropdownMenuItem(
                        text = { Text("${ruta.Codigo} - ${ruta.Nombre}") },
                        onClick = {
                            viewModelRutas.CambiaRuta(
                                ruta.Nombre,
                                ruta.Descripcion,
                                listaImagenes[index],
                                ruta.AltitudMaxima,
                                ruta.Distancia,
                                ruta.Codigo
                            )
                            viewModelRutas.MuestraMedidas()
                            viewModelRutas.AbreCierraDropDown()
                        }
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = imagenRuta),
            contentDescription = "Imagen $nombreRuta",
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
        )
        if (muestraMedidas) {
            Text(text = "Distancia: $distanciaRuta kms",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                color = colorFuente,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 15.dp)
            )
            Text(text = "Altitud máxima: $altitudRuta metros",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                color = colorFuente,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp, 10.dp)
            )
        }
        Text(text = textoDescripcion,
            fontSize = 15.sp,
            color = colorFuente,
            modifier = Modifier.padding(top = 10.dp, bottom = 35.dp)
        )
    }
}
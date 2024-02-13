package com.juan.tenerifeenunclick.gui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInExpo
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.juan.tenerifeenunclick.ui.theme.colorBoton
import com.juan.tenerifeenunclick.ui.theme.colorFuente
import com.juan.tenerifeenunclick.viewModel.ViewModelAutoctonas

@Composable
fun PantallaAutoctonas(){
    val viewModelAutoctonas: ViewModelAutoctonas = viewModel()
    val listaPlantas = viewModelAutoctonas.listaAutoctonas.collectAsState().value
    val listaImagenes = viewModelAutoctonas.listaImagenes.collectAsState().value
    DisposableEffect(viewModelAutoctonas) {
        viewModelAutoctonas.crearListener()
        onDispose { viewModelAutoctonas.borrarListener() }
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp, 0.dp)
    ) {
        LogotipoChato()
        LazyColumn() {
            itemsIndexed(listaPlantas) { index, planta ->
                TarjetaPlanta(
                    imagen = listaImagenes[index],
                    nombre = planta.Nombre,
                    nombreC = planta.NombreC,
                    descripcion = planta.Desc
                )
            }
        }
    }
}

@Composable
private fun TarjetaPlanta(
    imagen: Int,
    nombre: String,
    nombreC: String,
    descripcion: String
) {
    val expanded = remember { mutableStateOf(true) }
    val paddingAnimado = animateDpAsState(if (expanded.value) 0.dp else 5.dp, label = "padding")
    ElevatedCard(
        modifier = Modifier
            .padding(20.dp, 10.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Surface(color = colorBoton) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(paddingAnimado.value)
                    .animateContentSize(
                        animationSpec = tween(400, easing = EaseInExpo)
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        expanded.value = !expanded.value
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = imagen),
                    contentDescription = "imagen $nombre",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                )
                Text(
                    text = nombre,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    color = colorFuente,
                    modifier = Modifier.padding(0.dp, 10.dp)
                )
                Text(
                    text = nombreC,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = colorFuente,
                    modifier = Modifier.padding(0.dp, 10.dp)
                )
                if (!expanded.value) {
                    Text(
                        text = descripcion,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        fontSize = 11.sp,
                        color = colorFuente,
                        modifier = Modifier.padding(20.dp, 10.dp)
                    )
                }
            }
        }
    }
}

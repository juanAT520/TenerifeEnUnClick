package com.juan.tenerifeenunclick.gui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juan.tenerifeenunclick.ui.theme.colorFuente
import com.juan.tenerifeenunclick.ui.theme.colorFuenteBoton
import com.juan.tenerifeenunclick.ui.theme.fondoTextField
import com.juan.tenerifeenunclick.viewModel.ViewModelExoticas

@Composable
fun PantallaExoticas(){
    val viewModelExoticas: ViewModelExoticas = viewModel()
    val opcionesAfeccion = viewModelExoticas.opcionesAfeccion.collectAsState().value
    val opcionSeleccionada = viewModelExoticas.opcionSeleccionada.collectAsState().value
    val listaExoticas = viewModelExoticas.crearListaDeExoticas().collectAsState().value
    val listaMunicipios = viewModelExoticas.crearListaDeMunicipios().collectAsState().value
    val nombrePlanta = viewModelExoticas.nombrePlanta.collectAsState().value
    val nombreMunicipio = viewModelExoticas.nombreMunicipio.collectAsState().value
    val comboPlantasAbierto = viewModelExoticas.estaAbierto.collectAsState().value
    val comboMunicipiosAbierto = viewModelExoticas.estaAbierto.collectAsState().value

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
            text = "¿Que planta has visto?",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            color = colorFuente,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 25.dp)
        )
        FilaDropDown("Especie: ", listaExoticas)
        FilaDropDown("Municipio: ", listaMunicipios)
        //FilaDropDown("Cantidad: ", listaExoticas)
        BotonRegistro("Ubicación: ", "Pulsa para localizar", Icons.Rounded.LocationOn) {}
        BotonRegistro("Foto: ", "Envía una foto", Icons.Rounded.CameraAlt) {}
        Text(
            text = "Afección al medio:",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            color = colorFuente,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 25.dp)
        )
        Column(
            Modifier
                .selectableGroup()
                .fillMaxWidth()
        ) {
            opcionesAfeccion.forEach { opcion ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable { viewModelExoticas.updateOpcionSeleccionada(opcion) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = opcionSeleccionada == opcion,
                        onClick = { viewModelExoticas.updateOpcionSeleccionada(opcion) }
                    )
                    Text(
                        text = opcion,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = colorFuente,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
        Row (
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp)) {
            BotonFormulario("Limpiar") {}
            BotonFormulario("Enviar") {}
        }
    }
}

@Composable
private fun BotonRegistro(texto: String, textoBoton: String, icono: ImageVector, accion: () -> Unit) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    )  {
        Text(
            text = texto,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            color = colorFuente,
            modifier = Modifier.padding(end = 15.dp)
        )
        Button(
            onClick = { accion },
            colors = ButtonDefaults.buttonColors(
                containerColor = fondoTextField,
                contentColor = colorFuenteBoton
            ),
            modifier = Modifier.width(150.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icono, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(textoBoton)
            }
        }
    }
}

@Composable
private fun FilaDropDown(texto: String, lista: List<String>) {
    val abierto = remember { mutableStateOf(false) }
    val seleccionado = remember { mutableStateOf("") }
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = texto,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            color = colorFuente,
            modifier = Modifier.padding(end = 15.dp)
        )
        TextButton(
            colors = ButtonDefaults.buttonColors(fondoTextField),
            modifier = Modifier.width(200.dp),
            onClick = { abierto.value = true }) {
            Text(
                color = colorFuenteBoton,
                textAlign = TextAlign.Center,
                text = seleccionado.value
            )
            DropdownMenu(
                expanded = abierto.value,
                onDismissRequest = { abierto.value = false }) {
                lista.forEach() { area ->
                    DropdownMenuItem(
                        text = { Text(area) },
                        onClick = {
                            seleccionado.value = area
                            abierto.value = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BotonFormulario(texto: String, accion: () -> Unit) {
    Button(
        onClick = accion,
        shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
        colors = ButtonDefaults.buttonColors(containerColor = fondoTextField)
    ) {
        Text(
            text = texto,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = colorFuente,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


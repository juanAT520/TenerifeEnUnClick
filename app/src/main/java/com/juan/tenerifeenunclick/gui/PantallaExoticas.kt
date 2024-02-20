package com.juan.tenerifeenunclick.gui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.juan.tenerifeenunclick.ui.theme.White
import com.juan.tenerifeenunclick.ui.theme.colorFuente
import com.juan.tenerifeenunclick.ui.theme.colorFuenteBoton
import com.juan.tenerifeenunclick.ui.theme.fondoTextField
import com.juan.tenerifeenunclick.viewModel.ViewModelExoticas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun PantallaExoticas(context: Context, scope: CoroutineScope) {
    val viewModelExoticas: ViewModelExoticas = viewModel()
    val opcionesAfeccion = viewModelExoticas.opcionesAfeccion.collectAsState().value
    val listaExoticas = viewModelExoticas.crearListaDeExoticas().collectAsState().value
    val listaMunicipios = viewModelExoticas.crearListaDeMunicipios().collectAsState().value
    val nombrePlanta = viewModelExoticas.nombrePlanta.collectAsState().value
    val nombreMunicipio = viewModelExoticas.nombreMunicipio.collectAsState().value
    val opcionSeleccionada = viewModelExoticas.opcionSeleccionada.collectAsState().value
    val abrirCamara = viewModelExoticas.abrirCamara.collectAsState().value
    val muestraFoto = viewModelExoticas.muestraDialogoFoto.collectAsState().value
    val muestraUbicacion = viewModelExoticas.muestraDialogoUbicacion.collectAsState().value
    val muestraDialogo = remember { mutableStateOf(false) }
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


    DisposableEffect(viewModelExoticas) {
        viewModelExoticas.crearListener()
        onDispose { viewModelExoticas.borrarListener() }
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
            text = "¿Que planta has visto?",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            color = colorFuente,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 25.dp)
        )
        FilaDropDown("Especie: ", listaExoticas, viewModelExoticas.nombrePlanta) { seleccion ->
            viewModelExoticas.establecerNombrePlanta(seleccion)
        }
        FilaDropDown("Municipio: ", listaMunicipios, viewModelExoticas.nombreMunicipio) { seleccion ->
            viewModelExoticas.establecerNombreMunicipio(seleccion)
        }
        BotonRegistro("Ubicación: ", "Pulsa para localizar", Icons.Rounded.LocationOn) {
            viewModelExoticas.abreDialogoUbicacion()
        }
        BotonRegistro("Foto: ", "Envía una foto", Icons.Rounded.CameraAlt) {
            viewModelExoticas.abreCierraCamara()
        }
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
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp)
        ) {
            BotonFormulario("Limpiar") {
                viewModelExoticas.limpiarCampos()
            }
            BotonFormulario("Enviar") {
                viewModelExoticas.addReporte(
                    nombrePlanta,
                    nombreMunicipio,
                    opcionSeleccionada
                )
                muestraDialogo.value = true
                viewModelExoticas.limpiarCampos()
            }
        }
    }
    if (abrirCamara) DialogoAbrirCamara(context, viewModelExoticas, scope)
    if (muestraDialogo.value) dialogoReporteEnviado(muestraDialogo)
    if (muestraFoto) dialogoMuestraFoto(viewModelExoticas)
    if (muestraUbicacion) MostrarUbicacionExacta(fusedLocationClient, viewModelExoticas)
}

@Composable
fun dialogoMuestraFoto(viewModelExoticas: ViewModelExoticas) {
    val imagePainter = rememberAsyncImagePainter(model = viewModelExoticas.imagenURI.value)

    Dialog(
        onDismissRequest = { viewModelExoticas.cierraDialogoFoto() }
    ) {
        (LocalView.current.parent as? DialogWindowProvider)?.window?.setDimAmount(0.8f)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "Imagen Foto Tomada",
                modifier = Modifier.size(400.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            ) {
                Button(onClick = {
                    viewModelExoticas.abreCierraCamara()
                    viewModelExoticas.cierraDialogoFoto()
                }) {
                    Text(text = "Saca otra")
                }
                Button(onClick = { viewModelExoticas.cierraDialogoFoto() }) {
                    Text(text = "Usar esta foto")
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun DialogoAbrirCamara(
    context: Context,
    viewModelExoticas: ViewModelExoticas,
    scope: CoroutineScope
) {
    Dialog(
        onDismissRequest = { viewModelExoticas.abreCierraCamara() },
        properties = DialogProperties(decorFitsSystemWindows = true)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            (LocalView.current.parent as? DialogWindowProvider)?.window?.setDimAmount(0.05f)
            val permission = rememberPermissionState(permission = Manifest.permission.CAMERA)
            val camController = remember { LifecycleCameraController(context) }
            val lifecicle = LocalLifecycleOwner.current

            LaunchedEffect(Unit) {
                permission.launchPermissionRequest()
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                if (permission.status.isGranted) {
                    iniciarCamara(camController, lifecycle = lifecicle)
                } else {
                    Text(text = "Denegado")
                }
                FloatingActionButton(
                    shape = CircleShape,
                    containerColor = White,
                    modifier = Modifier.padding(bottom = 30.dp),
                    onClick = {
                    val executor = ContextCompat.getMainExecutor(context)
                    viewModelExoticas.tomarFoto(camController, executor)
                    scope.launch {
                        delay(1000)
                        viewModelExoticas.abreCierraCamara()
                        viewModelExoticas.abreDialogoFoto()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Camera,
                        contentDescription = "Icono cámara"
                    )
                }
            }
        }
    }
}


@Composable
private fun BotonRegistro(
    texto: String,
    textoBoton: String,
    icono: ImageVector,
    accion: () -> Unit
) {
    Row(
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
        Button(
            onClick = { accion() },
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
fun FilaDropDown(
    texto: String,
    lista: List<String>,
    opcionSeleccionada: StateFlow<String>,
    seleccionarElemento: (String) -> Unit
) {
    val abierto = remember { mutableStateOf(false) }

    Row(
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
                text = opcionSeleccionada.value
            )
            DropdownMenu(
                expanded = abierto.value,
                onDismissRequest = { abierto.value = false }) {
                lista.forEach() { elementoLista ->
                    DropdownMenuItem(
                        text = { Text(elementoLista) },
                        onClick = {
                            seleccionarElemento(elementoLista)
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

@Composable
private fun iniciarCamara(
    camController: LifecycleCameraController,
    lifecycle: LifecycleOwner,
    modifier: Modifier = Modifier
) {
    camController.bindToLifecycle(lifecycle)
    AndroidView(modifier = modifier, factory = { context ->
        val previewView = PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        previewView.controller = camController
        previewView
    })
}

@Composable
private fun dialogoReporteEnviado (
    muestraDialogo: MutableState<Boolean>
) {
    AlertDialog(
        onDismissRequest = {
            muestraDialogo.value = false
        },
        confirmButton = {
            Button(onClick = {
                muestraDialogo.value = false
            }) {
                Text(text = "Cerrar")
            }
        },
        title = { Text("Enviado") },
        text = { Text("El reporte ha sido enviado con éxito") }
    )
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MostrarUbicacionExacta(fusedLocationClient: FusedLocationProviderClient, viewModelExoticas: ViewModelExoticas) {
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val cameraPositionState = remember { mutableStateOf(CameraPositionState(CameraPosition(LatLng(0.0, -0.0),  0f,  0f,  0f))) }
    val ubicacion = remember { mutableStateOf(LatLng(0.0, -0.0)) }

    LaunchedEffect(Unit) {
        locationPermissionState.launchPermissionRequest()
        if (locationPermissionState.status.isGranted) {
            val infoLocalizacion = fusedLocationClient.lastLocation
            infoLocalizacion.addOnSuccessListener {
                ubicacion.value = LatLng(it.latitude, it.longitude)
                cameraPositionState.value = CameraPositionState(CameraPosition(ubicacion.value, 16f, 0f, 0f))
            }
        }
    }
    Dialog(
        onDismissRequest = { viewModelExoticas.cierraDialogoUbicacion() }
    ) {
        (LocalView.current.parent as? DialogWindowProvider)?.window?.setDimAmount(0.8f)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .size(300.dp)
                    .padding(0.dp, 20.dp)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState.value,
                    content = {
                        Marker(position = ubicacion.value)
                    }
                )
            }
            Button(onClick = { viewModelExoticas.cierraDialogoUbicacion() }) {
                Text(text = "Usar esta ubicación")
            }
        }
    }
}

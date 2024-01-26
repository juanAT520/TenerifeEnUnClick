package com.juan.tenerifeenunclick.gui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Forest
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.juan.tenerifeenunclick.navigation.Rutas
import com.juan.tenerifeenunclick.ui.theme.autumn_foliage
import com.juan.tenerifeenunclick.ui.theme.colorBoton
import com.juan.tenerifeenunclick.ui.theme.colorFuente
import com.juan.tenerifeenunclick.ui.theme.colorFuenteBoton
import com.juan.tenerifeenunclick.ui.theme.fondo
import com.juan.tenerifeenunclick.ui.theme.fondoTextField
import com.juan.tenerifeenunclick.viewModel.ViewModelInicial

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PantallaInicial(navController: NavHostController) {
    val miViewModel: ViewModelInicial = viewModel()
    val estaAbierto = miViewModel.dialogoAbierto.collectAsState().value
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = fondo
    ) {
        if (miViewModel.elementosDeFondo.value) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(25.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Forest,
                        contentDescription = "Icono Árboles",
                        modifier = Modifier.size(60.dp),
                        tint = autumn_foliage
                    )
                    Text(
                        text = "Tenerife en un Click",
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorFuente
                    )
                }
                Text(
                    text = "En esta isla de Canarias las aventuras no terminan, desde perderte por los montes de Anaga a hacer windsurf en el acogedor pueblo del Médano. \n" +
                            "\n" +
                            "Crea tu cuenta de usuario para descubrir todo lo que te ofrece.",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W500,
                    color = colorFuente
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(0.dp, 8.dp)
                ) {
                    Button(
                        onClick = { miViewModel.abrirDialogo() },
                        colors = ButtonDefaults.buttonColors(colorBoton),
                        shape = RoundedCornerShape(20.dp), // siguiendo las normas de material design ;)
                        content = {
                            Text(
                                text = "Crear cuenta",
                                fontSize = 25.sp,
                                color = colorFuenteBoton
                            )
                        }
                    )
                    TextButton(
                        onClick = { navController.navigate(Rutas.IniciarSesion.ruta) },
                        modifier = Modifier.padding(8.dp),
                        content = {
                            Text(
                                text = "Iniciar sesión",
                                fontSize = 20.sp,
                                color = colorFuenteBoton
                            )
                        }
                    )
                }
            }
        }
        if (estaAbierto) DialogoCrearUsuario(miViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun DialogoCrearUsuario(miViewModel: ViewModelInicial) {
    val textoNombre = miViewModel.textoNombre.collectAsState().value
    val textoEmail = miViewModel.textoEmail.collectAsState().value
    val textoNombreUsuario = miViewModel.textoNombreUsuario.collectAsState().value
    val textoPassword = miViewModel.textoPassword.collectAsState().value
    val textoRepitePassword = miViewModel.textoRepitePassword.collectAsState().value
    Dialog(
        onDismissRequest = { miViewModel.abrirDialogo() },
        properties = DialogProperties(decorFitsSystemWindows = true)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                (LocalView.current.parent as? DialogWindowProvider)?.window?.setDimAmount(0.15f)
                Icon(
                    imageVector = Icons.Rounded.Forest,
                    contentDescription = "Icono Árboles",
                    modifier = Modifier.size(60.dp),
                    tint = autumn_foliage
                )
                Text(
                    text = "Tenerife en un Click",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorFuente
                )
            }
            Texto(text = "Nombre completo")
            CampoDeTexto(textoNombre) { nuevoTexto -> miViewModel.actualizaNombre(nuevoTexto) }
            Texto(text = "Dirección de email")
            CampoDeTexto(texto = textoEmail) { nuevoTexto -> miViewModel.actualizaEmail(nuevoTexto) }
            Texto(text = "Nombre de usuario")
            CampoDeTexto(texto = textoNombreUsuario) { nuevoTexto ->
                miViewModel.actualizaNombreUsuario(
                    nuevoTexto
                )
            }
            Texto(text = "Contraseña")
            CampoDeTexto(texto = textoPassword) { nuevoTexto ->
                miViewModel.actualizaPassword(
                    nuevoTexto
                )
            }
            Texto(text = "Repite la contraseña")
            CampoDeTexto(texto = textoRepitePassword) { nuevoTexto ->
                miViewModel.actualizaPasswordOtraVez(
                    nuevoTexto
                )
            }
            Button(
                onClick = { miViewModel.abrirDialogo() },
                colors = ButtonDefaults.buttonColors(colorBoton),
                shape = RoundedCornerShape(20.dp), // siguiendo las normas de material design ;)
                content = {
                    Text(
                        text = "Crear cuenta",
                        fontSize = 25.sp,
                        color = colorFuenteBoton
                    )
                },
                modifier = Modifier.padding(top = 20.dp)
            )
            IconButton(
                onClick = { miViewModel.abrirDialogo() },
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Icono Árboles",
                            tint = colorBoton
                        )
                        Text(
                            text = "Volver",
                            color = colorBoton
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun Texto(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.W500,
        color = colorFuente,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CampoDeTexto(texto: String, accion: (String) -> Unit) {
    TextField(
        value = texto,
        onValueChange = accion,
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.textFieldColors(containerColor = fondoTextField)
    )
}
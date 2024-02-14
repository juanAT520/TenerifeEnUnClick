package com.juan.tenerifeenunclick.gui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.juan.tenerifeenunclick.navigation.Ruta
import com.juan.tenerifeenunclick.ui.theme.autumn_foliage
import com.juan.tenerifeenunclick.ui.theme.colorBoton
import com.juan.tenerifeenunclick.ui.theme.colorFuente
import com.juan.tenerifeenunclick.ui.theme.colorFuenteBoton
import com.juan.tenerifeenunclick.ui.theme.fondoTextField
import com.juan.tenerifeenunclick.viewModel.ViewModelInicial

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PantallaInicial(navController: NavHostController) {
    val viewModelInicial: ViewModelInicial = viewModel()
    val dialogoCrearUsuario = viewModelInicial.dialogoCrearUsuario.collectAsState().value
    val dialogoIniciarSesion = viewModelInicial.dialogoIniciarSesion.collectAsState().value
    val colorDeFondo = arrayOf(0.65f to Color(0xFFB6D866), 1.0f to Color(0xFF5D8D32))

    // Probando animaciones. Esto es un contenedor en el que si ahora mismo encapsulo el contenido del siguiente if hará una animación al cambiar el estado de dialogoCrearUsuario
    //AnimatedVisibility(!dialogoCrearUsuario) {}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = colorDeFondo))
    )
    if (viewModelInicial.elementosDeFondo.value) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
        ) {
            Logotipo()
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
                    onClick = { viewModelInicial.abrirCrearUsuario() },
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
                    onClick = { viewModelInicial.abrirIniciarSesion() },
                    modifier = Modifier.padding(8.dp),
                    content = {
                        Text(
                            text = "Iniciar sesión",
                            fontSize = 20.sp,
                            color = colorBoton
                        )
                    }
                )
            }
        }
    }

    if (dialogoCrearUsuario) DialogoCrearUsuario(viewModelInicial, navController)
    if (dialogoIniciarSesion) DialogoIniciarSesion(viewModelInicial, navController)
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun DialogoCrearUsuario(miViewModel: ViewModelInicial, navController: NavHostController) {
    val textoNombre = miViewModel.textoNombre.collectAsState().value
    val textoEmail = miViewModel.textoEmail.collectAsState().value
    val textoNombreUsuario = miViewModel.textoNombreUsuario.collectAsState().value
    val textoPassword = miViewModel.textoPassword.collectAsState().value
    val textoRepitePassword = miViewModel.textoRepitePassword.collectAsState().value
    Dialog(
        onDismissRequest = { miViewModel.abrirCrearUsuario() },
        properties = DialogProperties(decorFitsSystemWindows = true)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            (LocalView.current.parent as? DialogWindowProvider)?.window?.setDimAmount(0.05f)
            Logotipo()
            Texto(text = "Nombre completo")
            CampoDeTexto(textoNombre) { nuevoTexto -> miViewModel.actualizaNombre(nuevoTexto) }
            Texto(text = "Dirección de email")
            CampoDeTexto(texto = textoEmail) { nuevoTexto -> miViewModel.actualizaEmail(nuevoTexto) }
            Texto(text = "Nombre de usuario")
            CampoDeTexto(texto = textoNombreUsuario) { nuevoTexto -> miViewModel.actualizaNombreUsuario(nuevoTexto) }
            Texto(text = "Contraseña")
            CampoDeTexto(texto = textoPassword) { nuevoTexto -> miViewModel.actualizaPassword(nuevoTexto) }
            Texto(text = "Repite la contraseña")
            CampoDeTexto(texto = textoRepitePassword) { nuevoTexto -> miViewModel.actualizaPasswordOtraVez(nuevoTexto) }
            BotonesSesion(texto1 = "Crear cuenta", navigate = { navController.navigate(Ruta.Principal.ruta) }) { miViewModel.abrirCrearUsuario() }
        }
    }
}

@Composable
private fun DialogoIniciarSesion(miViewModel: ViewModelInicial, navController: NavHostController) {
    val textoNombreUsuario = miViewModel.textoNombreUsuario.collectAsState().value
    val textoPassword = miViewModel.textoPassword.collectAsState().value
    Dialog(
        onDismissRequest = { miViewModel.abrirIniciarSesion() },
        properties = DialogProperties(decorFitsSystemWindows = true)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            (LocalView.current.parent as? DialogWindowProvider)?.window?.setDimAmount(0.05f)
            Logotipo()
            Texto(text = "Nombre de usuario")
            CampoDeTexto(texto = textoNombreUsuario) { nuevoTexto -> miViewModel.actualizaNombreUsuario(nuevoTexto) }
            Texto(text = "Contraseña")
            CampoDeTexto(texto = textoPassword) { nuevoTexto -> miViewModel.actualizaPassword(nuevoTexto) }
            BotonesSesion(texto1 = "Iniciar sesión", navigate = { navController.navigate(Ruta.Principal.ruta) }) { miViewModel.abrirIniciarSesion() }
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
fun CampoDeTexto(texto: String, accion: (String) -> Unit) {
    TextField(
        value = texto,
        onValueChange = accion,
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.textFieldColors(containerColor = fondoTextField)
    )
}

@Composable
fun Logotipo() {
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
}

@Composable
fun BotonesSesion(texto1: String, navigate: () -> Unit, onClick: () -> Unit) {
    Button(
        onClick = {
                navigate()
                onClick()
        },
        colors = ButtonDefaults.buttonColors(colorBoton),
        shape = RoundedCornerShape(20.dp), // siguiendo las normas de material design ;)
        content = {
            Text(
                text = texto1,
                fontSize = 25.sp,
                color = colorFuenteBoton
            )
        },
        modifier = Modifier.padding(top = 20.dp)
    )
    IconButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        content = {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Icono volver",
                    tint = colorBoton
                )
                Text(
                    text = "Volver",
                    fontSize = 20.sp,
                    color = colorBoton
                )
            }
        }
    )
}

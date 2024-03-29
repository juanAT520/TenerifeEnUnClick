package com.juan.tenerifeenunclick.gui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Forest
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
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

    DisposableEffect(viewModelInicial) {
        viewModelInicial.crearListener()
        onDispose { viewModelInicial.borrarListener() }
    }

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
    if (dialogoIniciarSesion) dialogoIniciarSesion(viewModelInicial, navController)
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun DialogoCrearUsuario(
    viewModelInicial: ViewModelInicial,
    navController: NavHostController
) {
    val textoNombre = viewModelInicial.textoNombre.collectAsState().value
    val textoEmail = viewModelInicial.textoEmail.collectAsState().value
    val textoNombreUsuario = viewModelInicial.textoNombreUsuario.collectAsState().value
    val textoPassword = viewModelInicial.textoPassword.collectAsState().value
    val textoRepitePassword = viewModelInicial.textoRepitePassword.collectAsState().value
    var datosUserValidos: Int
    val muestraMensajePassword = viewModelInicial.muestraMensajePassword.collectAsState().value
    val muestraMensajeDatos = viewModelInicial.muestraMensajeDatos.collectAsState().value
    val mensajePassword = remember { mutableStateOf("") }
    val mensajeDatos = remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = { viewModelInicial.abrirCrearUsuario() },
        properties = DialogProperties(decorFitsSystemWindows = true)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            (LocalView.current.parent as? DialogWindowProvider)?.window?.setDimAmount(0.05f)
            Logotipo()
            Texto(text = "Nombre completo")
            CampoDeTexto(textoNombre) { nuevoTexto -> viewModelInicial.actualizaNombre(nuevoTexto) }
            Texto(text = "Dirección de email")
            CampoDeTexto(texto = textoEmail) { nuevoTexto ->
                viewModelInicial.actualizaEmail(
                    nuevoTexto
                )
            }
            Texto(text = "Nombre de usuario")
            CampoDeTexto(texto = textoNombreUsuario) { nuevoTexto ->
                viewModelInicial.actualizaNombreUsuario(
                    nuevoTexto
                )
            }
            Texto(text = "Contraseña")
            CampoDeTexto(texto = textoPassword) { nuevoTexto ->
                viewModelInicial.actualizaPassword(
                    nuevoTexto
                )
            }
            Texto(text = "Repite la contraseña")
            CampoDeTexto(texto = textoRepitePassword) { nuevoTexto ->
                viewModelInicial.actualizaPasswordOtraVez(
                    nuevoTexto
                )
            }
            BotonesSesion(texto1 = "Crear cuenta", {
                viewModelInicial.borrarCampos()
                viewModelInicial.abrirCrearUsuario()
            }) {
                datosUserValidos =
                    viewModelInicial.compruebaPassword(textoPassword, textoRepitePassword)
                when {
                    datosUserValidos == 2 -> {
                        mensajePassword.value = "Las contraseñas no coinciden o no existen"
                        viewModelInicial.abrirCerrarMensajePassword()
                    }

                    textoEmail.isEmpty() || textoNombreUsuario.isEmpty() -> {
                        mensajeDatos.value =
                            "Debes introducir dirección de email y nombre de usuario."
                        viewModelInicial.abrirCerrarMensajeDatos()
                    }

                    else -> {
                        if (textoPassword.length >= 6) {
                            Firebase.auth.createUserWithEmailAndPassword(textoEmail, textoPassword)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        viewModelInicial.addUser(
                                            textoEmail,
                                            textoNombre,
                                            textoNombreUsuario,
                                            textoPassword
                                        )
                                        viewModelInicial.abrirCrearUsuario()
                                        navController.navigate(Ruta.Principal.ruta)
                                        Log.d(TAG, "Email sent.")
                                    } else {
                                        mensajeDatos.value = "El formato del email es incorrecto."
                                        viewModelInicial.abrirCerrarMensajeDatos()
                                    }
                                }
                        } else {
                            mensajePassword.value =
                                "La contraseña debe tener, al menos, seis caracteres."
                            viewModelInicial.abrirCerrarMensajePassword()
                        }
                    }
                }
            }
        }
    }
    if (muestraMensajePassword) dialogoAviso(
        viewModelInicial,
        titulo = "Contraseña no válida",
        contenido = mensajePassword.value
    ) { viewModelInicial.abrirCerrarMensajePassword() }
    if (muestraMensajeDatos) dialogoAviso(
        viewModelInicial,
        titulo = "Datos inválidos",
        contenido = mensajeDatos.value
    ) { viewModelInicial.abrirCerrarMensajeDatos() }
}

@Composable
private fun dialogoIniciarSesion(
    viewModelInicial: ViewModelInicial,
    navController: NavHostController
) {
    val textoEmail = viewModelInicial.textoEmail.collectAsState().value
    val textoPassword = viewModelInicial.textoPassword.collectAsState().value
    val muestraMensajePassword = viewModelInicial.muestraMensajePassword.collectAsState().value
    val muestraMensajeUsuarioDesconocido =
        viewModelInicial.muestraMensajeUsuarioDesconocido.collectAsState().value
    Dialog(
        onDismissRequest = { viewModelInicial.abrirIniciarSesion() },
        properties = DialogProperties(decorFitsSystemWindows = true)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            (LocalView.current.parent as? DialogWindowProvider)?.window?.setDimAmount(0.05f)
            Logotipo()
            Texto(text = "Dirección de email")
            CampoDeTexto(texto = textoEmail) { nuevoTexto ->
                viewModelInicial.actualizaEmail(
                    nuevoTexto
                )
            }
            Texto(text = "Contraseña")
            CampoDeTexto(texto = textoPassword) { nuevoTexto ->
                viewModelInicial.actualizaPassword(
                    nuevoTexto
                )
            }
            BotonesSesion(texto1 = "Iniciar sesión", {
                viewModelInicial.borrarCampos()
                viewModelInicial.abrirIniciarSesion()
            }) {
                val esValido = viewModelInicial.compruebaUsuarioExistente(textoEmail)
                if (esValido) {
                    Firebase.auth.signInWithEmailAndPassword(textoEmail, textoPassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                viewModelInicial.abrirIniciarSesion()
                                navController.navigate(Ruta.Principal.ruta)
                            } else {
                                viewModelInicial.abrirCerrarMensajePassword()
                            }
                        }
                } else {
                    viewModelInicial.abrirCerrarMensajeUsuarioDesconocido()
                }
            }
        }
    }
    if (muestraMensajePassword) dialogoAviso(
        viewModelInicial,
        "Contraseña no válida",
        "Contraseña incorrecta"
    ) { viewModelInicial.abrirCerrarMensajePassword() }

    if (muestraMensajeUsuarioDesconocido) dialogoAviso(
        viewModelInicial,
        "Usuario no válido",
        "El nombre de usuario no dispone de cuenta."
    ) { viewModelInicial.abrirCerrarMensajeUsuarioDesconocido() }
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
fun BotonesSesion(texto1: String, atras: () -> Unit, onClick: () -> Unit) {
    Button(
        onClick = {
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
        onClick = { atras() },
        modifier = Modifier.fillMaxWidth(),
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
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

@Composable
private fun dialogoAviso(
    viewModelInicial: ViewModelInicial,
    titulo: String,
    contenido: String,
    onClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            viewModelInicial.reiniciaValidacionDatos(false)
            onClick()
        },
        confirmButton = {
            Button(onClick = {
                viewModelInicial.reiniciaValidacionDatos(false)
                onClick()
            }) {
                Text(text = "Probar de nuevo")
            }
        },
        title = { Text(titulo) },
        text = { Text(contenido) }
    )
}

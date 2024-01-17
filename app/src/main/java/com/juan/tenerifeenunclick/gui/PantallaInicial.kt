package com.juan.tenerifeenunclick.gui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Forest
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.juan.tenerifeenunclick.navigation.Rutas
import com.juan.tenerifeenunclick.ui.theme.autumn_foliage
import com.juan.tenerifeenunclick.ui.theme.colorBoton
import com.juan.tenerifeenunclick.ui.theme.colorFuente
import com.juan.tenerifeenunclick.ui.theme.colorFuenteBoton
import com.juan.tenerifeenunclick.ui.theme.fondo
import com.juan.tenerifeenunclick.ui.theme.lime_Green

@Composable
fun PantallaInicial(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = fondo
    ) {
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
                    onClick = { navController.navigate(Rutas.CrearCuenta.ruta) },
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
}
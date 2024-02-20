package com.juan.tenerifeenunclick.navigation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Forest
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.juan.tenerifeenunclick.gui.PantallaAutoctonas
import com.juan.tenerifeenunclick.gui.PantallaExoticas
import com.juan.tenerifeenunclick.gui.PantallaInicial
import com.juan.tenerifeenunclick.gui.PantallaPrincipal
import com.juan.tenerifeenunclick.gui.PantallaRecreativas
import com.juan.tenerifeenunclick.gui.PantallaRutas
import com.juan.tenerifeenunclick.ui.theme.autumn_foliage
import com.juan.tenerifeenunclick.ui.theme.colorBoton
import com.juan.tenerifeenunclick.ui.theme.colorBotonMenu
import com.juan.tenerifeenunclick.ui.theme.colorFuenteBoton
import com.juan.tenerifeenunclick.ui.theme.colorPrimarioSistema
import com.juan.tenerifeenunclick.ui.theme.fondo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrafoNavegacion() {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val colorDeFondo = arrayOf(0.65f to Color(0xFFB6D866), 1.0f to Color(0xFF5D8D32))
    // Obtener la ruta actual del navController
    val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route
    // Booleano que será true si no está en la pantalla inicial
    val mostrarDrawer = rutaActual != "inicial"

    if (mostrarDrawer) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                scrimColor = fondo,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colorStops = colorDeFondo)),
                drawerContent = {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .background(colorPrimarioSistema)
                            .fillMaxHeight()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(0.dp, 15.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Forest,
                                contentDescription = "Icono Árboles",
                                modifier = Modifier
                                    .size(60.dp),
                                tint = autumn_foliage
                            )
                            BotonSeccionMenuLateral({navController.navigate(Ruta.Principal.ruta)}, scope, drawerState, titulo = "Pantalla principal")
                            BotonSeccionMenuLateral({navController.navigate(Ruta.Autoctonas.ruta)}, scope, drawerState, titulo = "Plantas autóctonas")
                            BotonSeccionMenuLateral({navController.navigate(Ruta.Rutas.ruta)}, scope, drawerState, titulo = "Rutas y senderos")
                            BotonSeccionMenuLateral({navController.navigate(Ruta.Recreativas.ruta)}, scope, drawerState, titulo = "Áreas recreativas")
                            BotonSeccionMenuLateral({navController.navigate(Ruta.Exoticas.ruta)}, scope, drawerState, titulo = "He visto una exótica!!")
                        }
                        BotonSeccionMenuLateral({navController.navigate(Ruta.Inicial.ruta)}, scope, drawerState, titulo = "Cerrar sesión")
                    }
                },
                content = {
                    RutasNavHost(navController, scope, context)
                }
            )
            FloatingActionButton(
                shape = CircleShape,
                containerColor = colorBotonMenu,
                onClick = {
                    scope.launch {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {
                            drawerState.open()
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(15.dp)
                    .size(70.dp),
                content = {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Icono menú",
                        modifier = Modifier
                            .padding(10.dp)
                            .size(45.dp),
                        tint = colorFuenteBoton
                    )
                }
            )
        }
    } else {
        RutasNavHost(navController, scope, context)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutasNavHost(
    navController: NavHostController,
    scope: CoroutineScope,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = Ruta.Inicial.ruta
    ) {
        composable("inicial") {
            PantallaInicial(navController)
        }
        composable("principal") {
            PantallaPrincipal()
        }
        composable("autoctonas") {
            PantallaAutoctonas()
        }
        composable("rutas") {
            PantallaRutas()
        }
        composable("recreativas") {
            PantallaRecreativas()
        }
        composable("exoticas") {
            PantallaExoticas(context, scope)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BotonSeccionMenuLateral(
    navega: () -> Unit,
    scope: CoroutineScope,
    drawerState: DrawerState,
    titulo: String
) {
    TextButton(
        onClick = {
            navega()
            scope.launch {
                drawerState.close()
            }
        },
        modifier = Modifier.width(300.dp),
        content = {
            Text(
                text = titulo,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(0.dp, 20.dp)
            )
        },
        colors = ButtonDefaults.textButtonColors(
            contentColor = colorBoton
        )
    )
}

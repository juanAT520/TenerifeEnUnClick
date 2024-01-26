package com.juan.tenerifeenunclick.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juan.tenerifeenunclick.gui.PantallaCrearCuenta
import com.juan.tenerifeenunclick.gui.PantallaInicial
import com.juan.tenerifeenunclick.gui.PantallaIniciarSesion
import com.juan.tenerifeenunclick.gui.PantallaPrincipal
import com.juan.tenerifeenunclick.viewModel.ViewModelInicial
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrafoNavegacion() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    NavHost(
        navController = navController,
        startDestination = Rutas.Inicial.ruta
    ) {
        composable("inicial") {
            PantallaInicial(navController)
        }
        composable("crearCuenta") {
            PantallaCrearCuenta(navController)
        }
        composable("iniciarSesion") {
            PantallaIniciarSesion(navController)
        }
        composable("principal") {
            PantallaPrincipal(drawerState)
        }
    }
}

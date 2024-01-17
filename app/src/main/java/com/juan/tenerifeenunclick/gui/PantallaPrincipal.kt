package com.juan.tenerifeenunclick.gui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juan.tenerifeenunclick.ui.theme.AlmostBlack
import com.juan.tenerifeenunclick.ui.theme.lime_Green
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Tenerife en un Click") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            if (drawerState.isOpen) {
                                drawerState.close()
                            } else {
                                drawerState.open()
                            }
                        }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            Box {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        Column(
                            modifier = Modifier
                                .background(AlmostBlack)
                                .fillMaxHeight()
                        ) {
                            TextButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                content = {
                                    Text(
                                        text = "Playlist meme",
                                        modifier = Modifier
                                            .padding(70.dp, 15.dp)
                                    )
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = lime_Green
                                )
                            )
                            TextButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                content = {
                                    Text(
                                        text = "Playlist buena",
                                        modifier = Modifier
                                            .padding(70.dp, 15.dp)
                                    )
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = lime_Green
                                )
                            )
                        }
                    },
                    content = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {

                        }
                    }
                )
            }
        }
    }
}

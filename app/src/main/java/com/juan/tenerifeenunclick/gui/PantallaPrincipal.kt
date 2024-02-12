package com.juan.tenerifeenunclick.gui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Forest
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juan.tenerifeenunclick.R
import com.juan.tenerifeenunclick.ui.theme.autumn_foliage
import com.juan.tenerifeenunclick.ui.theme.colorBoton
import com.juan.tenerifeenunclick.ui.theme.colorFuente
import com.juan.tenerifeenunclick.ui.theme.colorPrimarioSistema
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize()
        ) {
            LogotipoChato()
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.imagen1),
                    contentDescription = "Imagen costa de Tenerife",
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp)))
                Text(
                    text = "En esta isla de Canarias las aventuras no terminan, desde perderte por los montes de Anaga a hacer windsurf en el acogedor pueblo del Médano. \n" +
                            " Debido a su origen volcánico, su clima subtropical y sus altitudes la isla cuenta con una gran diversidad biológica, paisajista y geológica. \n" +
                            "Tenerife dispone de un sin fin de senderos en los que disfrutar de un día en la naturaleza observando sus peculiares endemismos, algunos de ellos únicos en el mundo, además cuenta con varias áreas recreativas donde podrás disfrutar de una buena barbacoa mientras acampas.",
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    color = colorFuente,
                    modifier = Modifier.padding(0.dp, 20.dp)
                )
            }
        }
    }
}

@Composable
fun LogotipoChato() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Rounded.Forest,
            contentDescription = "Icono Árboles",
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopStart),
            tint = autumn_foliage
        )
        Text(
            text = "Tenerife en un Click",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = colorFuente,
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}


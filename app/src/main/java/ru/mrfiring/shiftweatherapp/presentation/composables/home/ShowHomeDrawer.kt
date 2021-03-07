package ru.mrfiring.shiftweatherapp.presentation.composables.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mrfiring.shiftweatherapp.R

@Composable
fun ShowHomeDrawer(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onBtnClick: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalDrawer(
        drawerContent = {
            Column {
                HomeDrawerHeader(onBtnClick)
                Divider()
                HomeDrawerContent()
            }
        },
        drawerState = drawerState
    ) {
        content()
    }
}

@Composable
fun HomeDrawerHeader(onClick: () -> Unit) {
    Column {
        Button(onClick = onClick) {
            Text(text = "Theme)")
        }
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "User avatar"
        )
        Text(
            text = "Unknown user",
            style = MaterialTheme.typography.h5
        )
        Text(
            text = "hello1234@shiftweather.ru",
            style = MaterialTheme.typography.subtitle1,
            fontSize = 18.sp

        )

    }

}

@Composable
fun HomeDrawerContent() {
    Column {
        val res = painterResource(id = R.drawable.ic_logo_foreground)
        DrawerMenuItem(painter = res, text = "Show cities", onClick = { /*TODO*/ })
        DrawerMenuItem(painter = res, text = "Show countries", onClick = { /*TODO*/ })
        DrawerMenuItem(painter = res, text = "Favorites", onClick = { /*TODO*/ })
        DrawerMenuItem(painter = res, text = "Settings", onClick = { /*TODO*/ })
    }
}

@Composable
fun DrawerMenuItem(painter: Painter, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Image(
            painter = painter,
            contentDescription = "Menu Item",
            modifier = Modifier
                .padding(8.dp)
                .width(20.dp)
                .height(20.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}
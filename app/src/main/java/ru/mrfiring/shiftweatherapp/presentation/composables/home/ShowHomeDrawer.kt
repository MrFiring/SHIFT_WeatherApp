package ru.mrfiring.shiftweatherapp.presentation.composables.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.domain.models.DomainDrawerMenuItem
import ru.mrfiring.shiftweatherapp.presentation.theme.CardWithPaddingAndFillWidth

@Composable
fun ShowHomeDrawer(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onBtnClick: () -> Unit,
    items: List<DomainDrawerMenuItem>,
    content: @Composable () -> Unit
) {
    ModalDrawer(
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                HomeDrawerHeader(onBtnClick)
                Divider(modifier = Modifier.padding(bottom = 8.dp))
                HomeDrawerContent(items)
            }
        },
        drawerState = drawerState
    ) {
        content()
    }
}

@Composable
fun HomeDrawerHeader(onClick: () -> Unit) {

    val isLight = MaterialTheme.colors.isLight

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        val switcher = painterResource(id = R.drawable.ic_theme_switcher)
        Image(
            painter = switcher,
            contentDescription = "Switch theme",
            modifier = Modifier
                .align(Alignment.End)
                .padding(vertical = 8.dp)
                .clickable { onClick() }
                .background(
                    color = if (isLight) {
                        MaterialTheme.colors.surface
                    } else {
                        MaterialTheme.colors.secondary
                    }, shape = CircleShape
                ),

            colorFilter = if (isLight) {
                ColorFilter.tint(MaterialTheme.colors.primary)
            } else {
                ColorFilter.tint(MaterialTheme.colors.onPrimary)
            }

        )
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "User avatar",

            )

        Column(
            modifier = Modifier.padding(vertical = 14.dp)
        ) {
            Text(
                text = "Unknown user",
                style = MaterialTheme.typography.h5,
                color = if (isLight) MaterialTheme.colors.secondaryVariant
                else MaterialTheme.colors.onPrimary
            )
            Text(
                text = "hello1234@shiftweather.ru",
                style = MaterialTheme.typography.subtitle1,
                fontSize = 18.sp,
                color = if (isLight) MaterialTheme.colors.secondaryVariant
                else MaterialTheme.colors.onPrimary
            )
        }

    }

}

@Composable
fun HomeDrawerContent(items: List<DomainDrawerMenuItem>) {
    Column {
        items.forEach {
            DrawerMenuItem(
                painter = painterResource(id = it.drawableId),
                text = it.text,
                onClick = { it.onClick() })
        }
    }
}

@Composable
fun DrawerMenuItem(painter: Painter, text: String, onClick: () -> Unit) {
    CardWithPaddingAndFillWidth(
        modifier = Modifier
            .clickable {
                onClick()
            },
        padding = PaddingValues(8.dp, 4.dp, 8.dp, 4.dp)
    ) {
        Row(
            modifier = if (MaterialTheme.colors.isLight) {
                Modifier
            } else {
                Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colors.secondary
                )
            }
        ) {
            Image(
                painter = painter,
                contentDescription = "Menu Item",
                modifier = Modifier
                    .padding(8.dp)
                    .width(24.dp)
                    .height(24.dp),
                colorFilter = if (MaterialTheme.colors.isLight) {
                    ColorFilter.tint(MaterialTheme.colors.primary)
                } else {
                    null
                }

            )
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle1,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(24.dp, 8.dp, 0.dp, 8.dp)

            )
        }
    }

}

//For debug purpose only)
val items = listOf(
    DomainDrawerMenuItem(
        drawableId = R.drawable.ic_city_icon,
        text = "Show cities",
        onClick = { /*TODO*/ }),
    DomainDrawerMenuItem(
        drawableId = R.drawable.ic_country_icon,
        text = "Show countries",
        onClick = { /*TODO*/ }),
    DomainDrawerMenuItem(
        drawableId = R.drawable.ic_favorite,
        text = "Favorites",
        onClick = { /*TODO*/ }),
    DomainDrawerMenuItem(
        drawableId = R.drawable.ic_settings,
        text = "Settings",
        onClick = { /*TODO*/ })
)


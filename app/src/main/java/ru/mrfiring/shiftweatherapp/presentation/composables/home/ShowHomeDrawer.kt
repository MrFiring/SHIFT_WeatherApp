package ru.mrfiring.shiftweatherapp.presentation.composables.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mrfiring.shiftweatherapp.domain.models.DomainDrawerMenuItem
import ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer.HomeDrawerContent
import ru.mrfiring.shiftweatherapp.presentation.composables.home.drawer.HomeDrawerHeader

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


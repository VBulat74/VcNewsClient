package ru.com.vbulat.vcnewsclient.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import ru.com.vbulat.vcnewsclient.R
import ru.com.vbulat.vcnewsclient.navigaton.Screen

sealed class NavigationItem(
    val titleResId : Int,
    val icon : ImageVector,
    val screen : Screen,
) {
    object Home : NavigationItem(
        titleResId = R.string.navigation_item_main,
        icon = Icons.Outlined.Home,
        screen = Screen.Home,
    )

    object Favorite : NavigationItem(
        titleResId = R.string.navigation_item_favoritу,
        icon = Icons.Outlined.Favorite,
        screen = Screen.Favorite,
    )

    object Profile : NavigationItem(
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person,
        screen = Screen.Profile,
    )

}
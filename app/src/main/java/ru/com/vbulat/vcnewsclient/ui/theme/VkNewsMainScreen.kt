package ru.com.vbulat.vcnewsclient.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.com.vbulat.vcnewsclient.MainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel
){
    val selectedNavItem by viewModel.selectedNavItem.observeAsState(NavigationItem.Home)

    Scaffold (
        bottomBar = {
            NavigationBar {
                val items = listOf(NavigationItem.Home, NavigationItem.Favorite, NavigationItem.Profile)
                items.forEach {item ->
                    NavigationBarItem(
                        selected = selectedNavItem == item,
                        onClick = { viewModel.selectNavIem(item) },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        icon = {
                            Icon(item.icon, contentDescription =null )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                    )
                }

            }
        },
    ){ paddingValues ->
        //Box(modifier = Modifier.padding(paddingValues))
        when(selectedNavItem) {
            NavigationItem.Home -> {
                HomeScreen(viewModel= viewModel, paddingValues = paddingValues)
            }
            NavigationItem.Profile -> {
                TextCounter(name = "Profile")
            }
            NavigationItem.Favorite -> {
                TextCounter(name = "Favorite")
            }
        }
    }
}

@Composable
private fun TextCounter(name : String){
    var count by remember {
        mutableStateOf(0)
    }

    Text(
        modifier = Modifier.clickable { count++},
        text = "$name Count: $count"
    )
}
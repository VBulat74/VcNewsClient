package ru.com.vbulat.vcnewsclient.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.com.vbulat.vcnewsclient.domain.FeedPost

@Composable
fun MainScreen(){

    val feedPost = remember {
        mutableStateOf(FeedPost())
    }

    Scaffold (
        bottomBar = {
            NavigationBar {
                val selectedItemPosition = remember {
                    mutableStateOf(0)
                }
                val items = listOf(NavigationItem.Home, NavigationItem.Favorite, NavigationItem.Profile)
                items.forEachIndexed {index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.value==index,
                        onClick = { selectedItemPosition.value = index },
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
        PostCard(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp),
            feedPost = feedPost.value,
            onStatisticsItemClickListener = { newItem ->
                val oldStatistics = feedPost.value.statistics
                val newStatistics = oldStatistics.toMutableList().apply {
                    replaceAll { oldItem ->
                        if (oldItem.type == newItem.type) {
                            oldItem.copy(count = oldItem.count + 1)
                        } else {
                            oldItem
                        }
                    }
                }
                feedPost.value = feedPost.value.copy(statistics = newStatistics)
            }
        )
    }
}

@Preview
@Composable
fun MainScreenPreview(){
    MainScreen()
}
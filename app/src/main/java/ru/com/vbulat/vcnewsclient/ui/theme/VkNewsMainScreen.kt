package ru.com.vbulat.vcnewsclient.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.com.vbulat.vcnewsclient.MainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel
){
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
        val feedPosts = viewModel.feedPosts.observeAsState(listOf())

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ){
            items(feedPosts.value, key = {it.id}){feedPost->

                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)){
                    viewModel.remove(feedPost)
                }

                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    background = {

                    },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissContent = {PostCard(
                        feedPost = feedPost,
                        onLikeClickListener = { item ->
                            viewModel.updateCount(feedPost, item)
                        },
                        onSharesClickListener = { item ->
                            viewModel.updateCount(feedPost, item)
                        },
                        onViewsClickListener = { item ->
                            viewModel.updateCount(feedPost, item)
                        },
                        onCommentsClickListener = { item ->
                            viewModel.updateCount(feedPost, item)
                        },
                    )}
                )
            }
        }
    }
}
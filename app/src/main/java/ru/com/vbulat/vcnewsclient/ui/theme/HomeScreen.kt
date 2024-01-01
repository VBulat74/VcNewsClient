package ru.com.vbulat.vcnewsclient.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import ru.com.vbulat.vcnewsclient.MainViewModel
import ru.com.vbulat.vcnewsclient.domain.PostComment

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
){
    val feedPosts = viewModel.feedPosts.observeAsState(listOf())

    val comments = mutableListOf<PostComment>().apply {
        repeat(20){
            add(
                PostComment(id = it)
            )
        }
    }

    CommentsScreen(
        feedPost = feedPosts.value.get(0),
        comments = comments
    )

    /*LazyColumn(
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
    }*/
}
package ru.com.vbulat.vcnewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.com.vbulat.vcnewsclient.domain.FeedPost
import ru.com.vbulat.vcnewsclient.domain.PostComment
import ru.com.vbulat.vcnewsclient.domain.StatisticItem
import ru.com.vbulat.vcnewsclient.ui.theme.HomeScreenState
import ru.com.vbulat.vcnewsclient.ui.theme.NavigationItem

class MainViewModel : ViewModel() {

    private val comments = mutableListOf<PostComment>().apply {
        repeat(10){
            add(
                PostComment(id = it)
            )
        }
    }

    val sourceList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(
                FeedPost(
                    id = it,

                )
            )
        }
    }

    private val initialState = HomeScreenState.Posts(posts = sourceList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState

    private val _selectedNavItem = MutableLiveData<NavigationItem> (NavigationItem.Home)
    val selectedNavItem : LiveData <NavigationItem> = _selectedNavItem

    private var savedState : HomeScreenState? = initialState

    fun selectNavIem (item: NavigationItem) {
        _selectedNavItem.value = item
    }

    fun showComment (
        feedPost: FeedPost
    ){
        savedState = screenState.value
        _screenState.value = HomeScreenState.Comments(feedPost = feedPost, comments = comments)
    }

    fun closeComments(){
        _screenState.value = savedState
    }
    fun updateCount(feedPost: FeedPost, item: StatisticItem) {

        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()

        val oldStatistics = feedPost.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
        _screenState.value = HomeScreenState.Posts(posts = newPosts)
    }

    fun remove(feedPost : FeedPost) {

        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)

        _screenState.value = HomeScreenState.Posts(posts = oldPosts)
    }
}
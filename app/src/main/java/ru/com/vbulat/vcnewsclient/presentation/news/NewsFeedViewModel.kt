package ru.com.vbulat.vcnewsclient.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.com.vbulat.vcnewsclient.data.repository.NewsFeedRepository
import ru.com.vbulat.vcnewsclient.domain.FeedPost

class NewsFeedViewModel (application : Application) : AndroidViewModel(application) {



    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val repository = NewsFeedRepository(application)

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadRecommendations()
    }

    private fun loadRecommendations(){
        viewModelScope.launch {
            val feedPosts = repository.loadRecommendations()
            _screenState.value = NewsFeedScreenState.Posts(posts = feedPosts)
        }
    }

    fun loadNextRecommendations(){
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataIsLoading = true
        )

        loadRecommendations()
    }

    fun changeLikeStatus(feedPost : FeedPost){
        viewModelScope.launch {
            if (feedPost.isLiked){
                repository.deleteLike(feedPost)
                _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
            }else{
                repository.addLike(feedPost)
                _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
            }
        }
    }

    fun remove(feedPost : FeedPost) {

        viewModelScope.launch {
            repository.deletePost(feedPost)
        }

        _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
    }


}
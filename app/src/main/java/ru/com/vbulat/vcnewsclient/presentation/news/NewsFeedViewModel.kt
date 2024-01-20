package ru.com.vbulat.vcnewsclient.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.com.vbulat.vcnewsclient.domain.entety.FeedPost
import ru.com.vbulat.vcnewsclient.domain.usecases.AddLikeUseCase
import ru.com.vbulat.vcnewsclient.domain.usecases.DeleteLikeUseCase
import ru.com.vbulat.vcnewsclient.domain.usecases.DeletePostUseCase
import ru.com.vbulat.vcnewsclient.domain.usecases.GetRecommendationUseCase
import ru.com.vbulat.vcnewsclient.domain.usecases.LoadNextDataUseCase
import ru.com.vbulat.vcnewsclient.extensions.mergeWith
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getRecommendationUseCase: GetRecommendationUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase,
    private val addLikeUseCase: AddLikeUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("AAA", "Exception caught by Exception handler")
    }

    private val recommendationsFlow = getRecommendationUseCase()

    private val loadNextDataEvents = MutableSharedFlow<Unit>()
    private val loadNextDataFlow = flow {
        loadNextDataEvents.collect() {
            emit(
                NewsFeedScreenState.Posts(
                    posts = recommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
        }
    }

    val screenState = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun loadNextRecommendations() {
        viewModelScope.launch {
            loadNextDataEvents.emit(Unit)
            loadNextDataUseCase()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            if (feedPost.isLiked) {
                deleteLikeUseCase(feedPost)
            } else {
                addLikeUseCase(feedPost)
            }
        }
    }

    fun remove(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(feedPost)
        }
    }


}
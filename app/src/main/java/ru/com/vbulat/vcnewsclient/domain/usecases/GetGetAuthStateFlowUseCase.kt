package ru.com.vbulat.vcnewsclient.domain.usecases

import kotlinx.coroutines.flow.StateFlow
import ru.com.vbulat.vcnewsclient.domain.entety.AuthState
import ru.com.vbulat.vcnewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetGetAuthStateFlowUseCase @Inject constructor(
    private val repository : NewsFeedRepository
) {
    operator fun invoke() : StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }
}
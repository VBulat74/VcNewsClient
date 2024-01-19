package ru.com.vbulat.vcnewsclient.domain.usecases

import ru.com.vbulat.vcnewsclient.domain.repository.NewsFeedRepository

class CheckAuthStateUseCase(
    private val repository : NewsFeedRepository
) {
    suspend operator fun  invoke() {
        return repository.checkAuthState()
    }
}
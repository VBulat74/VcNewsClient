package ru.com.vbulat.vcnewsclient.domain.usecases

import ru.com.vbulat.vcnewsclient.domain.repository.NewsFeedRepository

class LoadNextDataUseCase(
    private val repository : NewsFeedRepository
) {
    suspend operator fun invoke() {
        return repository.loadNextData()
    }
}
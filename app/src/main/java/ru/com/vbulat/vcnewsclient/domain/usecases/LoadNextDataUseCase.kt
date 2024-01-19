package ru.com.vbulat.vcnewsclient.domain.usecases

import ru.com.vbulat.vcnewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class LoadNextDataUseCase @Inject constructor(
    private val repository : NewsFeedRepository
) {
    suspend operator fun invoke() {
        return repository.loadNextData()
    }
}
package ru.com.vbulat.vcnewsclient.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.com.vbulat.vcnewsclient.data.repository.NewsFeedRepositoryImpl
import ru.com.vbulat.vcnewsclient.domain.usecases.CheckAuthStateUseCase
import ru.com.vbulat.vcnewsclient.domain.usecases.GetGetAuthStateFlowUseCase

class MainViewModel (application : Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getAuthStateFlowUseCase = GetGetAuthStateFlowUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val authState = getAuthStateFlowUseCase()

    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }

}
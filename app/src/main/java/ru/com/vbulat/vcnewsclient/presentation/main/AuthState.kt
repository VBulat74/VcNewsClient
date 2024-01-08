package ru.com.vbulat.vcnewsclient.presentation.main

sealed class AuthState {

    object Authorized : AuthState()

    object  NotAuthorized : AuthState()

    object Initial : AuthState()
}
package ru.com.vbulat.vcnewsclient

sealed class AuthState {

    object Authorized : AuthState()

    object  NotAuthorized : AuthState()

    object Initial : AuthState()
}
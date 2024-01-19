package ru.com.vbulat.vcnewsclient.domain.entety

sealed class AuthState {

    object Authorized : AuthState()

    object  NotAuthorized : AuthState()

    object Initial : AuthState()
}
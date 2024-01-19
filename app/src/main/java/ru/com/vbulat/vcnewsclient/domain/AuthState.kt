package ru.com.vbulat.vcnewsclient.domain

sealed class AuthState {

    object Authorized : AuthState()

    object  NotAuthorized : AuthState()

    object Initial : AuthState()
}
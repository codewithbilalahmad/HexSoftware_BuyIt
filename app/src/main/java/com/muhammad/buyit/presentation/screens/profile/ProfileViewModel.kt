package com.muhammad.buyit.presentation.screens.profile

import androidx.lifecycle.ViewModel
import com.muhammad.buyit.data.UserSession
import com.muhammad.domain.model.UserDomainModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val userSession: UserSession
) : ViewModel(){
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()
    private val _events = Channel<ProfileEvent>()
    val events = _events.receiveAsFlow()
    init {
        onAction(ProfileAction.GetUserData)
    }
    fun onAction(action: ProfileAction){
        when(action){
            ProfileAction.GetUserData -> getUserData()
            ProfileAction.OnLogout -> onLogout()
        }
    }

    private fun onLogout() {
        userSession.insertUser(UserDomainModel(id = 0, username = "", email = "", name = ""))
        _events.trySend(ProfileEvent.OnUserLogout)
    }

    private fun getUserData() {
        val userData = userSession.getUser() ?: return
        _state.update { it.copy(username = userData.name, email = userData.email) }
    }
}
package com.example.semana09_servicios.viewmodel

import android.os.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semana09_servicios.data.model.User
import com.example.semana09_servicios.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



sealed class UserUiState {
    object Loading : UserUiState()
    data class Success(val users: List<User>) : UserUiState()
    data class Error(val message: String) : UserUiState()
}

class UserViewModel : ViewModel() {

    private val repository = UserRepository()
    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState

    init {
        loadUsers()
    }

    fun loadUsers(){
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading

            try {
                val users = repository.getUsers()
                _uiState.value = UserUiState.Success(users)
            } catch (e: Exception){
                _uiState.value = UserUiState.Error(
                    e.message ?: "Error desconocido al cargar usuarios"
                )
            }
        }
    }

}
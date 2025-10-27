package com.example.semana09_servicios.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semana09_servicios.data.model.User
import com.example.semana09_servicios.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UserUiState {
    object Loading : UserUiState()
    data class Success(val users: List<User>, val filteredUsers: List<User>) : UserUiState()
    data class Error(val message: String) : UserUiState()
}

class UserViewModel : ViewModel() {

    private val repository = UserRepository()
    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var allUsers: List<User> = emptyList()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading

            try {
                allUsers = repository.getUsers()
                _uiState.value = UserUiState.Success(
                    users = allUsers,
                    filteredUsers = allUsers
                )
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error(
                    e.message ?: "Error desconocido al cargar usuarios"
                )
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query

        // Solo filtramos si ya tenemos usuarios cargados
        val currentState = _uiState.value
        if (currentState is UserUiState.Success) {
            val filtered = if (query.isBlank()) {
                allUsers
            } else {
                allUsers.filter { user ->
                    user.name.contains(query, ignoreCase = true) ||
                            user.email.contains(query, ignoreCase = true) ||
                            user.username.contains(query, ignoreCase = true)
                }
            }
            _uiState.value = UserUiState.Success(
                users = allUsers,
                filteredUsers = filtered
            )
        }
    }
}
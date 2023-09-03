package com.keetr.comicsnac.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.data.settings.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AuthUiState {
    Initial, Verifying, InvalidKey, Verified, UnknownError
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(AuthUiState.Initial)

    var key by mutableStateOf("")
        private set

    fun onKeyChange(value: String) {
        key = value
    }

    fun verify() {
        state = AuthUiState.Verifying
        viewModelScope.launch {
            state = when (authRepository.verifyApiKey(key)) {
                RepositoryResponse.InvalidApiKeyError -> AuthUiState.InvalidKey
                RepositoryResponse.TimeoutError -> AuthUiState.UnknownError
                is RepositoryResponse.UnknownNetworkError -> AuthUiState.UnknownError
                is RepositoryResponse.Success -> {
                    authRepository.updateApiKey(key)
                    AuthUiState.Verified
                }
            }
        }
    }
}
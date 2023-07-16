package app.game.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(context: Context): ViewModel(){

    private val loginState = MutableStateFlow(LoginUiState())
    val flowLoginState: StateFlow<LoginUiState> = loginState.asStateFlow()

    private val sharedPref = SharedPref(context)

    fun username(username: String) {
        viewModelScope.launch {
            loginState.update { updateValue ->
                updateValue.copy(username = username)
            }
        }
    }

    fun password(password: String) {
        viewModelScope.launch {
            loginState.update { updateValue ->
                updateValue.copy(password = password)
            }
        }
    }

    fun returnPassword() {
        viewModelScope.launch {
            sharedPref.getPassword.collect {
                    password -> loginState.update {
                    updateValue -> updateValue.copy(storedPassword = password)
                }
            }
        }
    }

    fun returnUsername() {
        viewModelScope.launch {
            sharedPref.getUserName.collect {
                username -> loginState.update {
                    updateValue -> updateValue.copy(storedUsername = username)
                }
            }
        }
    }
}
